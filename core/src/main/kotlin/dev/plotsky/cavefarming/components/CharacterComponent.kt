package dev.plotsky.cavefarming.components

import com.badlogic.ashley.core.Component
import ktx.ashley.mapperFor

class CharacterComponent : Component {
    companion object {
        val mapper = mapperFor<CharacterComponent>()
    }
}
