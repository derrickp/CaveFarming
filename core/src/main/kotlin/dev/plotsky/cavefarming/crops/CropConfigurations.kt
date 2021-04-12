package dev.plotsky.cavefarming.crops

object CropConfigurations {
    val mushroom by lazy {
        CropConfiguration(
            gridSize = Pair(3, 3),
            areaNeededPerCrop = Pair(1f, 1f),
            seedRegionName = "mushroom_spores",
            cropRegionName = "mushroom",
            growingSeasonLength = 10,
            chanceGrowAfterSeason = 0.95f,
            tooOldSeasonLength = 20,
            chanceDieAfterSeason = 0.05f,
            CropType.MUSHROOMS
        )
    }

    val kane by lazy {
        CropConfiguration(
            gridSize = Pair(5, 3),
            areaNeededPerCrop = Pair(2f, 2f),
            seedRegionName = "kane",
            cropRegionName = "kane",
            growingSeasonLength = 5,
            chanceGrowAfterSeason = 0.90f,
            tooOldSeasonLength = 15,
            chanceDieAfterSeason = 0.10f,
            CropType.KANES
        )
    }

    val turnip by lazy {
        CropConfiguration(
            gridSize = Pair(1, 1),
            areaNeededPerCrop = Pair(1f, 1f),
            seedRegionName = "turnip",
            cropRegionName = "turnip",
            growingSeasonLength = 15,
            chanceGrowAfterSeason = 0.85f,
            tooOldSeasonLength = 10,
            chanceDieAfterSeason = 0.20f,
            CropType.TURNIPS
        )
    }

    val potato by lazy {
        CropConfiguration(
            gridSize = Pair(1, 3),
            areaNeededPerCrop = Pair(1f, 1f),
            seedRegionName = "potato",
            cropRegionName = "potato",
            growingSeasonLength = 10,
            chanceGrowAfterSeason = 0.90f,
            tooOldSeasonLength = 30,
            chanceDieAfterSeason = 0.05f,
            CropType.POTATOES
        )
    }
}
