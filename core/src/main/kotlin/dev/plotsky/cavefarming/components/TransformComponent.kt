package dev.plotsky.cavefarming.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Rectangle
import ktx.ashley.mapperFor

class TransformComponent : Component {
    companion object {
        val mapper = mapperFor<TransformComponent>()
    }

    val bounds = Rectangle()
}