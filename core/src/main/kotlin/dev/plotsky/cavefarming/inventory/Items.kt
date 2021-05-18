package dev.plotsky.cavefarming.inventory

import dev.plotsky.cavefarming.crops.CropType

object Items {
    private const val LIMIT_ONE = 1

    val nothing by lazy {
        Item(
            "nothing",
            LIMIT_ONE,
            ItemType.NOTHING
        )
    }

    val cropGloves by lazy {
        Item(
            "cropGloves",
            LIMIT_ONE,
            ItemType.WEARABLE
        )
    }

    private val harvestedMushrooms by lazy {
        Item(
            name = CropType.MUSHROOMS.name,
            Int.MAX_VALUE,
            ItemType.HARVESTED_ITEM
        )
    }

    private val harvestedKanes by lazy {
        Item(
            name = CropType.KANES.name,
            Int.MAX_VALUE,
            ItemType.HARVESTED_ITEM
        )
    }

    private val harvestedPotatoes by lazy {
        Item(
            name = CropType.POTATOES.name,
            Int.MAX_VALUE,
            ItemType.HARVESTED_ITEM
        )
    }

    private val harvestedTurnips by lazy {
        Item(
            name = CropType.TURNIPS.name,
            Int.MAX_VALUE,
            ItemType.HARVESTED_ITEM
        )
    }

    fun itemByCropType(cropType: CropType): Item {
        return when (cropType) {
            CropType.MUSHROOMS -> harvestedMushrooms
            CropType.KANES -> harvestedKanes
            CropType.POTATOES -> harvestedPotatoes
            CropType.TURNIPS -> harvestedTurnips
        }
    }
}
