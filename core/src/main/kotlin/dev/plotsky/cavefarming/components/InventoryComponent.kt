package dev.plotsky.cavefarming.components

import com.badlogic.ashley.core.Component
import dev.plotsky.cavefarming.crops.CropType
import ktx.ashley.mapperFor

class InventoryComponent : Component {
    companion object {
        val mapper = mapperFor<InventoryComponent>()
    }

    val crops = listOf(
        CropType.MUSHROOMS,
        CropType.KANES,
        CropType.POTATOES,
        CropType.TURNIPS
    )
    var currentCrop: CropType = CropType.MUSHROOMS
}
