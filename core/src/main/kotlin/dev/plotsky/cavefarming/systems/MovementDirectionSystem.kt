package dev.plotsky.cavefarming.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import dev.plotsky.cavefarming.components.InputComponent
import dev.plotsky.cavefarming.components.MoveComponent
import dev.plotsky.cavefarming.inputs.MovementInput
import ktx.ashley.allOf
import ktx.ashley.get

class MovementDirectionSystem : IteratingSystem(
    allOf(InputComponent::class, MoveComponent::class).get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[InputComponent.mapper]?.let { input ->
            entity[MoveComponent.mapper]?.let { move ->
                handleMovement(input, move)
            }
        }
    }

    private fun handleMovement(input: InputComponent, move: MoveComponent) {
        if (input.movementKeys.isEmpty()) {
            move.speed.x = 0f
            move.speed.y = 0f
            return
        }

        when {
            input.movementKeys.contains(MovementInput.RIGHT) -> {
                move.speed.x = 16f * 0.6f
            }
            input.movementKeys.contains(MovementInput.LEFT) -> {
                move.speed.x = -16f * 0.6f
            }
            else -> {
                move.speed.x = 0f
            }
        }

        when {
            input.movementKeys.contains(MovementInput.UP) -> {
                move.speed.y = 9f * 0.6f
            }
            input.movementKeys.contains(MovementInput.DOWN) -> {
                move.speed.y = -9f * 0.6f
            }
            else -> {
                move.speed.y = 0f
            }
        }
    }
}
