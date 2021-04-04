package dev.plotsky.cavefarming.components

import com.badlogic.ashley.core.Component
import dev.plotsky.cavefarming.Crop
import ktx.ashley.mapperFor

class InventoryComponent : Component {
    companion object {
        val mapper = mapperFor<InventoryComponent>()
    }

    val crops = listOf(Crop.MUSHROOMS, Crop.KANES, Crop.POTATOES, Crop.TURNIPS)
    var currentCrop: Crop = Crop.MUSHROOMS
}
