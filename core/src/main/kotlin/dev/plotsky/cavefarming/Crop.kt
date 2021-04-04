package dev.plotsky.cavefarming

enum class Crop(var type: String) {
    MUSHROOMS("Mushrooms"),
    POTATOES("Potatoes"),
    TURNIPS("Turnips"),
    KANES("Kanes");

    override fun toString(): String {
        return type
    }
}
