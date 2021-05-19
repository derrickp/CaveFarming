package dev.plotsky.cavefarming.crops

import kotlinx.serialization.Serializable

@Serializable
data class GrowthStage(
    val name: String,
    val timeInStage: Int,
    val regionName: String,
    val harvestable: Boolean,
    val chanceToProgressFromStage: Float
)
