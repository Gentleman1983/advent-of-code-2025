package de.havox_design.aoc2025.day08

class Playground(private var filename: String) {
    private val data = getResourceAsText(filename)

    fun processPart1(): Any =
        40L

    fun processPart2(): Any =
        0L

    private fun getResourceAsText(path: String): List<String> =
        this.javaClass.classLoader.getResourceAsStream(path)!!.bufferedReader().readLines()
}
