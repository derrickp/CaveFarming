package dev.plotsky.cavefarming.systems

import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IntervalSystem
import com.badlogic.gdx.assets.AssetManager
import dev.plotsky.cavefarming.GROWING_INTERVAL
import dev.plotsky.cavefarming.assets.TextureAtlasAssets
import dev.plotsky.cavefarming.assets.get
import dev.plotsky.cavefarming.components.CropComponent
import dev.plotsky.cavefarming.components.RenderComponent
import dev.plotsky.cavefarming.crops.GrowthStage
import ktx.ashley.get
import kotlin.random.Random

class GrowthSystem(private val assetManager: AssetManager) : IntervalSystem(
    GROWING_INTERVAL
) {
    private val cropFamily: Family by lazy {
        Family.all(CropComponent::class.java, RenderComponent::class.java).get()
    }

    private fun growCrop(crop: CropComponent, render: RenderComponent) {
        val chance = Random.nextDouble(0.0, 1.0)
        if (chance > crop.configuration.chanceGrowAfterSeason) {
            return
        }

        crop.growthStage = GrowthStage.PLANT
        val region = assetManager[TextureAtlasAssets.CaveFarming]
            .findRegion(crop.configuration.cropRegionName)
        render.sprite.setRegion(region)
    }

    override fun updateInterval() {
        val groupedEntities = engine.getEntitiesFor(cropFamily)
            .groupBy {
                it[CropComponent.mapper]!!.growthStage
            }

        val seeds = groupedEntities[GrowthStage.SEED] ?: emptyList()
        for (seed in seeds) {
            val cropComponent = seed[CropComponent.mapper]!!
            cropComponent.ageTick = cropComponent.ageTick + 1
            if (cropComponent.ageTick > cropComponent.configuration.growingSeasonLength) {
                cropComponent.growthStage = GrowthStage.SPROUT
            }
        }

        val sprouts = groupedEntities[GrowthStage.SPROUT] ?: emptyList()

        if (sprouts.isEmpty()) {
            return
        }

        val sproutIndex = Random.nextInt(sprouts.size)
        val sprout = sprouts.get(index = sproutIndex)
        val cropComponent = sprout[CropComponent.mapper]!!
        val renderComponent = sprout[RenderComponent.mapper]!!

        growCrop(cropComponent, renderComponent)
    }
}
