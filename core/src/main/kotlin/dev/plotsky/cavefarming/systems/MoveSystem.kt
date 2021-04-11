package dev.plotsky.cavefarming.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import dev.plotsky.cavefarming.components.MoveComponent
import dev.plotsky.cavefarming.components.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.get

class MoveSystem : IteratingSystem(allOf(TransformComponent::class, MoveComponent::class).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[TransformComponent.mapper]?.let { transform ->
            entity[MoveComponent.mapper]?.let { move ->
                // make sure the entities stay within the screen bounds
                transform.bounds.x = calculateX(transform.bounds, move.speed, deltaTime)
                transform.bounds.y = calculateY(transform.bounds, move.speed, deltaTime)
            }
        }
    }

    private fun calculateX(bounds: Rectangle, speed: Vector2, deltaTime: Float): Float {
        return MathUtils.clamp(bounds.x + speed.x * deltaTime, 0f, 40f - 1f)
    }

    private fun calculateY(bounds: Rectangle, speed: Vector2, deltaTime: Float): Float {
        return MathUtils.clamp(bounds.y + speed.y * deltaTime, 0f, 22.75f - 1f)
    }
}
