package dev.plotsky.cavefarming.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import dev.plotsky.cavefarming.components.RenderComponent
import dev.plotsky.cavefarming.components.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use

class RenderSystem(
    private val batch: Batch,
    private val viewport: Viewport
) : SortedIteratingSystem(
    allOf(TransformComponent::class, RenderComponent::class).get(),
    compareBy { entity: Entity -> entity[RenderComponent.mapper]?.z }
) {
    override fun update(deltaTime: Float) {
        forceSort()
        viewport.apply()
        batch.projectionMatrix = viewport.camera.combined
        batch.use(viewport.camera) {
            super.update(deltaTime)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]!!
        val render = entity[RenderComponent.mapper]!!
        render.sprite.run {
            setBounds(
                transform.bounds.x,
                transform.bounds.y,
                transform.bounds.width,
                transform.bounds.height
            )
            draw(batch)
        }
    }
}
