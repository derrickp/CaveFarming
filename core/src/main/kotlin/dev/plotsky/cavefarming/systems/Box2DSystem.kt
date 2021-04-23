package dev.plotsky.cavefarming.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.World
import dev.plotsky.cavefarming.components.Box2DComponent
import dev.plotsky.cavefarming.components.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.get
import kotlin.math.min

class Box2DSystem(
    private val world: World,
) : IteratingSystem(allOf(Box2DComponent::class, TransformComponent::class).get()) {
    private var accumulator = 0f

    /**
     * Updates the [world] using a fixed time step of deltaTime
     */
    override fun update(deltaTime: Float) {
        if (world.autoClearForces) {
            world.autoClearForces = false
        }

        accumulator += min(1 / 30f, deltaTime)
        while (accumulator >= deltaTime) {
            updatePrevPositionAndApplyForces()
            world.step(deltaTime, 6, 2)
            accumulator -= deltaTime
        }
        world.clearForces()

        updatePositionAndRenderPosition(accumulator / deltaTime)
    }

    /**
     * Applies an impulse once to all entities and updates their [TransformComponent.bounds] to the position
     * before calling [World.step].
     */
    private fun updatePrevPositionAndApplyForces() {
        entities.forEach { entity ->
            val transformCmp = entity[TransformComponent.mapper]!!
            val box2dCmp = entity[Box2DComponent.mapper]!!
            val halfW = transformCmp.bounds.width * 0.5f
            val halfH = transformCmp.bounds.height * 0.5f
            val body = box2dCmp.body

            transformCmp.bounds.x = body.position.x - halfW
            transformCmp.bounds.y = body.position.y - halfH

            if (!box2dCmp.impulse.isZero) {
                // apply non-zero impulse once before a call to world.step
//                body.applyForceToCenter(box2dCmp.impulse, true)
                body.applyLinearImpulse(box2dCmp.impulse, body.worldCenter, true)
                box2dCmp.impulse.set(0f, 0f)
            }
        }
    }

    /**
     * Updates the [TransformComponent.position] and [Box2DComponent.renderPosition] of all entities.
     */
    private fun updatePositionAndRenderPosition(alpha: Float) {
        entities.forEach { entity ->
            val transformCmp = entity[TransformComponent.mapper]!!
            val box2dCmp = entity[Box2DComponent.mapper]!!
            val halfW = transformCmp.bounds.width * 0.5f
            val halfH = transformCmp.bounds.height * 0.5f
            val body = box2dCmp.body

            // transform position contains the previous position of the body before world.step.
            // we use it for the interpolation for the render position
            box2dCmp.renderPosition.x = MathUtils.lerp(transformCmp.bounds.x, body.position.x - halfW, alpha)
            box2dCmp.renderPosition.y = MathUtils.lerp(transformCmp.bounds.y, body.position.y - halfH, alpha)

            transformCmp.bounds.x = body.position.x - halfW
            transformCmp.bounds.y = body.position.y - halfH
            // update transform position to correct body position
//            transformCmp.position.set(
//                body.position.x - halfW,
//                body.position.y - halfH,
//                transformCmp.position.z,
//            )
        }
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) = Unit
}
