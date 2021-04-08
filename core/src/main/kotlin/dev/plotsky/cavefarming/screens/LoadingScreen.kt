package dev.plotsky.cavefarming.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import dev.plotsky.cavefarming.CaveFarming
import dev.plotsky.cavefarming.assets.TextureAtlasAssets
import dev.plotsky.cavefarming.assets.load
import ktx.app.KtxScreen
import ktx.graphics.use

class LoadingScreen(
    private val caveFarming: CaveFarming,
    private val batch: Batch,
    private val font: BitmapFont,
    private val assetManager: AssetManager,
    private val camera: OrthographicCamera
) : KtxScreen {
    override fun show() {
        TextureAtlasAssets.values().forEach { assetManager.load(it) }
        super.show()
    }

    override fun render(delta: Float) {
        assetManager.update()
        camera.update()
        batch.use(camera) {
            font.draw(
                it,
                "[GOLDENROD]Welcome to Cave Farming!",
                camera.position.x - 75f,
                Gdx.graphics.height - 150f
            )
            if (assetManager.isFinished) {
                font.draw(
                    it,
                    "[GOLDENROD]Press Enter or click the screen to begin",
                    camera.position.x - 100f,
                    Gdx.graphics.height - 200f
                )
            } else {
                font.draw(
                    it,
                    "[GOLDENROD]Loading...",
                    camera.position.x - 25f,
                    Gdx.graphics.height - 200f
                )
            }
        }

        if ((Gdx.input.isTouched || Gdx.input.isKeyPressed(Input.Keys.ENTER)) && assetManager.isFinished) {
            caveFarming.setScreen<OverWorldScreen>()
            caveFarming.removeScreen<LoadingScreen>()
            dispose()
        }

        super.render(delta)
    }
}
