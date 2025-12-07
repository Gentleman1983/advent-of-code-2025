package de.havox_design.aoc2025.day07

class Laboratories(private var filename: String) {
    private val data = getResourceAsText(filename)

    fun processPart1(): Any {
        val emitter = data[0]
            .indexOf('S')
        val beams = mutableSetOf<Int>(emitter)
        var sum = 0L

        data
            .forEach { line ->
            if (line.contains('^')) {
                val splitters = mutableSetOf<Int>()

                for (i in line.indices)
                    if (line[i] == '^') splitters.add(i)

                val collisions = beams.intersect(splitters)

                sum += collisions.size
                beams.removeAll(collisions)

                for (collision in collisions) {
                    beams.add(collision - 1)
                    beams.add(collision + 1)
                }
            }
        }

        return sum
    }

        fun processPart2(): Any =
            40L

        private fun getResourceAsText(path: String): List<String> =
            this.javaClass.classLoader.getResourceAsStream(path)!!.bufferedReader().readLines()
    }
