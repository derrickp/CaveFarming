package dev.plotsky.cavefarming.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import ktx.ashley.mapperFor

class MoveComponent : Component {
    var speedBase = 0f
    var maxSpeed = 5f
    var alpha = 0f
    var accInterpolation: Interpolation = Interpolation.exp10Out
    var cosDeg = 0f
    var sinDeg = 0f
    var root = false

    companion object {
        val mapper = mapperFor<MoveComponent>()
    }
}
