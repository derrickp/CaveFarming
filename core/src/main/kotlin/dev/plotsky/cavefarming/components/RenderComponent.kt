package dev.plotsky.cavefarming.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Sprite
import ktx.ashley.mapperFor

class RenderComponent : Component {
    val sprite = Sprite()
    var z = 0

    companion object {
        val mapper = mapperFor<RenderComponent>()
    }
}
