package de.havox_design.aoc2025.day10

import de.havox_design.aoc.utils.kotlin.helpers.tests.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class FactoryTest {

    @ParameterizedTest
    @MethodSource("getDataForTestProcessPart1")
    fun testProcessPart1(filename: String, expectedResult: Int) =
        Factory(filename).processPart1().shouldBe(expectedResult)

    @ParameterizedTest
    @MethodSource("getDataForTestProcessPart2")
    fun testProcessPart2(filename: String, expectedResult: Long) =
        Factory(filename).processPart2().shouldBe(expectedResult)

    companion object {
        @JvmStatic
        private fun getDataForTestProcessPart1(): Stream<Arguments> =
            Stream.of(
                Arguments.of("de/havox_design/aoc2025/day10/day10sample1.txt", 7),
                Arguments.of("de/havox_design/aoc2025/day10/day10sample2.txt", 2),
                Arguments.of("de/havox_design/aoc2025/day10/day10sample3.txt", 3),
                Arguments.of("de/havox_design/aoc2025/day10/day10sample4.txt", 2)
            )

        @JvmStatic
        private fun getDataForTestProcessPart2(): Stream<Arguments> =
            Stream.of(
                Arguments.of("de/havox_design/aoc2025/day10/day10sample1.txt", 33L)//,
                //Arguments.of("de/havox_design/aoc2025/day10/day10sample2.txt", 10L),
                //Arguments.of("de/havox_design/aoc2025/day10/day10sample3.txt", 12L),
                //Arguments.of("de/havox_design/aoc2025/day10/day10sample4.txt", 11L)
            )
    }
}
