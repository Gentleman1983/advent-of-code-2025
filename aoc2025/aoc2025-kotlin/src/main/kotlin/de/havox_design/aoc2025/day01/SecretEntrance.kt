package de.havox_design.aoc2025.day01

import de.havox_design.aoc.utils.kotlin.model.directions.LeftRightDirection

class SecretEntrance(private var filename: String) {
    private val data = getResourceAsText(filename)
        .map { row -> Move.parse(row) }

    fun processPart1(start: Int = 50): Any =
        data
            .runningFold(start) { currentValue, move ->
                move
                    .process(currentValue)
                    .first
            }
            .count {
                it == 0
            }

    fun processPart2(start: Int = 50): Any =
        data
            .fold(start to 0) { (currentValue, oldZeros), move ->
                require(move.distance != 0) {
                    "Not moving anywhere"
                }

                val newValue = ((move.process(currentValue).first % 100) + 100) % 100
                val hitsZero = (
                        currentValue != 0 &&
                                (newValue == 0 ||
                                        move.direction == LeftRightDirection.LEFT && newValue > currentValue ||
                                        move.direction == LeftRightDirection.RIGHT && newValue < currentValue)
                        )

                newValue to oldZeros + move.distance / 100 + if (hitsZero) 1 else 0
            }
            .second

    private fun getResourceAsText(path: String): List<String> =
        this
            .javaClass
            .classLoader
            .getResourceAsStream(path)!!
            .bufferedReader()
            .readLines()
}
