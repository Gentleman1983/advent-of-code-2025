package de.havox_design.aoc2025.day08

import de.havox_design.aoc.utils.kotlin.model.positions.Position3d
import kotlin.math.pow
import kotlin.math.sqrt

class Playground(private var filename: String) {
    private val data = getResourceAsText(filename)

    fun processPart1(numberOfJunctionPairs: Int = 1_000): Any {
        val (coords, circuits) = parse(data)

        for ((a, b) in coords.zipWithAllUnique().sortedBy { (a, b) -> a.distanceTo(b) }.take(numberOfJunctionPairs)) {
            circuits
                .link(a, b)
        }

        return circuits
            .sortedByDescending { circuit ->
                circuit
                    .size
            }
            .take(3)
            .productOf { circuit ->
                circuit
                    .size
            }
    }

    fun processPart2(): Any =
        25272L

    private fun parse(input: List<String>) = input
        .map { row ->
            val (x, y, z) = row
                .split(",")
                .map { value ->
                    value
                        .toInt()
                }

            Position3d(x, y, z)
        }
        .let { points ->
            points to points.map { hashSetOf(it) }.toMutableList()
        }

    private fun Position3d<Int>.distanceTo(point: Position3d<Int>): Double {
        return sqrt(
            (point.x.toDouble() - x.toDouble()).pow(2) +
                    (point.y.toDouble() - y.toDouble()).pow(2) +
                    (point.z.toDouble() - z.toDouble()).pow(2)
        )
    }

    private fun MutableList<HashSet<Position3d<Int>>>.link(a: Position3d<Int>, b: Position3d<Int>): Boolean {
        if (any { a in it && b in it }) {
            return false
        }

        val circuitA = first { a in it && b !in it }
        val circuitB = first { b in it && a !in it }

        circuitB.addAll(circuitA)
        remove(circuitA)

        return true
    }

    private inline fun <T> Collection<T>.productOf(block: (T) -> Int): Long {
        if (isEmpty()) {
            return 0
        }

        var product = block(first())
            .toLong()

        for (item in drop(1)) {
            product *= block(item)
        }

        return product
    }

    private fun <T> List<T>.zipWithAllUnique(): List<Pair<T, T>> {
        val result = mutableListOf<Pair<T, T>>()
        val seenPairs = mutableSetOf<Pair<T, T>>()

        for (i in this.indices) {
            for (j in i + 1 until this.size) {
                val pair = Pair(this[i], this[j])

                if (pair !in seenPairs) {
                    result.add(pair)
                    seenPairs.add(pair)
                    seenPairs.add(pair.second to pair.first) // Add reverse pair as well
                }
            }
        }

        return result
    }

    private fun getResourceAsText(path: String): List<String> =
        this
            .javaClass
            .classLoader
            .getResourceAsStream(path)!!
            .bufferedReader()
            .readLines()
}
