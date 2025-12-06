package de.havox_design.aoc2025.day06

import de.havox_design.aoc.utils.kotlin.helpers.math.product

enum class Operator(val sign: String) {
    ADD("+"),
    MULTIPLY("*");

    fun compute(elements: List<Long>) =
        compute(this, elements)

    companion object {
        fun compute(operator: Operator, elements: List<Long>): Long =
            when(operator) {
                ADD -> elements.sum()
                MULTIPLY -> elements.product()
            }

        fun parse(sign: String): Operator =
            when(sign) {
                ADD.sign -> ADD
                MULTIPLY.sign -> MULTIPLY
                else -> throw IllegalArgumentException("'$sign' is no valid sign.")
            }
    }
}