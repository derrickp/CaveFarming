package dev.plotsky.cavefarming.crops

data class CropConfiguration(
    val gridSize: Pair<Int, Int>,
    val areaNeededPerCrop: Pair<Float, Float>,
    val cropType: CropType,
    val growthStages: List<GrowthStage> = emptyList()
)
