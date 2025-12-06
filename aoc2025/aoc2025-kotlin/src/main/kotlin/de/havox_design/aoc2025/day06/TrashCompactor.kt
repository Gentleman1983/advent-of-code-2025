package de.havox_design.aoc2025.day06

class TrashCompactor(private var filename: String) {
    private val data = getResourceAsText(filename)
        .splitLinesForTrashCompactor()
        .convertToSingleCalculations()

    fun processPart1(): Any =
        data.sumOf { calculation ->
            calculation.operator.compute(calculation.elements)
        }

    fun processPart2(): Any =
        0L

    private fun List<List<String>>.convertToSingleCalculations(): List<Calculation> {
        val size = this.validateAndGetSize()
        val calculations: MutableList<Calculation> = mutableListOf()
        var dataIndex = 0;

        while(dataIndex < size) {
            var index = 0;
            val elements: MutableList<Long> = mutableListOf()

            while (index < this.size - 1) {
                elements
                    .add(this[index][dataIndex].toLong())

                index++
            }

            calculations
                .add(Calculation(Operator.parse(this[index][dataIndex]), elements))

            dataIndex++
        }

        return calculations
    }

    private fun List<List<String>>.validateAndGetSize(): Int {
        val size = this[0].size

        for(row in this) {
            assert(row.size == size)
        }

        return size
    }

    private fun List<String>.splitLinesForTrashCompactor(): List<List<String>> =
        this
            .map { row ->
                row
                    .split("\\s+".toRegex())
                    .filter { element ->
                        element.isNotBlank()
                    }
            }

    private fun getResourceAsText(path: String): List<String> =
        this
            .javaClass
            .classLoader
            .getResourceAsStream(path)!!
            .bufferedReader()
            .readLines()
}
