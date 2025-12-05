package de.havox_design.aoc2025.day05

import kotlin.math.max
import kotlin.math.min

class Cafeteria(private var filename: String) {
    private val data = getResourceAsText(filename)
    private val ingredientIds = getIngredientsIds(data)
    private val freshRanges = getFreshRanges(data)

    fun processPart1(): Any =
        ingredientIds
            .count { id ->
                freshRanges
                    .any { range ->
                        range.contains(id)
                    }
            }

    fun processPart2(): Any {
        val mergedFreshIngredientIdRanges = mutableSetOf<LongRange>()

        freshRanges
            .forEach { range ->
                val overlaps = mergedFreshIngredientIdRanges
                    .filter { ids ->
                        areRangesOverlapping(ids, range)
                    }
                val mergedFreshIngredientIdRange = overlaps
                    .fold(range, ::mergeRanges)

                mergedFreshIngredientIdRanges
                    .removeAll(overlaps.toSet())
                mergedFreshIngredientIdRanges
                    .add(mergedFreshIngredientIdRange)
            }

        return mergedFreshIngredientIdRanges
            .sumOf { range ->
                range.last - range.first + 1
            }
    }

    private fun mergeRanges(a: LongRange, b: LongRange): LongRange {
        return min(a.first, b.first)..max(a.last, b.last)
    }

    private fun areRangesOverlapping(mergedRanges: LongRange, newRanges: LongRange): Boolean {
        return mergedRanges.contains(newRanges.first)
                || newRanges.contains(mergedRanges.first)
    }

    private fun getIngredientsIds(input: List<String>): List<Long> =
        input
            .takeLastWhile { row ->
                row.isNotBlank()
            }
            .map { row ->
                row.toLong()
            }

    private fun getFreshRanges(input: List<String>): List<LongRange> =
        input
            .takeWhile { row ->
                row.isNotBlank()
            }
            .map { row ->
                row.split('-')
            }
            .map { values ->
                values[0].toLong()..values[1].toLong()
            }

    private fun getResourceAsText(path: String): List<String> =
        this
            .javaClass
            .classLoader
            .getResourceAsStream(path)!!
            .bufferedReader()
            .readLines()
}
