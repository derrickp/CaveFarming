package dev.plotsky.cavefarming

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import dev.plotsky.cavefarming.screens.LoadingScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.inject.Context
import ktx.inject.register

/** [ktx.app.KtxGame] implementation shared by all platforms.  */
class Game : KtxGame<KtxScreen>() {
    private val context = Context()
    override fun create() {
        context.register {
            bindSingleton<Batch>(SpriteBatch())
            bindSingleton(BitmapFont())
            addScreen(LoadingScreen(this@Game, inject(), inject()))
        }

        setScreen<LoadingScreen>()

        super.create()
    }
}
