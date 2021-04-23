package dev.plotsky.cavefarming.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.MathUtils
import dev.plotsky.cavefarming.components.CharacterComponent
import dev.plotsky.cavefarming.components.InteractComponent
import dev.plotsky.cavefarming.components.MoveComponent
import ktx.ashley.allOf
import ktx.ashley.get
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2

class PlayerControlSystem : InputProcessor,
    IteratingSystem(allOf(CharacterComponent::class).get()) {
    private var valueLeftX = 0f
    private var valueLeftY = 0f
    private var stopMovement = true
    private var moveDirectionDeg = 0f
    private var actionPressed = false

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        Gdx.input.inputProcessor = this
    }

    override fun removedFromEngine(engine: Engine?) {
        super.removedFromEngine(engine)
        Gdx.input.inputProcessor = null
    }

    override fun setProcessing(processing: Boolean) {
        super.setProcessing(processing)
        if (processing) {
            Gdx.input.inputProcessor = this
        } else {
            Gdx.input.inputProcessor = null
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.RIGHT -> updateMovementValues(1f, valueLeftY)
            Input.Keys.LEFT -> updateMovementValues(-1f, valueLeftY)
            Input.Keys.UP -> updateMovementValues(valueLeftX, -1f)
            Input.Keys.DOWN -> updateMovementValues(valueLeftX, 1f)
            Input.Keys.SPACE -> actionPressed = true
            Input.Keys.I -> {
                updateMovementValues(0f, 0f)
            }
            else -> return false
        }

        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.RIGHT -> {
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    updateMovementValues(-1f, valueLeftY)
                } else {
                    updateMovementValues(0f, valueLeftY)
                }
            }
            Input.Keys.LEFT -> {
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    updateMovementValues(1f, valueLeftY)
                } else {
                    updateMovementValues(0f, valueLeftY)
                }
            }
            Input.Keys.UP -> {
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    updateMovementValues(valueLeftX, 1f)
                } else {
                    updateMovementValues(valueLeftX, 0f)
                }
            }
            Input.Keys.DOWN -> {
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    updateMovementValues(valueLeftX, -1f)
                } else {
                    updateMovementValues(valueLeftX, 0f)
                }
            }
            Input.Keys.SPACE -> actionPressed = false
            else -> return false
        }

        return true
    }

    override fun keyTyped(character: Char) = false

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int) = false

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int) = false

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int) = false

    override fun mouseMoved(screenX: Int, screenY: Int) = false

    override fun scrolled(amountX: Float, amountY: Float) = false

    private fun updateMovementValues(newX: Float, newY: Float) {
        valueLeftX = newX
        valueLeftY = newY
        stopMovement = abs(valueLeftX) <= AXIS_DEAD_ZONE && abs(valueLeftY) <= AXIS_DEAD_ZONE
        moveDirectionDeg = atan2(-valueLeftY, valueLeftX) * 180f / PI.toFloat()
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        updateMovement(entity)
        processAction(entity)
    }

    private fun updateMovement(entity: Entity) {
        with(entity[MoveComponent.mapper]!!) {
            root = stopMovement
            if (!root) {
                cosDeg = MathUtils.cosDeg(moveDirectionDeg)
                sinDeg = MathUtils.sinDeg(moveDirectionDeg)
            }
        }
    }

    private fun processAction(entity: Entity) {
        if (!actionPressed) {
            return
        }

        actionPressed = false
        entity[InteractComponent.mapper]?.let { it.interact = true }
    }

    companion object {
        private const val AXIS_DEAD_ZONE = 0.25f
    }
}
