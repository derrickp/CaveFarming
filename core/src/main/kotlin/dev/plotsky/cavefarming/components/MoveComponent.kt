package dev.plotsky.cavefarming.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Interpolation
import ktx.ashley.mapperFor

class MoveComponent : Component {
    var speedBase = DEFAULT_SPEED
    var maxSpeed = MAX_SPEED
    var alpha = DEFAULT_ALPHA
    var accInterpolation: Interpolation = Interpolation.exp10Out
    var cosDeg = COS_DEGREE
    var sinDeg = SIN_DEGREE
    var root = false

    companion object {
        val mapper = mapperFor<MoveComponent>()
        private const val DEFAULT_ALPHA = 0f
        private const val DEFAULT_SPEED = 0f
        private const val MAX_SPEED = 5f
        private const val COS_DEGREE = 0f
        private const val SIN_DEGREE = 0f
    }
}
