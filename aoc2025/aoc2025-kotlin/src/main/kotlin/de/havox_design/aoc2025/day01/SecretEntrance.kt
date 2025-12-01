package de.havox_design.aoc2025.day01

class SecretEntrance(private var filename: String) {
    private val data = getResourceAsText(filename)
        .map { row -> Move.parse(row) }

    fun processPart1(start: Int = 50): Any =
        data
            .runningFold(start) { currentValue, move ->
            move
                .process(currentValue)
        }
            .count {
                it == 0
            }

    fun processPart2(): Any =
        0L

    private fun getResourceAsText(path: String): List<String> =
        this
            .javaClass
            .classLoader
            .getResourceAsStream(path)!!
            .bufferedReader()
            .readLines()
}
