package dev.plotsky.cavefarming.crops

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CropType(private val type: String) {
    @SerialName("mushrooms")
    MUSHROOMS("Mushrooms"),
    @SerialName("potatoes")
    POTATOES("Potatoes"),
    @SerialName("turnips")
    TURNIPS("Turnips"),
    @SerialName("kanes")
    KANES("Kanes");

    override fun toString(): String {
        return type
    }
}
