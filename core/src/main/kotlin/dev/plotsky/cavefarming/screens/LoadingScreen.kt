package dev.plotsky.cavefarming.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.maps.tiled.TiledMap
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
        assetManager.load("first_cave.tmx", TiledMap::class.java)
        super.show()
    }

    override fun render(delta: Float) {
        assetManager.update()
        camera.update()
        batch.use(camera) {
            font.draw(
                it,
                "[GOLDENROD]Welcome to Cave Farming!",
                camera.position.x - TITLE_X,
                Gdx.graphics.height - TITLE_Y
            )
            if (assetManager.isFinished) {
                font.draw(
                    it,
                    "[GOLDENROD]Press Enter or click the screen to begin",
                    camera.position.x - LOADED_X,
                    Gdx.graphics.height - LOADED_Y
                )
            } else {
                font.draw(
                    it,
                    "[GOLDENROD]Loading...",
                    camera.position.x - LOADING_X,
                    Gdx.graphics.height - LOADING_Y
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

    companion object {
        private const val TITLE_X = 75f
        private const val TITLE_Y = 150f
        private const val LOADED_X = 100f
        private const val LOADED_Y = 200f
        private const val LOADING_X = 25f
        private const val LOADING_Y = 200f
    }
}
