package dev.plotsky.cavefarming.components

import com.badlogic.ashley.core.Component
import ktx.ashley.mapperFor

class FontCharacterComponent : Component {
    companion object {
        val mapper = mapperFor<FontCharacterComponent>()
    }

    var character: String = ""
    var color: String = "WHITE"
    var z = 0
}
