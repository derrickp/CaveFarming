package dev.plotsky.cavefarming.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.FitViewport
import dev.plotsky.cavefarming.CaveFarming
import dev.plotsky.cavefarming.GAME_HEIGHT
import dev.plotsky.cavefarming.GAME_WIDTH
import dev.plotsky.cavefarming.assets.TextureAtlasAssets
import dev.plotsky.cavefarming.assets.get
import dev.plotsky.cavefarming.components.InputComponent
import dev.plotsky.cavefarming.components.InventoryComponent
import dev.plotsky.cavefarming.components.MoveComponent
import dev.plotsky.cavefarming.components.NameComponent
import dev.plotsky.cavefarming.components.RenderComponent
import dev.plotsky.cavefarming.components.TransformComponent
import dev.plotsky.cavefarming.systems.ActionsSystem
import dev.plotsky.cavefarming.systems.InputSystem
import dev.plotsky.cavefarming.systems.MoveSystem
import dev.plotsky.cavefarming.systems.MovementDirectionSystem
import dev.plotsky.cavefarming.systems.RenderSystem
import ktx.app.KtxScreen
import ktx.ashley.entity
import ktx.ashley.with

class OverWorldScreen(
    private val caveFarming: CaveFarming,
    private val batch: Batch,
    private val engine: PooledEngine,
    private val assetManager: AssetManager
) : KtxScreen {
    private val viewport = FitViewport(GAME_WIDTH, GAME_HEIGHT)
    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        super.resize(width, height)
    }
    override fun render(delta: Float) {
        viewport.camera.position.set(GAME_WIDTH / 2, GAME_HEIGHT / 2, 0f)
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
            with<MoveComponent>()
            with<TransformComponent> {
                bounds.x = GAME_WIDTH / 2
                bounds.y = GAME_HEIGHT / 2
                bounds.width = 1f
                bounds.height = 1f
            }
            with<InputComponent>()
            with<InventoryComponent>()
            with<RenderComponent> {
                sprite.setRegion(assetManager[TextureAtlasAssets.CaveFarming].findRegion("ogre_idle"))
            }
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
            addSystem(ActionsSystem(engine, assetManager))
            addSystem(RenderSystem(batch, viewport))
//            addSystem(RenderSystem(hole, batch, font, camera))
            // add CollisionSystem last as it removes entities and this should always
            // happen at the end of an engine update frame
//            addSystem(CollisionSystem(hole, assets))
//            addSystem(AnimationSystem(batch, font, camera))
        }
    }
}
