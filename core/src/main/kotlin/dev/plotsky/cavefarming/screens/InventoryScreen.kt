package dev.plotsky.cavefarming.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import dev.plotsky.cavefarming.CaveFarming
import dev.plotsky.cavefarming.systems.ChooseCropSystem
import dev.plotsky.cavefarming.systems.RenderInventorySelectionSystem
import ktx.app.KtxScreen
import ktx.graphics.use

class InventoryScreen(
    private val caveFarming: CaveFarming,
    private val batch: Batch,
    private val font: BitmapFont,
    private val engine: PooledEngine,
    private val camera: OrthographicCamera
) : KtxScreen {
    override fun render(delta: Float) {
        camera.update()
        // everything is now done within our entity engine --> update it every frame
        batch.use(camera) {
            font.draw(it, "INVENTORY", 50f, 50f)
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            engine.removeAllSystems()
            caveFarming.setScreen<OverWorldScreen>()
        }

        engine.update(delta)
    }

    override fun show() {
        addSystems()
        super.show()
    }

    private fun addSystems() {
        engine.apply {
            addSystem(ChooseCropSystem())
            addSystem(RenderInventorySelectionSystem(batch, font))
        }
    }
}
