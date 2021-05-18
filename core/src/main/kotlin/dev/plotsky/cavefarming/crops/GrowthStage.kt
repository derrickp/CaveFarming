package dev.plotsky.cavefarming.crops

data class GrowthStage(
    val name: String,
    val timeInStage: Int,
    val regionName: String,
    val harvestable: Boolean,
    val chanceToProgressFromStage: Float
)
