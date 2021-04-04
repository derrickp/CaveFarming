package dev.plotsky.cavefarming.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import dev.plotsky.cavefarming.CaveFarming
import dev.plotsky.cavefarming.components.FontCharacterComponent
import dev.plotsky.cavefarming.components.InputComponent
import dev.plotsky.cavefarming.components.InventoryComponent
import dev.plotsky.cavefarming.components.MoveComponent
import dev.plotsky.cavefarming.components.NameComponent
import dev.plotsky.cavefarming.components.TransformComponent
import dev.plotsky.cavefarming.systems.ActionsSystem
import dev.plotsky.cavefarming.systems.InputSystem
import dev.plotsky.cavefarming.systems.MoveSystem
import dev.plotsky.cavefarming.systems.MovementDirectionSystem
import dev.plotsky.cavefarming.systems.RenderFontCharacterSystem
import ktx.app.KtxScreen
import ktx.ashley.entity
import ktx.ashley.with

class OverWorldScreen(
    private val caveFarming: CaveFarming,
    private val batch: Batch,
    private val font: BitmapFont,
    private val camera: OrthographicCamera,
    private val engine: PooledEngine
) : KtxScreen {
    override fun render(delta: Float) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            removeSystems()
            caveFarming.setScreen<InventoryScreen>()
        }

        // everything is now done within our entity engine --> update it every frame
        engine.update(delta)
    }

    override fun show() {
        // initialize entity engine
        if (engine.entities.size() == 0) {
            addMainCharacter()
        }

        if (engine.systems.size() == 0) {
            addSystems()
        }

        super.show()
    }

    private fun addMainCharacter() {
        engine.entity {
            with<NameComponent> { name = "goblin" }
            with<FontCharacterComponent> {
                character = "g"
                color = "GREEN"
                z = 100
            }
            with<MoveComponent>()
            with<TransformComponent> {
                bounds.x = 100f
                bounds.y = 200f
            }
            with<InputComponent>()
            with<InventoryComponent>()
        }
    }

    private fun removeSystems() {
        engine.removeAllSystems()
    }

    private fun addSystems() {
        engine.apply {
            addSystem(InputSystem())
            addSystem(MoveSystem())
            addSystem(MovementDirectionSystem())
            addSystem(ActionsSystem(engine))
            addSystem(RenderFontCharacterSystem(batch, font, camera))
//            addSystem(RenderSystem(hole, batch, font, camera))
            // add CollisionSystem last as it removes entities and this should always
            // happen at the end of an engine update frame
//            addSystem(CollisionSystem(hole, assets))
//            addSystem(AnimationSystem(batch, font, camera))
        }
    }
}
