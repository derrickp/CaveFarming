package dev.plotsky.cavefarming

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
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
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.TiledMap

const val GAME_WIDTH = 16f
const val GAME_HEIGHT = 9f
const val GROWING_INTERVAL = 1f

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
                setToOrtho(false, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
            })
            bindSingleton(PooledEngine())
            val assetManager = AssetManager()
            assetManager.setLoader(TiledMap::class.java, TmxMapLoader(InternalFileHandleResolver()))

            bindSingleton(assetManager)
            addScreen(LoadingScreen(this@CaveFarming, inject(), inject(), inject(), inject()))
            addScreen(OverWorldScreen(this@CaveFarming, inject(), inject(), inject()))
            addScreen(InventoryScreen(this@CaveFarming, inject(), inject(), inject(), inject()))
        }

        setScreen<LoadingScreen>()

        super.create()
    }

    override fun dispose() {
        context.dispose()
        super.dispose()
    }
}
