package dev.plotsky.cavefarming.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import dev.plotsky.cavefarming.components.Box2DComponent
import dev.plotsky.cavefarming.components.MoveComponent
import dev.plotsky.cavefarming.components.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.get
import kotlin.math.max
import kotlin.math.min

class MoveSystem : IteratingSystem(allOf(TransformComponent::class, MoveComponent::class).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        with(entity[MoveComponent.mapper]!!) {
            val box2dCmp = entity[Box2DComponent.mapper]!!
            if (root) {
                box2dCmp.impulse.x = box2dCmp.body.mass * (0f - box2dCmp.body.linearVelocity.x)
                box2dCmp.impulse.y = box2dCmp.body.mass * (0f - box2dCmp.body.linearVelocity.y)
            } else {
                alpha = max(0f, min(1f, alpha + deltaTime))
                speedBase = accInterpolation.apply(0f, maxSpeed, alpha)

                // calculate impulse to apply
                with(box2dCmp.body) {
                    box2dCmp.impulse.x = mass * (speedBase * cosDeg - linearVelocity.x)
                    box2dCmp.impulse.y = mass * (speedBase * sinDeg - linearVelocity.y)
                }
            }
        }
    }
}
