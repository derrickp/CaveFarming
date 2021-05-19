package dev.plotsky.cavefarming.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.utils.viewport.Viewport
import dev.plotsky.cavefarming.components.Box2DComponent.Companion.boxOrNull
import dev.plotsky.cavefarming.components.RenderComponent
import dev.plotsky.cavefarming.components.RenderComponent.Companion.render
import dev.plotsky.cavefarming.components.TransformComponent
import dev.plotsky.cavefarming.components.TransformComponent.Companion.transform
import ktx.ashley.allOf
import ktx.graphics.use

class RenderSystem(
    private val batch: Batch,
    private val viewport: Viewport,
    private val renderer: OrthogonalTiledMapRenderer
) : SortedIteratingSystem(
    allOf(TransformComponent::class, RenderComponent::class).get(),
    compareBy { entity: Entity -> entity.render().z }
) {
    override fun update(deltaTime: Float) {
        forceSort()
        viewport.apply()
        batch.projectionMatrix = viewport.camera.combined
        renderer.setView(viewport.camera as OrthographicCamera)
        batch.use(viewport.camera) {
            renderer.render()
            super.update(deltaTime)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity.transform()
        val render = entity.render()
        val box2dCmp = entity.boxOrNull()
        render.sprite.run {
            if (box2dCmp != null) {
                setBounds(
                    box2dCmp.renderPosition.x,
                    box2dCmp.renderPosition.y,
                    transform.size.x,
                    transform.size.y
                )
            } else {
                setBounds(
                    transform.position.x,
                    transform.position.y,
                    transform.size.x,
                    transform.size.y
                )
            }
            draw(batch)
        }
    }
}
