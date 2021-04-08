package dev.plotsky.cavefarming.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.math.Rectangle
import dev.plotsky.cavefarming.Crop
import dev.plotsky.cavefarming.assets.TextureAtlasAssets
import dev.plotsky.cavefarming.assets.get
import dev.plotsky.cavefarming.components.CropComponent
import dev.plotsky.cavefarming.components.InputComponent
import dev.plotsky.cavefarming.components.InventoryComponent
import dev.plotsky.cavefarming.components.NameComponent
import dev.plotsky.cavefarming.components.RenderComponent
import dev.plotsky.cavefarming.components.TransformComponent
import dev.plotsky.cavefarming.inputs.InteractionInput
import ktx.ashley.allOf
import ktx.ashley.entity
import ktx.ashley.get
import ktx.ashley.with

class ActionsSystem(
    private val engine: PooledEngine,
    private val assetManager: AssetManager
) : IteratingSystem(
    allOf(InputComponent::class, TransformComponent::class, InventoryComponent::class).get()
) {
    private val cropFamily: Family by lazy {
        Family.all(CropComponent::class.java).get()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[InputComponent.mapper]?.let { input ->
            if (input.interactionKeys.contains(InteractionInput.SPACE)) {
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
                height = 0.6f
                width = 0.6f
            }
            val overlapping = existingCrops.any { crop ->
                crop[TransformComponent.mapper]?.bounds?.overlaps(possibleBounds) ?: false
            }

            if (overlapping) {
                return
            }

            when (entity[InventoryComponent.mapper]!!.currentCrop) {
                Crop.MUSHROOMS -> spawnMushroom(possibleBounds)
                Crop.TURNIPS -> spawnTurnip(possibleBounds)
                Crop.KANES -> spawnKane(possibleBounds)
                Crop.POTATOES -> spawnPotato(possibleBounds)
            }
        }
    }

    private fun spawnMushroom(where: Rectangle) {
        engine.entity {
            with<NameComponent> { name = "crop" }
            with<TransformComponent> {
                bounds.set(where)
            }
            with<RenderComponent> {
                sprite.setRegion(assetManager[TextureAtlasAssets.CaveFarming].findRegion("mushroom"))
            }
            with<CropComponent> { type = "mushroom" }
        }
    }

    private fun spawnTurnip(where: Rectangle) {
        engine.entity {
            with<NameComponent> { name = "crop" }
            with<TransformComponent> {
                bounds.set(where)
            }
            with<RenderComponent> {
                sprite.setRegion(assetManager[TextureAtlasAssets.CaveFarming].findRegion("turnip"))
            }
            with<CropComponent> { type = "turnip" }
        }
    }

    private fun spawnKane(where: Rectangle) {
        engine.entity {
            with<NameComponent> { name = "crop" }
            with<TransformComponent> {
                bounds.set(where)
            }
            with<RenderComponent> {
                sprite.setRegion(assetManager[TextureAtlasAssets.CaveFarming].findRegion("kane"))
            }
            with<CropComponent> { type = "kane" }
        }
    }

    private fun spawnPotato(where: Rectangle) {
        engine.entity {
            with<NameComponent> { name = "crop" }
            with<TransformComponent> {
                bounds.set(where)
            }
            with<RenderComponent> {
                sprite.setRegion(assetManager[TextureAtlasAssets.CaveFarming].findRegion("potato"))
            }
            with<CropComponent> { type = "potato" }
        }
    }
}
