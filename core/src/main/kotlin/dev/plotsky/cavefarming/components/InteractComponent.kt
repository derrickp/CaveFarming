package dev.plotsky.cavefarming.components

import com.badlogic.ashley.core.Component
import ktx.ashley.mapperFor

class InteractComponent : Component {
    var interact = false
    var harvest = false

    companion object {
        val mapper = mapperFor<InteractComponent>()
    }
}
