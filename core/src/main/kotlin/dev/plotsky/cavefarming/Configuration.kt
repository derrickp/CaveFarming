package dev.plotsky.cavefarming

import dev.plotsky.cavefarming.crops.CropConfiguration
import kotlinx.serialization.Serializable

@Serializable
data class Configuration(
    val crops: List<CropConfiguration>
)
