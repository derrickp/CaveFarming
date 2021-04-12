package dev.plotsky.cavefarming.crops

import com.badlogic.gdx.math.Vector2
import kotlin.math.floor
import kotlin.math.roundToInt

object CropGrid {
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
