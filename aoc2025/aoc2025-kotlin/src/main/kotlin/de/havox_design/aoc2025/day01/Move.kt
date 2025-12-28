package de.havox_design.aoc2025.day01

import de.havox_design.aoc.utils.kotlin.model.directions.LeftRightDirection
import kotlin.math.*

data class Move(val direction: LeftRightDirection, val distance: Int) {
    fun process(start: Int): Pair<Int, Int> {
        val target = (start + calculateChange()) % 100
        val visitingZero = calculateDistance(start)

        return Pair(target, visitingZero)
    }

    private fun calculateChange(): Int =
        direction.direction * distance

    private fun calculateDistance(start: Int): Int {
        val change = calculateChange()
        val target = start + change

        return if (target < 0) {
            abs(target) / 100 + 1
        } else {
            target / 100
        }
    }

    companion object {
        fun parse(string: String): Move {
            val direction = LeftRightDirection
                .from(string)
            val distance = string
                .substring(1)
                .toInt()

            return Move(direction, distance)
        }
    }
}
