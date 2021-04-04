package dev.plotsky.cavefarming.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import dev.plotsky.cavefarming.components.InventoryComponent
import ktx.ashley.allOf
import ktx.ashley.get

class ChooseCropSystem : IteratingSystem(
    allOf(InventoryComponent::class).get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[InventoryComponent.mapper]?.let {
            when {
                Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) -> {
                    it.currentCrop = it.crops[0]
                }
                Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) -> {
                    it.currentCrop = it.crops[1]
                }
                Gdx.input.isKeyJustPressed(Input.Keys.NUM_3) -> {
                    it.currentCrop = it.crops[2]
                }
                Gdx.input.isKeyJustPressed(Input.Keys.NUM_4) -> {
                    it.currentCrop = it.crops[3]
                }
            }

        }
    }
}
