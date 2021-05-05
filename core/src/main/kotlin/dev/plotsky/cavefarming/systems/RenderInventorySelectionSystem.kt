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
                    font.draw(
                        it,
                        "${crop.index + ONE}: ${crop.value.cropType}",
                        CROP_TEXT_X,
                        CROP_TEXT_Y - crop.index * CROP_TEXT_Y_MODIFIER
                    )
                }
                font.draw(
                    it,
                    "Current: ${inventory.currentCropConfiguration.cropType}",
                    CROP_TEXT_X,
                    CURRENT_CROP_Y
                )

                font.draw(
                    it,
                    "Current harvested number of crop types: ${inventory.items.keys.size}",
                    CROP_TEXT_X,
                    HARVESTED_Y
                )
            }
        }
    }

    companion object {
        private const val ONE = 1
        private const val CROP_TEXT_X = 100f
        private const val CROP_TEXT_Y = 400f
        private const val HARVESTED_Y = 100f
        private const val CROP_TEXT_Y_MODIFIER = 20f
        private const val CURRENT_CROP_Y = 200f
    }
}
