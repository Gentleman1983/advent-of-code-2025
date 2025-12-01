package de.havox_design.aoc2025.day01

enum class Direction(key: Char, val direction: Int) {
    L('L', -1),
    R('R', 1),
    UNKNOWN('U', 0);

    companion object {
        fun parse(string: String): Direction =
            when(string.uppercase()[0]) {
                'L' -> L
                'R' -> R
                else -> UNKNOWN
            }
    }
}
