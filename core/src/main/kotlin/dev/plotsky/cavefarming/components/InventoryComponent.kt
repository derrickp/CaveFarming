package dev.plotsky.cavefarming.components

import com.badlogic.ashley.core.Component
import dev.plotsky.cavefarming.crops.CropConfigurations
import dev.plotsky.cavefarming.inventory.Item
import dev.plotsky.cavefarming.inventory.Items
import ktx.ashley.mapperFor

class InventoryComponent : Component {
    val crops = listOf(
        CropConfigurations.mushroom,
        CropConfigurations.kane,
        CropConfigurations.potato,
        CropConfigurations.turnip
    )
    var currentCropConfiguration = CropConfigurations.mushroom
    val items: MutableMap<Item, Int> = mutableMapOf()
    var equippedItem: Item = Items.nothing

    companion object {
        val mapper = mapperFor<InventoryComponent>()
    }
}
