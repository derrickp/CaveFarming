package dev.plotsky.cavefarming.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import ktx.ashley.get
import ktx.ashley.mapperFor

class InteractComponent : Component {
    var interact = false

    companion object {
        val mapper = mapperFor<InteractComponent>()

        fun Entity.interact(): InteractComponent {
            return this[mapper]!!
        }
    }
}
