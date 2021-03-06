package dev.plotsky.cavefarming.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import dev.plotsky.cavefarming.assets.caveFarmingAtlas
import dev.plotsky.cavefarming.components.CropComponent
import dev.plotsky.cavefarming.components.CropComponent.Companion.crop
import dev.plotsky.cavefarming.components.InteractComponent
import dev.plotsky.cavefarming.components.InteractComponent.Companion.interact
import dev.plotsky.cavefarming.components.InventoryComponent
import dev.plotsky.cavefarming.components.InventoryComponent.Companion.inventory
import dev.plotsky.cavefarming.components.NameComponent
import dev.plotsky.cavefarming.components.RenderComponent
import dev.plotsky.cavefarming.components.TransformComponent
import dev.plotsky.cavefarming.components.TransformComponent.Companion.transform
import dev.plotsky.cavefarming.crops.CropConfiguration
import dev.plotsky.cavefarming.crops.CropGrid.buildCropGrid
import dev.plotsky.cavefarming.inventory.Items
import ktx.ashley.allOf
import ktx.ashley.entity
import ktx.ashley.with

class ActionsSystem(
    private val engine: PooledEngine,
    private val assetManager: AssetManager
) : IteratingSystem(
    allOf(InteractComponent::class, TransformComponent::class, InventoryComponent::class).get()
) {
    private val cropFamily: Family by lazy {
        Family.all(CropComponent::class.java).get()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity.interact().let { interact ->
            if (interact.interact) {
                handleInteraction(entity)
                interact.interact = false
            }
        }
    }

    private fun handleInteraction(player: Entity) {
        val overlapping = retrieveOverlappingCrop(player)

        if (overlapping != null) {
            harvest(player, overlapping)
        } else {
            plantCrop(player)
        }
    }

    private fun retrieveOverlappingCrop(entity: Entity): Entity? {
        val transform = entity.transform()
        val x = transform.position.x + transform.size.x / 2
        val y = transform.position.y + transform.size.y / 2
        val where = Vector2(x, y)
        // Go through all crops
        // If inside any crop square, add to inventory, and remove from engine
        val existingCrops = engine.getEntitiesFor(cropFamily)
        return existingCrops.firstOrNull { cropEntity ->
            val existingGrowingBounds = cropEntity.crop().growingBounds
            existingGrowingBounds.contains(where)
        }
    }

    private fun harvest(player: Entity, cropEntity: Entity) {
        val cropComponent = cropEntity.crop()

        if (!cropComponent.growthStage.harvestable) {
            return
        }

        val inventoryComponent = player.inventory()
        val item = Items.itemByCropType(cropComponent.configuration.cropType)
        inventoryComponent.items.putIfAbsent(item, 0)
        inventoryComponent.items[item]?.plus(1)

        engine.removeEntity(cropEntity)
    }

    private fun plantCrop(player: Entity) {
        player.transform().let { transform ->
            val where = Vector2(transform.position.x, transform.position.y)
            val inventory = player.inventory()
            spawnCropGrid(where, inventory.currentCropConfiguration)
        }
    }

    private fun spawnCrop(where: Vector2, configuration: CropConfiguration) {
        if (isOutOfBounds(where)) {
            return
        }
        val existingCrops = engine.getEntitiesFor(cropFamily)
        val growingBounds = Rectangle(
            where.x,
            where.y,
            configuration.areaNeededPerCrop.first,
            configuration.areaNeededPerCrop.second
        )
        val overlapping = existingCrops.any { cropEntity ->
            val existingGrowingBounds = cropEntity.crop().growingBounds
            existingGrowingBounds.overlaps(growingBounds)
        }
        if (overlapping) {
            return
        }
        engine.entity {
            val lifeStage = configuration.growthStages.first()
            with<NameComponent> { name = "crop" }
            with<TransformComponent> {
                position.set(where, 0f)
                size.set(1f, 1f)
            }
            val region = assetManager.caveFarmingAtlas().findRegion(lifeStage.regionName)
            with<RenderComponent> {
                sprite.setRegion(region)
                z = 1
            }
            with<CropComponent> {
                this.configuration = configuration
                this.growingBounds.set(growingBounds)
                this.growthStage = lifeStage
            }
        }
    }

    private fun spawnCropGrid(where: Vector2, configuration: CropConfiguration) {
        val gridCells = buildCropGrid(where, configuration)
        for (cell in gridCells) {
            spawnCrop(cell, configuration)
        }
    }

    private fun isOutOfBounds(where: Vector2): Boolean {
        return where.x < MIN_X || where.x > MAX_X || where.y < MIN_Y || where.y > MAX_Y
    }

    companion object {
        private const val MIN_X = 0
        private const val MAX_X = 39
        private const val MIN_Y = 0
        private const val MAX_Y = 21.75
    }
}
