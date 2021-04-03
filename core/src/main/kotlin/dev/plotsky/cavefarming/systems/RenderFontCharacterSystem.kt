package dev.plotsky.cavefarming.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import dev.plotsky.cavefarming.components.FontCharacterComponent
import dev.plotsky.cavefarming.components.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use

class RenderFontCharacterSystem(
    private val batch: Batch,
    private val font: BitmapFont,
    private val camera: OrthographicCamera
) : SortedIteratingSystem(
    allOf(TransformComponent::class, FontCharacterComponent::class).get(),
    compareBy { entity: Entity -> entity[FontCharacterComponent.mapper]?.z }
) {
    override fun update(deltaTime: Float) {
        forceSort()
        camera.update()
        batch.projectionMatrix = camera.combined
        batch.use {
            super.update(deltaTime)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[TransformComponent.mapper]?.let { transform ->
            entity[FontCharacterComponent.mapper]?.let { fontCharacter ->
                val text = "[${fontCharacter.color}]${fontCharacter.character}"
                font.data.markupEnabled = true
                font.draw(
                    batch,
                    text,
                    transform.bounds.x,
                    transform.bounds.y
                )
            }
        }
    }
}
