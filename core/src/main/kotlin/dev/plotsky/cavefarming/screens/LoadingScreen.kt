package dev.plotsky.cavefarming.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import dev.plotsky.cavefarming.Game
import ktx.app.KtxScreen
import ktx.graphics.use

class LoadingScreen(
    private val game: Game,
    private val batch: Batch,
    private val font: BitmapFont,
    private val camera: OrthographicCamera
) : KtxScreen {
    override fun render(delta: Float) {
        batch.use(camera) {
            font.draw(it, "[GOLDENROD]Welcome to Cave Farming!", 200f, 400f)
        }

        if (Gdx.input.isTouched || Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            game.setScreen<OverWorldScreen>()
            game.removeScreen<LoadingScreen>()
            dispose()
        }

        super.render(delta)
    }
}
