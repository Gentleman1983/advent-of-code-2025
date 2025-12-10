package de.havox_design.aoc2025.day10

import java.util.PriorityQueue

data class Machine(
    val switches: Int, val joltage: List<Int>, val bitOperations: List<Int>, val operations: List<List<Int>>
) {
    companion object {
        private const val DATA_DELIMITER = " "
        private const val JOLTAGE_DELIMITER = ','
        private const val OPERATIONS_DELIMITER = ","
        private const val INDICATOR_ON = '#'

        fun parse(line: String): Machine {
            val groups = line
                .split(DATA_DELIMITER)
            val switches = groups
                .first()
                .drop(1)
                .dropLast(1)
                .map { it == INDICATOR_ON }
                .reversed()
                .fold(0) { acc, bool ->
                    (acc shl 1) or bool.toInt()
                }
            val joltage = groups
                .last()
                .dropLast(1)
                .drop(1)
                .split(JOLTAGE_DELIMITER)
                .map { it.toInt() }
            val operations = groups
                .drop(1)
                .dropLast(1)
                .map { o ->
                    o
                        .drop(1)
                        .dropLast(1)
                        .split(OPERATIONS_DELIMITER)
                        .map { it.toInt() }
                }

            return Machine(switches, joltage, operations.map { it.toBitMask() }, operations)
        }

        val comparator: Comparator<Pair<Int, Set<Int>>> = compareBy { it.second.size }
    }

    fun findShortestOpeningSequence(): Int {
        val queue = priorityQueueOf(comparator)
        val seen = mutableSetOf<Set<Int>>()

        queue
            .add(0 to emptySet())

        while (queue.isNotEmpty()) {
            val (current, turns) = queue
                .poll()

            if (!seen.add(turns)) {
                continue
            }

            val next = bitOperations
                .map { current xor it to turns + it }
                .filter { it.second !in seen }
                .distinct()

            if (next.any { it.first == switches }) {
                return turns.size + 1
            }

            next
                .toCollection(queue)
        }
        error("Did not find target for $this")
    }
}

private fun List<Int>.toBitMask() =
    fold(0) { acc, value ->
        acc or (1 shl value)
    }

private fun Boolean.toInt() = when {
    this -> 1
    else -> 0
}

private fun <T : Any> priorityQueueOf(comparator: Comparator<T>, vararg args: T): PriorityQueue<T> {
    val queue = PriorityQueue<T>(comparator)

    queue
        .addAll(args)

    return queue
}
