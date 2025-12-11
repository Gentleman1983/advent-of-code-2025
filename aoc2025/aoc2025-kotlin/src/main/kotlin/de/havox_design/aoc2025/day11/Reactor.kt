package de.havox_design.aoc2025.day11

import de.havox_design.aoc2025.day04.filterNotEmpty
import java.util.LinkedList

class Reactor(private var filename: String) {
    private val data = getResourceAsText(filename)
        .toDeviceConnections()

    fun processPart1(): Any {
        val pathsToCheck = LinkedList<List<String>>()
        var pathCount = 0

        pathsToCheck
            .add(listOf(YOU))

        while (pathsToCheck.isNotEmpty()) {
            val currentPath = pathsToCheck
                .pop()
            val lastDevice = currentPath
                .last()

            if (lastDevice == TARGET_DEVICE) {
                pathCount++

                continue
            }

            data[lastDevice]!!
                .filter { it !in currentPath }
                .map { connectedDevice ->
                    currentPath + connectedDevice
                }
                .also(pathsToCheck::addAll)
        }

        return pathCount
    }

    fun processPart2(): Any =
       2L

    private fun List<String>.toDeviceConnections(): Map<String, Set<String>> =
        this
            .filterNotEmpty()
            .associate { line ->
                val source = line
                    .substringBefore(DELIMITER_CONNECTION)
                val target =
                    line
                        .substringAfter(DELIMITER_CONNECTION)
                        .split(DELIMITER_TARGETS)
                        .toSet()

                source to target
            }

    private fun getResourceAsText(path: String): List<String> =
        this
            .javaClass
            .classLoader
            .getResourceAsStream(path)!!
            .bufferedReader()
            .readLines()

    companion object {
        private const val DELIMITER_CONNECTION = ": "
        private const val DELIMITER_TARGETS = ' '
        private const val TARGET_DEVICE = "out"
        private const val YOU = "you"
    }
}
