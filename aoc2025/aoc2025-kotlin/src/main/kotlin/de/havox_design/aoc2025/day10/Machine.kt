package de.havox_design.aoc2025.day10

import de.havox_design.aoc.utils.kotlin.helpers.priorityQueueOf
import de.havox_design.aoc.utils.kotlin.helpers.toInt
import org.ojalgo.netio.BasicLogger
import org.ojalgo.optimisation.ExpressionsBasedModel
import org.ojalgo.optimisation.integer.IntegerSolver
import org.ojalgo.type.context.NumberContext
import java.math.BigDecimal

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

    fun findShortestJoltageSequence(): BigDecimal {
        val model = ExpressionsBasedModel()

        model
            .options
            .progress(IntegerSolver::class.java)
        model
            .options
            .logger_appender = BasicLogger.NULL
        model
            .options
            .solution = NumberContext
            .of(joltage.size)
        model
            .options
            .integer()

        val factors = Array(operations.size) {
            model
                .addVariable("x_${it}")
                .integer()
                .lower(0)
                .weight(1)
        }

        joltage
            .indices
            .forEach { index ->
                val expr = model
                    .addExpression("press_$index")
                    .level(joltage[index])

                expr
                    .setLinearFactorsSimple(
                        operations
                            .indices
                            .filter { index in operations[it] }
                            .map { factors[it] }
                    )
            }

        val result = model
            .minimise()

        return (0..<result.size())
            .sumOf { i ->
                result[i.toLong()]
            }
    }
}

private fun List<Int>.toBitMask() =
    fold(0) { acc, value ->
        acc or (1 shl value)
    }
