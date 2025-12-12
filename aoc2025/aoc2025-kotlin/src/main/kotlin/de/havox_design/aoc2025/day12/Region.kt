package de.havox_design.aoc2025.day12

class Region(val n: Int, val m: Int, val quantities: IntArray)

fun Region.canFit(shapes: List<Shape>): Boolean {
    if (n * m < quantities.withIndex().sumOf { (index, count) -> shapes[index].area * count }) {
        return false
    }

    val grid = Array(n) { CharArray(m) { '.' } }
    val rotatedShapes = shapes
        .map { shape ->
        val rotations = mutableListOf(shape)

            repeat(3) {
            rotations += rotations
                .last()
                .rotate()
        }

        rotations
    }

    fun backtrack(shapeIndex: Int): Boolean {
        if (shapeIndex == quantities.size) {
            return true
        }

        if (quantities[shapeIndex] == 0) {
            return backtrack(shapeIndex + 1)
        }

        val rotations = rotatedShapes[shapeIndex]

        for (shape in rotations) {
            for (i in 0..<(n - shape.size + 1)) {
                for (j in 0..<(m - shape.first().size + 1)) {
                    if (grid.canPlace(i, j, shape)) {
                        grid
                            .place(i, j, shape)
                        quantities[shapeIndex]--

                        if (backtrack(shapeIndex)) {
                            return true
                        }

                        grid
                            .place(i, j, shape, '.')
                        quantities[shapeIndex]++
                    }
                }
            }
        }

        return false
    }

    return backtrack(0)
}