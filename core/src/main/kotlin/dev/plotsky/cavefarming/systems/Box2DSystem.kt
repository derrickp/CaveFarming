package dev.plotsky.cavefarming.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.World
import dev.plotsky.cavefarming.components.Box2DComponent
import dev.plotsky.cavefarming.components.Box2DComponent.Companion.box
import dev.plotsky.cavefarming.components.TransformComponent
import dev.plotsky.cavefarming.components.TransformComponent.Companion.transform
import ktx.ashley.allOf
import kotlin.math.min

class Box2DSystem(
    private val world: World,
) : IteratingSystem(allOf(Box2DComponent::class, TransformComponent::class).get()) {
    private var accumulator = STARTING_ACCUMULATOR

    /**
     * Updates the [world] using a fixed time step of deltaTime
     */
    override fun update(deltaTime: Float) {
        if (world.autoClearForces) {
            world.autoClearForces = false
        }

        accumulator += min(ACCUMULATOR_MIN_NUMERATOR / ACCUMULATOR_MIN_DENOMINATOR, deltaTime)
        while (accumulator >= deltaTime) {
            updatePrevPositionAndApplyForces()
            world.step(deltaTime, VELOCITY_ITERATIONS, POSITION_ITERATIONS)
            accumulator -= deltaTime
        }
        world.clearForces()

        updatePositionAndRenderPosition(accumulator / deltaTime)
    }

    /**
     * Applies an impulse once to all entities and updates their [TransformComponent.position] to the position
     * before calling [World.step].
     */
    private fun updatePrevPositionAndApplyForces() {
        entities.forEach { entity ->
            val transformCmp = entity.transform()
            val box2dCmp = entity.box()
            val halfW = transformCmp.size.x * HALF
            val halfH = transformCmp.size.y * HALF
            val body = box2dCmp.body

            transformCmp.position.x = body.position.x - halfW
            transformCmp.position.y = body.position.y - halfH

            if (!box2dCmp.impulse.isZero) {
                // apply non-zero impulse once before a call to world.step
//                body.applyForceToCenter(box2dCmp.impulse, true)
                body.applyLinearImpulse(box2dCmp.impulse, body.worldCenter, true)
                box2dCmp.impulse.set(ZERO, ZERO)
            }
        }
    }

    /**
     * Updates the [TransformComponent.position] and [Box2DComponent.renderPosition] of all entities.
     */
    private fun updatePositionAndRenderPosition(alpha: Float) {
        entities.forEach { entity ->
            val transformCmp = entity.transform()
            val box2dCmp = entity.box()
            val halfW = transformCmp.size.x * HALF
            val halfH = transformCmp.size.y * HALF
            val body = box2dCmp.body

            // transform position contains the previous position of the body before world.step.
            // we use it for the interpolation for the render position
            box2dCmp.renderPosition.x = MathUtils.lerp(transformCmp.position.x, body.position.x - halfW, alpha)
            box2dCmp.renderPosition.y = MathUtils.lerp(transformCmp.position.y, body.position.y - halfH, alpha)

            transformCmp.position.set(
                body.position.x - halfW,
                body.position.y - halfH,
                transformCmp.position.z
            )
        }
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) = Unit

    companion object {
        private const val STARTING_ACCUMULATOR = 0f
        private const val ACCUMULATOR_MIN_DENOMINATOR = 15f
        private const val ACCUMULATOR_MIN_NUMERATOR = 1f
        private const val HALF = 0.5f
        private const val ZERO = 0f
        private const val VELOCITY_ITERATIONS = 6
        private const val POSITION_ITERATIONS = 2
    }
}
