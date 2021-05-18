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
            CropType.MUSHROOMS,
            growthStages = listOf(
                GrowthStage(
                    name = "spore",
                    timeInStage = MUSHROOM_SEASON_LENGTH,
                    regionName = "giant_mushroom_spores",
                    harvestable = false,
                    chanceToProgressFromStage = MUSHROOM_GROW_CHANCE
                ),
                GrowthStage(
                    name = "crop",
                    timeInStage = MUSHROOM_OLD_SEASON_LENGTH,
                    regionName = "giant_mushroom",
                    harvestable = true,
                    chanceToProgressFromStage = MUSHROOM_DIE_TOO_OLD
                )
            )
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
            CropType.KANES,
            growthStages = listOf(
                GrowthStage(
                    name = "seed",
                    timeInStage = KANE_SEASON_LENGTH,
                    regionName = "kane_seeds",
                    harvestable = false,
                    chanceToProgressFromStage = KANE_GROW_CHANCE
                ),
                GrowthStage(
                    name = "stalks",
                    timeInStage = KANE_OLD_SEASON_LENGTH,
                    regionName = "kane_stalks",
                    harvestable = true,
                    chanceToProgressFromStage = KANE_DIE_TOO_OLD
                )
            )
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
            CropType.TURNIPS,
            growthStages = listOf(
                GrowthStage(
                    name = "seed",
                    timeInStage = TURNIP_SEASON_LENGTH,
                    regionName = "turnip_seed",
                    harvestable = false,
                    chanceToProgressFromStage = TURNIP_GROW_CHANCE
                ),
                GrowthStage(
                    name = "crop",
                    timeInStage = TURNIP_OLD_SEASON_LENGTH,
                    regionName = "turnip_top",
                    harvestable = true,
                    chanceToProgressFromStage = TURNIP_DIE_TOO_OLD
                )
            )
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
            CropType.POTATOES,
            growthStages = listOf(
                GrowthStage(
                    name = "seed",
                    timeInStage = POTATO_SEASON_LENGTH,
                    regionName = "potato_seeds",
                    harvestable = false,
                    chanceToProgressFromStage = POTATO_GROW_CHANCE
                ),
                GrowthStage(
                    name = "crop",
                    timeInStage = POTATO_OLD_SEASON_LENGTH,
                    regionName = "potatoes",
                    harvestable = true,
                    chanceToProgressFromStage = POTATO_DIE_TOO_OLD
                )
            )
        )
    }
}
