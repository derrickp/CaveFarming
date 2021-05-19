package dev.plotsky.cavefarming.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.utils.viewport.Viewport
import dev.plotsky.cavefarming.components.CharacterComponent
import dev.plotsky.cavefarming.components.TransformComponent
import dev.plotsky.cavefarming.components.TransformComponent.Companion.transform
import ktx.ashley.allOf

class CameraMoveSystem(
    private val viewport: Viewport
) : IteratingSystem(
    allOf(TransformComponent::class, CharacterComponent::class).get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform()
        viewport.camera.position.set(transform.position.x, transform.position.y, 0f)
    }
}
