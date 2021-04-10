package dev.plotsky.cavefarming.crops

enum class CropType(private val type: String) {
    MUSHROOMS("Mushrooms"),
    POTATOES("Potatoes"),
    TURNIPS("Turnips"),
    KANES("Kanes");

    override fun toString(): String {
        return type
    }
}
