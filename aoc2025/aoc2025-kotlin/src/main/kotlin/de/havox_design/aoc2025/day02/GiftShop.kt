package de.havox_design.aoc2025.day02

class GiftShop(private var filename: String) {
    private val data = getResourceAsText(filename)
        .parseIdRanges()

    fun processPart1(): Any =
        data
            .filterInvalidIds(Long::isSequenceRepeatedTwice)
            .sum()

    fun processPart2(): Any =
        data
            .filterInvalidIds(Long::isSequenceRepeatedAtLeastTwice)
            .sum()

    private fun getResourceAsText(path: String): List<String> =
        this
            .javaClass
            .classLoader
            .getResourceAsStream(path)!!
            .bufferedReader()
            .readLines()
}

private fun Long.isSequenceRepeatedTwice(): Boolean {
    val id = "$this"

    if (id.length % 2 != 0) {
        return false
    }

    val first = id.take(id.length / 2)
    val second = id.substring(id.length / 2)

    return first == second
}

private fun Long.isSequenceRepeatedAtLeastTwice(): Boolean {
    val id = "$this"

    for (size in 1..id.length / 2) {
        if (id.length % size != 0) {
            continue
        }

        val subsequences = buildList {
            id
                .windowed(size, size) {
                    add(it)
                }
        }
        if (subsequences.all { it == subsequences.first() }) {
            return true
        }
    }

    return false
}

private fun List<LongRange>.filterInvalidIds(isInvalid: (Long) -> Boolean): List<Long> =
    flatMap { range ->
        range
            .filter { isInvalid(it) }
    }

private fun List<String>.parseIdRanges(): List<LongRange> =
    first()
        .split(",")
        .map { range ->
            val (from, to) = range
                .split("-")
                .map(String::toLong)
            from..to
        }
