package dev.plotsky.cavefarming.components

import com.badlogic.ashley.core.Component
import dev.plotsky.cavefarming.crops.CropType
import ktx.ashley.mapperFor

class CropComponent : Component {
    companion object {
        val mapper = mapperFor<CropComponent>()
    }

    var cropType: CropType = CropType.MUSHROOMS
}
