package dev.plotsky.cavefarming.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import dev.plotsky.cavefarming.assets.TextureAtlasAssets
import dev.plotsky.cavefarming.assets.get
import dev.plotsky.cavefarming.components.CropComponent
import dev.plotsky.cavefarming.components.InteractComponent
import dev.plotsky.cavefarming.components.InventoryComponent
import dev.plotsky.cavefarming.components.NameComponent
import dev.plotsky.cavefarming.components.RenderComponent
import dev.plotsky.cavefarming.components.TransformComponent
import dev.plotsky.cavefarming.crops.CropConfiguration
import dev.plotsky.cavefarming.crops.CropGrid.buildCropGrid
import dev.plotsky.cavefarming.crops.CropConfigurations.kane
import dev.plotsky.cavefarming.crops.CropConfigurations.mushroom
import dev.plotsky.cavefarming.crops.CropConfigurations.potato
import dev.plotsky.cavefarming.crops.CropConfigurations.turnip
import dev.plotsky.cavefarming.crops.CropType
import dev.plotsky.cavefarming.crops.GrowthStage
import ktx.ashley.allOf
import ktx.ashley.entity
import ktx.ashley.get
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
        entity[InteractComponent.mapper]?.let { interact ->
            if (interact.interact) {
                handlePressedPlantCrop(entity)
                interact.interact = false
            }
        }
    }

    private fun handlePressedPlantCrop(entity: Entity) {
        entity[TransformComponent.mapper]?.let { transform ->
            val where = Vector2(transform.bounds.x, transform.bounds.y)

            when (entity[InventoryComponent.mapper]!!.currentCrop) {
                CropType.MUSHROOMS -> spawnMushroom(where)
                CropType.TURNIPS -> spawnTurnip(where)
                CropType.KANES -> spawnKane(where)
                CropType.POTATOES -> spawnPotato(where)
            }
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
        val overlapping = existingCrops.any { crop ->
            val existingGrowingBounds = crop[CropComponent.mapper]?.growingBounds
            existingGrowingBounds?.overlaps(growingBounds) ?: false
        }
        if (overlapping) {
            return
        }
        engine.entity {
            with<NameComponent> { name = "crop" }
            with<TransformComponent> {
                bounds.set(
                    Rectangle(
                        where.x,
                        where.y,
                        1f,
                        1f
                    )
                )
            }
            val region = assetManager[TextureAtlasAssets.CaveFarming].findRegion(configuration.seedRegionName)
            with<RenderComponent> {
                sprite.setRegion(region)
                z = 1
            }
            with<CropComponent> {
                this.configuration = configuration
                this.growingBounds.set(growingBounds)
                this.growthStage = GrowthStage.SEED
            }
        }
    }

    private fun spawnMushroom(where: Vector2) {
        val gridCells = buildCropGrid(Vector2(where.x, where.y), mushroom)
        for (cell in gridCells) {
            spawnCrop(cell, mushroom)
        }
    }

    private fun spawnTurnip(where: Vector2) {
        val gridCells = buildCropGrid(Vector2(where.x, where.y), turnip)
        for (cell in gridCells) {
            spawnCrop(cell, turnip)
        }
    }

    private fun spawnKane(where: Vector2) {
        val gridCells = buildCropGrid(Vector2(where.x, where.y), kane)
        for (cell in gridCells) {
            spawnCrop(cell, kane)
        }
    }

    private fun spawnPotato(where: Vector2) {
        val gridCells = buildCropGrid(Vector2(where.x, where.y), potato)
        for (cell in gridCells) {
            spawnCrop(cell, potato)
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
