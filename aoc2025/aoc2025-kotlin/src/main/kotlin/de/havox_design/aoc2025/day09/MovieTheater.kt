package de.havox_design.aoc2025.day09

class MovieTheater(private var filename: String) {
    private val data = getResourceAsText(filename)

    fun processPart1(): Any =
        50L

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
