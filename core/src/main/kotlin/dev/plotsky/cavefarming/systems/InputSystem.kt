package dev.plotsky.cavefarming.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import dev.plotsky.cavefarming.inputs.MovementInput
import dev.plotsky.cavefarming.components.InputComponent
import ktx.ashley.allOf
import ktx.ashley.get

class InputSystem : IteratingSystem(allOf(InputComponent::class).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[InputComponent.mapper]?.let { input ->
            input.movementKeys.clear()
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                input.movementKeys.add(MovementInput.RIGHT)
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                input.movementKeys.add(MovementInput.LEFT)
            }

            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                input.movementKeys.add(MovementInput.UP)
            }

            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                input.movementKeys.add(MovementInput.DOWN)
            }
        }
    }
}
