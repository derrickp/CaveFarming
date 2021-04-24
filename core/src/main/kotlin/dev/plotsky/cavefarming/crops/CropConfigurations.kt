package dev.plotsky.cavefarming.crops

object CropConfigurations {
    private const val ONE_SIZE = 1
    private const val THREE_SIZE = 3
    private const val FIVE_SIZE = 5
    private const val ONE_AREA = 1f
    private const val TWO_AREA = 2f

    private const val MUSHROOM_SEASON_LENGTH = 10
    private const val MUSHROOM_OLD_SEASON_LENGTH = 20
    private const val MUSHROOM_GROW_CHANCE = 0.75f
    private const val MUSHROOM_DIE_TOO_OLD = 0.05f
    val mushroom by lazy {
        CropConfiguration(
            gridSize = Pair(THREE_SIZE, THREE_SIZE),
            areaNeededPerCrop = Pair(ONE_AREA, ONE_AREA),
            seedRegionName = "giant_mushroom_spores",
            cropRegionName = "giant_mushroom",
            growingSeasonLength = MUSHROOM_SEASON_LENGTH,
            chanceGrowAfterSeason = MUSHROOM_GROW_CHANCE,
            tooOldSeasonLength = MUSHROOM_OLD_SEASON_LENGTH,
            chanceDieAfterSeason = MUSHROOM_DIE_TOO_OLD,
            CropType.MUSHROOMS
        )
    }

    private const val KANE_SEASON_LENGTH = 5
    private const val KANE_OLD_SEASON_LENGTH = 15
    private const val KANE_GROW_CHANCE = 0.70f
    private const val KANE_DIE_TOO_OLD = 0.10f
    val kane by lazy {
        CropConfiguration(
            gridSize = Pair(FIVE_SIZE, THREE_SIZE),
            areaNeededPerCrop = Pair(TWO_AREA, TWO_AREA),
            seedRegionName = "kane_seeds",
            cropRegionName = "kane_stalks",
            growingSeasonLength = KANE_SEASON_LENGTH,
            chanceGrowAfterSeason = KANE_GROW_CHANCE,
            tooOldSeasonLength = KANE_OLD_SEASON_LENGTH,
            chanceDieAfterSeason = KANE_DIE_TOO_OLD,
            CropType.KANES
        )
    }

    private const val TURNIP_SEASON_LENGTH = 15
    private const val TURNIP_OLD_SEASON_LENGTH = 10
    private const val TURNIP_GROW_CHANCE = 0.65f
    private const val TURNIP_DIE_TOO_OLD = 0.20f
    val turnip by lazy {
        CropConfiguration(
            gridSize = Pair(ONE_SIZE, ONE_SIZE),
            areaNeededPerCrop = Pair(ONE_AREA, ONE_AREA),
            seedRegionName = "turnip_seed",
            cropRegionName = "turnip_top",
            growingSeasonLength = TURNIP_SEASON_LENGTH,
            chanceGrowAfterSeason = TURNIP_GROW_CHANCE,
            tooOldSeasonLength = TURNIP_OLD_SEASON_LENGTH,
            chanceDieAfterSeason = TURNIP_DIE_TOO_OLD,
            CropType.TURNIPS
        )
    }

    private const val POTATO_SEASON_LENGTH = 10
    private const val POTATO_OLD_SEASON_LENGTH = 30
    private const val POTATO_GROW_CHANCE = 0.70f
    private const val POTATO_DIE_TOO_OLD = 0.05f
    val potato by lazy {
        CropConfiguration(
            gridSize = Pair(ONE_SIZE, THREE_SIZE),
            areaNeededPerCrop = Pair(ONE_AREA, ONE_AREA),
            seedRegionName = "potato_seeds",
            cropRegionName = "potatoes",
            growingSeasonLength = POTATO_SEASON_LENGTH,
            chanceGrowAfterSeason = POTATO_GROW_CHANCE,
            tooOldSeasonLength = POTATO_OLD_SEASON_LENGTH,
            chanceDieAfterSeason = POTATO_DIE_TOO_OLD,
            CropType.POTATOES
        )
    }
}
