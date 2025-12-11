package de.havox_design.aoc2025.day11

import de.havox_design.aoc2025.day04.filterNotEmpty
import java.util.LinkedList

class Reactor(private var filename: String) {
    private val data = getResourceAsText(filename)
        .toDeviceConnections()

    @SuppressWarnings("kotlin:S6611")
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

    fun processPart2(): Any {
        val devicesToEncounter = setOf(DIGITAL_ANALOG_CONVERTER, FAST_FOURIER_TRANSFORMATION)
        val memoization = mutableMapOf<Any, Long>()

        return countProblematicPathsToTarget(SERVER, memoization, devicesToEncounter)
    }

    @SuppressWarnings("kotlin:S6611")
    private fun countProblematicPathsToTarget(
        fromDevice: String,
        memoization: MutableMap<Any, Long>,
        devicesToEncounter: Set<String>,
        encounteredDevices: Set<String> = emptySet(),
    ): Long {
        val memoizationKey = "$fromDevice${encounteredDevices.hashCode()}"

        if (memoization.containsKey(memoizationKey)) {
            return memoization[memoizationKey]!!
        }

        if (fromDevice == TARGET_DEVICE) {
            val result = if (encounteredDevices == devicesToEncounter) {
                1L
            } else {
                0L
            }

            memoization[memoizationKey] = result

            return result
        }

        val encounteredDevicesAtThisPoint =
            if (fromDevice in devicesToEncounter)
                encounteredDevices + fromDevice
            else
                encounteredDevices

        return data
            .getOrDefault(fromDevice, emptySet())
            .sumOf { connectedDevice ->
                countProblematicPathsToTarget(
                    fromDevice = connectedDevice,
                    memoization,
                    devicesToEncounter,
                    encounteredDevicesAtThisPoint,
                )
            }
            .also { result ->
                memoization[memoizationKey] = result
            }
    }

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
        private const val DIGITAL_ANALOG_CONVERTER = "dac"
        private const val DELIMITER_CONNECTION = ": "
        private const val DELIMITER_TARGETS = ' '
        private const val FAST_FOURIER_TRANSFORMATION = "fft"
        private const val SERVER = "svr"
        private const val TARGET_DEVICE = "out"
        private const val YOU = "you"
    }
}
