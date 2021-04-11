package dev.plotsky.cavefarming.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.utils.viewport.Viewport
import dev.plotsky.cavefarming.components.CharacterComponent
import dev.plotsky.cavefarming.components.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.get

class CameraMoveSystem(
    private val viewport: Viewport
) : IteratingSystem(
    allOf(TransformComponent::class, CharacterComponent::class).get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]!!
        viewport.camera.position.set(transform.bounds.x, transform.bounds.y, 0f)
    }
}
