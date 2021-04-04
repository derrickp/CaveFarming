package dev.plotsky.cavefarming.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Rectangle
import dev.plotsky.cavefarming.components.*
import dev.plotsky.cavefarming.inputs.InteractionInput
import ktx.ashley.allOf
import ktx.ashley.entity
import ktx.ashley.get
import ktx.ashley.with

class ActionsSystem(private val engine: PooledEngine) : IteratingSystem(
    allOf(InputComponent::class, TransformComponent::class).get()
) {
    private val cropFamily: Family by lazy {
        Family.all(CropComponent::class.java).get()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[InputComponent.mapper]?.let { input ->
            if (input.interactionKeys.contains(InteractionInput.M)) {
                handlePressedPlantCrop(entity)
            }
        }
    }

    private fun handlePressedPlantCrop(entity: Entity) {
        val existingCrops = engine.getEntitiesFor(cropFamily)
        entity[TransformComponent.mapper]?.let { transform ->
            val possibleBounds = Rectangle().apply {
                x = transform.bounds.x
                y = transform.bounds.y
                height = 25f
                width = 25f
            }
            val overlapping = existingCrops.any { crop ->
                crop[TransformComponent.mapper]?.bounds?.overlaps(possibleBounds) ?: false
            }

            if (overlapping) {
                return
            }

            spawnMushroom(possibleBounds)
        }
    }

    private fun spawnMushroom(where: Rectangle) {
        engine.entity {
            with<NameComponent> { name = "crop" }
            with<TransformComponent> {
                bounds.set(where)
            }
            with<FontCharacterComponent> {
                character = "m"
                color = "BROWN"
                z = 50
            }
            with<CropComponent> { type = "mushroom" }
        }
    }
}
