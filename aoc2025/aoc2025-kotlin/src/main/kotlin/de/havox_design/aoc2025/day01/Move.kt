package de.havox_design.aoc2025.day01

data class Move(val direction: Direction, val distance: Int) {
    fun process(start: Int) =
        (start + calculateChange()) % 100

    private fun calculateChange() =
        direction.direction * distance

    companion object {
        fun parse(string: String): Move {
            val direction = Direction
                .parse(string)
            val distance = string
                .substring(1)
                .toInt()

            return Move(direction, distance)
        }
    }
}
