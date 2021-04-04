package dev.plotsky.cavefarming.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import dev.plotsky.cavefarming.CaveFarming
import ktx.app.KtxScreen
import ktx.graphics.use

class LoadingScreen(
    private val caveFarming: CaveFarming,
    private val batch: Batch,
    private val font: BitmapFont,
    private val camera: OrthographicCamera
) : KtxScreen {
    override fun render(delta: Float) {
        batch.use(camera) {
            font.draw(it, "[GOLDENROD]Welcome to Cave Farming!", 300f, 400f)
            font.draw(it, "[GOLDENROD]Press Enter or click the screen to begin", 275f, 380f)
        }

        if (Gdx.input.isTouched || Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            caveFarming.setScreen<OverWorldScreen>()
            caveFarming.removeScreen<LoadingScreen>()
            dispose()
        }

        super.render(delta)
    }
}
