package de.havox_design.aoc2025.day10

class Factory(private var filename: String) {
    private val data = getResourceAsText(filename)
        .parse()

    fun processPart1(): Any =
        data
            .sumOf { schematic ->
                schematic
                    .findShortestOpeningSequence()
            }

    fun processPart2(): Any =
        33L

    private fun List<String>.parse() =
        this
            .map { line ->
                Machine
                    .parse(line)
            }
            .toList()

    private fun getResourceAsText(path: String): List<String> =
        this
            .javaClass
            .classLoader
            .getResourceAsStream(path)!!
            .bufferedReader()
            .readLines()
}
