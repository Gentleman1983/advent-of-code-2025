package de.havox_design.aoc2025.day03

class Lobby(private var filename: String) {
    private val data = getResourceAsText(filename)
        .parse()

    fun processPart1(): Any =
        data
            .sumOf { row ->
                row
                    .calculateJoltage(2)
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

private fun List<Int>.calculateJoltage(numberOfBatteries: Int): Long {
    var index = 0
    val batteriesSwitchedOn = mutableListOf<Int>()

    for (remaining in numberOfBatteries - 1 downTo 0) {
        val remainingBatteries = subList(index, size - remaining)
        val chosenBattery = remainingBatteries.max()

        batteriesSwitchedOn.add(chosenBattery)
        index += 1 + remainingBatteries.indexOf(chosenBattery)
    }
    return batteriesSwitchedOn
        .joinToString("")
        .toLong()
}

private fun List<String>.parse(): List<List<Int>> = map { bank ->
    bank
        .map {
            it.digitToIntOrNull() ?: error("should never be null")
        }
}
