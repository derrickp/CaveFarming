package dev.plotsky.cavefarming.inventory

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
}
