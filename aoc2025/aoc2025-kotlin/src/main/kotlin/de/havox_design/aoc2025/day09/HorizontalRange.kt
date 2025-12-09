package de.havox_design.aoc2025.day09

import de.havox_design.aoc.utils.kotlin.model.positions.Position2d
import kotlin.math.max
import kotlin.math.min

data class HorizontalRange(val x: IntRange, val y: Int) {
    val internalX = (x.first + 1)..x.last

    fun intersectsWith(other: VerticalRange) =
        y in other.internalY && other.x in this.internalX

    fun isBelow(point: Position2d<Int>): Boolean =
        x.contains(point.x) && y > point.y

    operator fun contains(point: Position2d<Int>) =
        point.y == this.y && point.x in this.x

    override fun toString() =
        "($x, $y)"

    companion object {
        fun create(a: Position2d<Int>, b: Position2d<Int>) =
            HorizontalRange(min(a.x, b.x)..<max(a.x, b.x), a.y)
    }
}