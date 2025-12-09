package de.havox_design.aoc2025.day09

import de.havox_design.aoc.utils.kotlin.model.positions.Position2d
import de.havox_design.aoc.utils.kotlin.model.positions.minus
import kotlin.math.absoluteValue

class MovieTheater(private var filename: String) {
    private val data = getResourceAsText(filename)
        .parse()

    private val comparatorYFirst: Comparator<Position2d<Int>> =
        Comparator
            .comparing<Position2d<Int>, Int> { position ->
                position.y
            }
            .thenComparing { position ->
                position.x
            }

    fun processPart1(): Any =
        data
            .pairwise(data)
            .maxOf { (a, b) ->
                calculateArea(a, b)
            }

    fun processPart2(): Any {
        val red = data
        val hBorders = mutableListOf<HorizontalRange>()
        val vBorders = mutableListOf<VerticalRange>()

        (red + red.first())
            .asSequence()
            .zipWithNext()
            .forEach { (a, b) ->
                if (a.x == b.x) {
                    vBorders
                        .add(VerticalRange.create(a, b))
                } else {
                    hBorders
                        .add(HorizontalRange.create(a, b))
                }
            }

        return red
            .combinations()
            .filter { (a, b) -> validRectangle(a, b, hBorders, vBorders) }
            .maxOf { (a, b) -> calculateArea(a, b) }
    }

    private fun calculateArea(a: Position2d<Int>, b: Position2d<Int>): Long {
        val diff = a - b

        return (diff.x.absoluteValue.toLong() + 1) * (diff.y.absoluteValue + 1)
    }

    private fun validRectangle(
        a: Position2d<Int>,
        b: Position2d<Int>,
        hBorders: List<HorizontalRange>,
        vBorders: List<VerticalRange>
    ): Boolean {
        val c = Position2d(a.x, b.y)
        val d = Position2d(b.x, a.y)
        val comparator: Comparator<Position2d<Int>> =
            Comparator
                .comparing<Position2d<Int>, Int> { position ->
                    position.y
                }
                .thenComparing { position ->
                    position.x
                }
        val (topLeft, topRight, bottomLeft, bottomRight) = listOf(a, b, c, d)
            .sortedWith(comparator)
        val hSides = listOf(
            HorizontalRange.create(topLeft, topRight),
            HorizontalRange.create(bottomLeft, bottomRight)
        )
        val vSides = listOf(
            VerticalRange.create(topLeft, bottomLeft),
            VerticalRange.create(topRight, bottomRight)
        )

        return c.isInsidePolygon(hBorders, vBorders)
                && d.isInsidePolygon(hBorders, vBorders)
                && hSides
            .none { rectangleSide ->
                vBorders
                    .any { polygonSide ->
                        rectangleSide
                            .intersectsWith(polygonSide)
                    }
            }
                && vSides
            .none { rectangleSide ->
                hBorders
                    .any { polygonSide ->
                        rectangleSide
                            .intersectsWith(polygonSide)
                    }
            }
    }

    private fun Position2d<Int>.isInsidePolygon(
        horizontalEdges: List<HorizontalRange>,
        verticalEdges: List<VerticalRange>
    ): Boolean =
        verticalEdges.any { this in it }
                || horizontalEdges.any { this in it }
                || horizontalEdges.count { it.isBelow(this) } % 2 == 1

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
        private const val ROW_DELIMITER = ","
    }
}

private fun <A> Iterable<A>.combinations(): List<Pair<A, A>> =
    this
        .pairwise(this)

private fun <A, B> Iterable<A>.pairwise(other: Iterable<B>): List<Pair<A, B>> =
    flatMapIndexed { i, a ->
        other
            .drop(i + 1)
            .map { b ->
                a to b
            }
    }