package dev.plotsky.cavefarming.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import ktx.ashley.get
import ktx.ashley.mapperFor

class Box2DComponent : Component {
    lateinit var body: Body
    val renderPosition = Vector2()
    val impulse = Vector2()

    companion object {
        val mapper = mapperFor<Box2DComponent>()
        val TMP_VECTOR2 = Vector2()

        fun Entity.box(): Box2DComponent {
            return this[mapper]!!
        }

        fun Entity.boxOrNull(): Box2DComponent? {
            return this[mapper]
        }
    }
}
