package dev.plotsky.cavefarming.assets

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.TextureAtlas

// texture atlas
enum class TextureAtlasAssets(val path: String) {
    CaveFarming("game.atlas")
}

fun AssetManager.caveFarmingAtlas(): TextureAtlas {
    return this[TextureAtlasAssets.CaveFarming]
}
