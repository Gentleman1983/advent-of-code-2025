package de.havox_design.aoc2025.day04

import de.havox_design.aoc.utils.kotlin.model.collections.filterNotEmpty

class PrintingDepartment(private var filename: String) {
    private val data = getResourceAsText(filename)
        .toGrid()

    fun processPart1(): Any {
        var accessibleCellCount = 0

        data
            .forEachIndexed { y, row ->
                row
                    .forEachIndexed { x, cell ->
                        if (cell && data.isCellAccessible(x, y)) {
                            accessibleCellCount++
                        }
                    }
            }

        return accessibleCellCount
    }

    fun processPart2(): Any {
        var removedCellCount = 0

        do {
            var anyRemovals = false

            data
                .forEachIndexed { y, row ->
                    row
                        .forEachIndexed { x, cell ->
                            if (cell && data.isCellAccessible(x, y)) {
                                data[y][x] = false
                                anyRemovals = true
                                removedCellCount++
                            }
                        }
                }
        } while (anyRemovals)

        return removedCellCount
    }

    private fun getResourceAsText(path: String): List<String> =
        this
            .javaClass
            .classLoader
            .getResourceAsStream(path)!!
            .bufferedReader()
            .readLines()
}

private typealias Grid = List<MutableList<Boolean>>
typealias InputStrings = List<String>

private fun InputStrings.toGrid(): Grid =
    this
        .filterNotEmpty()
        .map { row ->
            row
                .mapTo(mutableListOf()) {
                    it == '@'
                }
        }

private fun Grid.isCellAccessible(x: Int, y: Int): Boolean {
    var occupiedCellsAround = 0

    if (getOrNull(y)?.getOrNull(x - 1) == true)
        occupiedCellsAround++
    if (getOrNull(y)?.getOrNull(x + 1) == true)
        occupiedCellsAround++
    if (getOrNull(y - 1)?.get(x) == true)
        occupiedCellsAround++
    if (getOrNull(y + 1)?.get(x) == true)
        occupiedCellsAround++
    if (getOrNull(y - 1)?.getOrNull(x - 1) == true)
        occupiedCellsAround++
    if (getOrNull(y - 1)?.getOrNull(x + 1) == true)
        occupiedCellsAround++
    if (getOrNull(y + 1)?.getOrNull(x - 1) == true)
        occupiedCellsAround++
    if (getOrNull(y + 1)?.getOrNull(x + 1) == true)
        occupiedCellsAround++

    return occupiedCellsAround < 4
}
