package dev.plotsky.cavefarming.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Rectangle
import dev.plotsky.cavefarming.crops.CropConfiguration
import dev.plotsky.cavefarming.crops.GrowthStage
import ktx.ashley.mapperFor

class CropComponent : Component {
    lateinit var configuration: CropConfiguration
    val growingBounds = Rectangle()
    var ageTick = 0
    lateinit var growthStage: GrowthStage

    companion object {
        val mapper = mapperFor<CropComponent>()
    }
}
