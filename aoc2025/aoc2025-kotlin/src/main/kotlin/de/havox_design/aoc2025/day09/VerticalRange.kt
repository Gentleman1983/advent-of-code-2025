package de.havox_design.aoc2025.day09

import de.havox_design.aoc.utils.kotlin.model.positions.Position2d
import kotlin.math.max
import kotlin.math.min

data class VerticalRange(val x: Int, private val y: IntRange) {
    val internalY = (y.first + 1)..y.last

    fun intersectsWith(other: HorizontalRange) =
        x in other.internalX && other.y in this.internalY

    operator fun contains(point: Position2d<Int>) =
        point.x == this.x && point.y in this.y

    override fun toString() =
        "($x, $y)"

    companion object {
        fun create(a: Position2d<Int>, b: Position2d<Int>) =
            VerticalRange(a.x, min(a.y, b.y)..<max(a.y, b.y))
    }
}
