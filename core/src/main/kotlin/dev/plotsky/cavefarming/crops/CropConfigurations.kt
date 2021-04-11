package dev.plotsky.cavefarming.crops

import com.badlogic.gdx.math.Vector2
import kotlin.math.floor
import kotlin.math.roundToInt

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

    fun buildCropGrid(
        position: Vector2,
        configuration: CropConfiguration
    ): List<Vector2> {
        val centerX = position.x.roundToInt()
        val centerY = position.y.roundToInt()

        val leftX = (centerX - floor(configuration.gridSize.first.toFloat() / 2)).toInt()
        val rightX = (centerX + floor(configuration.gridSize.first.toFloat() / 2)).toInt()
        val topY = (centerY + floor(configuration.gridSize.second.toFloat() / 2)).toInt()
        val bottomY = (centerY - floor(configuration.gridSize.second.toFloat() / 2)).toInt()

        val xStep = configuration.areaNeededPerCrop.first.toInt()
        val yStep = configuration.areaNeededPerCrop.second.toInt()

        val grid = mutableListOf<Vector2>()

        for (x in leftX..rightX step xStep) {
            for (y in bottomY..topY step yStep) {
                grid.add(Vector2(x.toFloat(), y.toFloat()))
            }
        }

        return grid
    }
}
