package dev.plotsky.cavefarming.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import ktx.ashley.get
import ktx.ashley.mapperFor

class TransformComponent : Component {
    val position = Vector3()
    val size = Vector2()

    companion object {
        val mapper = mapperFor<TransformComponent>()

        fun Entity.transform(): TransformComponent {
            return this[mapper]!!
        }
    }
}
