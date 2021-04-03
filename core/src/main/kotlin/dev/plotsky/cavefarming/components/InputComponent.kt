package dev.plotsky.cavefarming.components

import com.badlogic.ashley.core.Component
import dev.plotsky.cavefarming.inputs.InteractionInput
import dev.plotsky.cavefarming.inputs.MovementInput
import ktx.ashley.mapperFor

class InputComponent : Component {
    companion object {
        val mapper = mapperFor<InputComponent>()
    }

    var movementKeys = mutableListOf<MovementInput>()
    var interactionKeys = mutableListOf<InteractionInput>()
}
