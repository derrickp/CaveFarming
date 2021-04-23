package dev.plotsky.cavefarming.components

import com.badlogic.ashley.core.Component
import ktx.ashley.mapperFor

class NameComponent : Component {
    var name = ""

    companion object {
        val mapper = mapperFor<NameComponent>()
    }
}
