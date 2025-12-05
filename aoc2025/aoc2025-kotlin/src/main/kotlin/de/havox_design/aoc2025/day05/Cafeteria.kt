package de.havox_design.aoc2025.day05

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

    fun processPart2(): Any =
        0L

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
                values[0].toLong() .. values[1].toLong()
            }

    private fun getResourceAsText(path: String): List<String> =
        this
            .javaClass
            .classLoader
            .getResourceAsStream(path)!!
            .bufferedReader()
            .readLines()
}
