package dev.plotsky.cavefarming.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import dev.plotsky.cavefarming.components.InventoryComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use

class RenderInventorySelectionSystem(
    private val batch: Batch,
    private val font: BitmapFont
) : IteratingSystem(
    allOf(InventoryComponent::class).get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[InventoryComponent.mapper]?.let { inventory ->
            batch.use {
                inventory.crops.withIndex().forEach { crop ->
                    font.draw(it, "${crop.index + 1}: ${crop.value}", 100f, 400f - crop.index * 20f)
                }
                font.draw(it, "Current: ${inventory.currentCrop}", 100f, 200f)
            }
        }
    }

}
