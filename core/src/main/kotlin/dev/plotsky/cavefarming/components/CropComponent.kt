package dev.plotsky.cavefarming.components

import com.badlogic.ashley.core.Component
import ktx.ashley.mapperFor

class CropComponent : Component {
    companion object {
        val mapper = mapperFor<CropComponent>()
    }

    var type = ""
}
