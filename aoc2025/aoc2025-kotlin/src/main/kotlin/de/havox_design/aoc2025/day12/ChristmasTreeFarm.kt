package de.havox_design.aoc2025.day12

class ChristmasTreeFarm(private var filename: String) {
    private val data = getResourceAsText(filename).parse()
    private val shapes = data.first
    private val regions = data.second

    fun processPart1(): Any =
        regions
            .count { region ->
                region
                    .canFit(shapes)
            }

    fun processPart2(): Any =
        "Merry X-Mas"

    private fun List<String>.parse(): Pair<List<Shape>, List<Region>> {
        val shapes = mutableListOf<Shape>()

        for (i in 1..26 step 5) {
            val grid = mutableListOf<CharArray>()

            for (j in i..<(i + 3)) {
                grid += this[j]
                    .toCharArray()
            }

            shapes += grid
        }

        val regions = (30..this.lastIndex).map { i ->
            this[i]
                .split(": ")
                .let { (sizeStr, quantitiesStr) ->
                    val (n, m) = sizeStr
                        .split("x")
                        .map(String::toInt)

                    Region(n, m, quantitiesStr.split(" ").map(String::toInt).toIntArray())
                }
        }

        return shapes to regions
    }

    private fun getResourceAsText(path: String): List<String> =
        this
            .javaClass
            .classLoader
            .getResourceAsStream(path)!!
            .bufferedReader()
            .readLines()
}
