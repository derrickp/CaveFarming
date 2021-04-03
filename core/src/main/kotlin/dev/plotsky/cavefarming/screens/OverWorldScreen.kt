package dev.plotsky.cavefarming.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import dev.plotsky.cavefarming.components.*
import dev.plotsky.cavefarming.systems.ActionsSystem
import dev.plotsky.cavefarming.systems.InputSystem
import dev.plotsky.cavefarming.systems.MoveSystem
import dev.plotsky.cavefarming.systems.RenderFontCharacterSystem
import ktx.app.KtxScreen
import ktx.ashley.entity
import ktx.ashley.with

class OverWorldScreen(
    private val batch: Batch,
    private val font: BitmapFont,
    private val camera: OrthographicCamera,
    private val engine: PooledEngine
) : KtxScreen {
    override fun render(delta: Float) {
        // everything is now done within our entity engine --> update it every frame
        engine.update(delta)
    }

    override fun show() {
        // initialize entity engine
        engine.apply {
            entity {
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
            }
            // add systems
            addSystem(InputSystem())
            addSystem(MoveSystem())
            addSystem(ActionsSystem())
            addSystem(RenderFontCharacterSystem(batch, font, camera))
//            addSystem(RenderSystem(hole, batch, font, camera))
            // add CollisionSystem last as it removes entities and this should always
            // happen at the end of an engine update frame
//            addSystem(CollisionSystem(hole, assets))
//            addSystem(AnimationSystem(batch, font, camera))
        }
    }
}
