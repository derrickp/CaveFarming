package dev.plotsky.cavefarming.screens

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import dev.plotsky.cavefarming.Game
import ktx.app.KtxScreen
import ktx.graphics.use

class LoadingScreen(
    private val game: Game,
    private val batch: Batch,
    private val font: BitmapFont
) : KtxScreen {
    override fun render(delta: Float) {
        batch.use {
            font.draw(it, "Welcome to Cave Farming!", 200f, 400f)
        }
        super.render(delta)
    }
}
