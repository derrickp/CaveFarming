package dev.plotsky.cavefarming.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IntervalSystem
import com.badlogic.gdx.assets.AssetManager
import dev.plotsky.cavefarming.GROWING_INTERVAL
import dev.plotsky.cavefarming.assets.caveFarmingAtlas
import dev.plotsky.cavefarming.components.CropComponent
import dev.plotsky.cavefarming.components.CropComponent.Companion.crop
import dev.plotsky.cavefarming.components.RenderComponent
import dev.plotsky.cavefarming.components.RenderComponent.Companion.render
import kotlin.random.Random

class GrowthSystem(private val assetManager: AssetManager) : IntervalSystem(
    GROWING_INTERVAL
) {
    private val cropFamily: Family by lazy {
        Family.all(CropComponent::class.java, RenderComponent::class.java).get()
    }

    override fun updateInterval() {
        val crops = engine.getEntitiesFor(cropFamily)

        for (crop in crops) {
            progressCrop(crop)
        }
    }

    private fun progressCrop(entity: Entity) {
        val cropComponent = entity.crop()
        cropComponent.ageTick = cropComponent.ageTick + 1
        if (cropComponent.ageTick <= cropComponent.growthStage.timeInStage) {
            return
        }

        val chance = Random.nextDouble(0.0, 1.0)
        if (chance > cropComponent.growthStage.chanceToProgressFromStage) {
            return
        }

        val currentIndex = cropComponent.configuration.growthStages.indexOf(cropComponent.growthStage)
        val nextStage = cropComponent.configuration.growthStages.getOrNull(currentIndex + 1)
        if (nextStage == null) {
            engine.removeEntity(entity)
        } else {
            cropComponent.ageTick = 0
            cropComponent.growthStage = nextStage
            val renderComponent = entity.render()
            val region = assetManager.caveFarmingAtlas()
                .findRegion(cropComponent.growthStage.regionName)
            renderComponent.sprite.setRegion(region)
        }
    }
}
