package de.havox_design.aoc2025.day06

import de.havox_design.aoc.utils.kotlin.helpers.math.product
import kotlin.collections.filter
import kotlin.text.isNotBlank

class TrashCompactor(private var filename: String) {
    private val data = getResourceAsText(filename)
        .splitLinesForTrashCompactor()
        .convertToSingleCalculations()
    private val data2 = getResourceAsText(filename)
        .filter(String::isNotEmpty)

    fun processPart1(): Any =
        data
            .sumOf { calculation ->
                calculation.operator.compute(calculation.elements)
            }

    fun processPart2(): Any  =
        processRightToLeftValues(data2)

    fun processRightToLeftValues(input: List<String>): Any {
        val maxWidth = input
            .maxOf(String::length)
        var sumOfOperations = 0L
        val readOperands = mutableListOf<Long>()

        (maxWidth - 1 downTo 0).forEach { charIndex ->
            val verticallyReadValue =
                input
                    .mapNotNull { row ->
                        row
                            .getOrNull(charIndex)
                            ?.takeUnless(Char::isWhitespace)
                    }
                    .joinToString(separator = "")
                    .takeIf(String::isNotEmpty)
                    ?: return@forEach

            readOperands +=
                verticallyReadValue
                    .trim('*', '+')
                    .toLong()

            if (!verticallyReadValue.last().isDigit()) {
                val operator = verticallyReadValue
                    .last()

                sumOfOperations += readOperands.compute(operator)
                readOperands.clear()
            }
        }

        return sumOfOperations
    }

private fun List<Long>.compute(operator: Char): Long =
    if (operator == '*')
        product()
    else
        sum()

private fun List<List<String>>.convertToSingleCalculations(): List<Calculation> {
    val size = this.validateAndGetSize()
    val calculations: MutableList<Calculation> = mutableListOf()
    var dataIndex = 0;

    while (dataIndex < size) {
        var index = 0;
        val elements: MutableList<Long> = mutableListOf()

        while (index < this.size - 1) {
            elements
                .add(this[index][dataIndex].toLong())

            index++
        }

        calculations
            .add(Calculation(Operator.parse(this[index][dataIndex]), elements))

        dataIndex++
    }

    return calculations
}

private fun List<List<String>>.validateAndGetSize(): Int {
    val size = this[0].size

    for (row in this) {
        assert(row.size == size)
    }

    return size
}

private fun List<String>.splitLinesForTrashCompactor(): List<List<String>> =
    this
        .map { row ->
            row
                .split("\\s".toRegex())
                .filter { element ->
                    element.isNotBlank()
                }
        }

private fun getResourceAsText(path: String): List<String> =
    this
        .javaClass
        .classLoader
        .getResourceAsStream(path)!!
        .bufferedReader()
        .readLines()
}
