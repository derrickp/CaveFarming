package dev.plotsky.cavefarming.components

import com.badlogic.ashley.core.Component
import dev.plotsky.cavefarming.crops.CropConfiguration
import dev.plotsky.cavefarming.inventory.Item
import dev.plotsky.cavefarming.inventory.Items
import ktx.ashley.mapperFor

class InventoryComponent : Component {
    lateinit var crops: List<CropConfiguration>
    lateinit var currentCropConfiguration: CropConfiguration
    val items: MutableMap<Item, Int> = mutableMapOf()
    var equippedItem: Item = Items.nothing

    companion object {
        val mapper = mapperFor<InventoryComponent>()
    }
}
