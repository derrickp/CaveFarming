package dev.plotsky.cavefarming.crops

data class CropConfiguration(
    val gridSize: Pair<Int, Int>,
    val areaNeededPerCrop: Pair<Float, Float>,
    val seedRegionName: String,
    val cropRegionName: String,
    val growingSeasonLength: Int,
    val chanceGrowAfterSeason: Float,
    val tooOldSeasonLength: Int,
    val chanceDieAfterSeason: Float,
    val cropType: CropType
)
