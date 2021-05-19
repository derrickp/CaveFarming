package dev.plotsky.cavefarming.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import dev.plotsky.cavefarming.components.InventoryComponent
import dev.plotsky.cavefarming.components.InventoryComponent.Companion.inventory
import ktx.ashley.allOf

class ChooseCropSystem : IteratingSystem(
    allOf(InventoryComponent::class).get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity.inventory().let {
            when {
                Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) -> {
                    it.currentCropConfiguration = it.crops[FIRST_CROP_INDEX]
                }
                Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) -> {
                    it.currentCropConfiguration = it.crops[SECOND_CROP_INDEX]
                }
                Gdx.input.isKeyJustPressed(Input.Keys.NUM_3) -> {
                    it.currentCropConfiguration = it.crops[THIRD_CROP_INDEX]
                }
                Gdx.input.isKeyJustPressed(Input.Keys.NUM_4) -> {
                    it.currentCropConfiguration = it.crops[FOURTH_CROP_INDEX]
                }
            }
        }
    }

    companion object {
        private const val FIRST_CROP_INDEX = 0
        private const val SECOND_CROP_INDEX = 1
        private const val THIRD_CROP_INDEX = 2
        private const val FOURTH_CROP_INDEX = 3
    }
}
