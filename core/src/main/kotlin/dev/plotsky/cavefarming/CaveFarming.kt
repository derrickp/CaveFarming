package dev.plotsky.cavefarming

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import dev.plotsky.cavefarming.screens.InventoryScreen
import dev.plotsky.cavefarming.screens.LoadingScreen
import dev.plotsky.cavefarming.screens.OverWorldScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.inject.Context
import ktx.inject.register

/** [ktx.app.KtxGame] implementation shared by all platforms.  */
class CaveFarming : KtxGame<KtxScreen>() {
    private val context = Context()
    override fun create() {
        context.register {
            bindSingleton<Batch>(SpriteBatch())
            val font = BitmapFont().apply {
                data.markupEnabled = true
            }
            bindSingleton(font)
            // The camera ensures we can render using our target resolution of 800x480
            //    pixels no matter what the screen resolution is.
            bindSingleton(OrthographicCamera().apply {
                setToOrtho(false, 800f, 480f)
            })
            bindSingleton(PooledEngine())
            addScreen(LoadingScreen(this@CaveFarming, inject(), inject(), inject()))
            addScreen(OverWorldScreen(this@CaveFarming, inject(), inject(), inject(), inject()))
            addScreen(InventoryScreen(this@CaveFarming, inject(), inject(), inject()))
        }

        setScreen<LoadingScreen>()

        super.create()
    }

    override fun dispose() {
        context.dispose()
        super.dispose()
    }
}
