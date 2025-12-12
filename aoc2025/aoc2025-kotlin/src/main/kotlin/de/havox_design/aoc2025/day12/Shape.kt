package de.havox_design.aoc2025.day12

typealias Shape = List<CharArray>

val Shape.area: Int
    get() =
        sumOf { line ->
            line
                .count { it == '#' }
        }

fun Shape.rotate(): Shape {
    val rotatedGrid = List(first().size) { CharArray(size) { '.' } }

    for (i in 0..<size) {
        for (j in 0..<first().size) {
            rotatedGrid[j][size - i - 1] = this[i][j]
        }
    }

    return rotatedGrid
}

fun Array<CharArray>.canPlace(rowIndex: Int, colIndex: Int, shape: Shape): Boolean {
    for (i in rowIndex..<(rowIndex + shape.size)) {
        for (j in colIndex..<(colIndex + shape.first().size)) {
            if (shape[i - rowIndex][j - colIndex] == '#' &&
                this[i][j] == '#'
            ) {
                return false
            }
        }
    }
    return true
}

fun Array<CharArray>.place(rowIndex: Int, colIndex: Int, shape: Shape, char: Char = '#') {
    for (i in rowIndex..<(rowIndex + shape.size)) {
        for (j in colIndex..<(colIndex + shape.first().size)) {
            if (shape[i - rowIndex][j - colIndex] == '#') {
                this[i][j] = char
            }
        }
    }
}