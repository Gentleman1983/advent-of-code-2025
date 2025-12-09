package de.havox_design.aoc2025.day09

import de.havox_design.aoc.utils.kotlin.model.positions.Position2d
import de.havox_design.aoc.utils.kotlin.model.positions.minus
import kotlin.math.absoluteValue

class MovieTheater(private var filename: String) {
    private val data = getResourceAsText(filename)
        .parse()

    fun processPart1(): Any =
        data
            .pairwise(data)
            .maxOf { (a, b) ->
                calculateArea(a, b)
            }

    fun processPart2(): Any =
        24L

    private fun calculateArea(a: Position2d<Int>, b: Position2d<Int>): Long {
        val diff = a - b

        return (diff.x.absoluteValue.toLong() + 1) * (diff.y.absoluteValue + 1)
    }

    private fun List<String>.parse(): List<Position2d<Int>> =
        this
            .map { value ->
                Position2d(
                    value
                        .substringBefore(ROW_DELIMITER)
                        .toInt(),
                    value
                        .substringAfter(ROW_DELIMITER)
                        .toInt()
                )
            }

    private fun getResourceAsText(path: String): List<String> =
        this
            .javaClass
            .classLoader
            .getResourceAsStream(path)!!
            .bufferedReader()
            .readLines()

    companion object {
        private val ROW_DELIMITER = ","
    }
}

private fun <A, B> Iterable<A>.pairwise(other: Iterable<B>): List<Pair<A, B>> =
    flatMapIndexed { i, a ->
        other
            .drop(i + 1)
            .map { b ->
                a to b
            }
    }