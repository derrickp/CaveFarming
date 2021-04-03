package dev.plotsky.cavefarming.components

import com.badlogic.ashley.core.Component
import ktx.ashley.mapperFor

class NameComponent : Component {
    companion object {
        val mapper = mapperFor<NameComponent>()
    }

    var name = ""
}
