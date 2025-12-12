package de.havox_design.aoc2025

import de.havox_design.aoc.utils.kotlin.helpers.AocMainClassHelper
import de.havox_design.aoc2025.day01.SecretEntrance
import de.havox_design.aoc2025.day02.GiftShop
import de.havox_design.aoc2025.day03.Lobby
import de.havox_design.aoc2025.day04.PrintingDepartment
import de.havox_design.aoc2025.day05.Cafeteria
import de.havox_design.aoc2025.day06.TrashCompactor
import de.havox_design.aoc2025.day07.Laboratories
import de.havox_design.aoc2025.day08.Playground
import de.havox_design.aoc2025.day09.MovieTheater
import de.havox_design.aoc2025.day10.Factory
import de.havox_design.aoc2025.day11.Reactor
import de.havox_design.aoc2025.day12.ChristmasTreeFarm

class MainClass : AocMainClassHelper {
    override fun getYear(): Int = 2025

    override fun processYear(args: Array<String>) {
        val daysSelected = args
            .any { argument -> argument.startsWith("day") }

        var day = 1
        day(
            getDayString(day),
            SecretEntrance(getFileName(day))::processPart1,
            SecretEntrance(getFileName(day))::processPart2,
            daysSelected,
            args
        )

        day = 2
        day(
            getDayString(day),
            GiftShop(getFileName(day))::processPart1,
            GiftShop(getFileName(day))::processPart2,
            daysSelected,
            args
        )

        day = 3
        day(
            getDayString(day),
            Lobby(getFileName(day))::processPart1,
            Lobby(getFileName(day))::processPart2,
            daysSelected,
            args
        )

        day = 4
        day(
            getDayString(day),
            PrintingDepartment(getFileName(day))::processPart1,
            PrintingDepartment(getFileName(day))::processPart2,
            daysSelected,
            args
        )

        day = 5
        day(
            getDayString(day),
            Cafeteria(getFileName(day))::processPart1,
            Cafeteria(getFileName(day))::processPart2,
            daysSelected,
            args
        )

        day = 6
        day(
            getDayString(day),
            TrashCompactor(getFileName(day))::processPart1,
            TrashCompactor(getFileName(day))::processPart2,
            daysSelected,
            args
        )

        day = 7
        day(
            getDayString(day),
            Laboratories(getFileName(day))::processPart1,
            Laboratories(getFileName(day))::processPart2,
            daysSelected,
            args
        )

        day = 8
        day(
            getDayString(day),
            Playground(getFileName(day))::processPart1,
            Playground(getFileName(day))::processPart2,
            daysSelected,
            args
        )

        day = 9
        day(
            getDayString(day),
            MovieTheater(getFileName(day))::processPart1,
            MovieTheater(getFileName(day))::processPart2,
            daysSelected,
            args
        )

        day = 10
        day(
            getDayString(day),
            Factory(getFileName(day))::processPart1,
            Factory(getFileName(day))::processPart2,
            daysSelected,
            args
        )

        day = 11
        day(
            getDayString(day),
            Reactor(getFileName(day))::processPart1,
            Reactor(getFileName(day))::processPart2,
            daysSelected,
            args
        )

        day = 12
        day(
            getDayString(day),
            ChristmasTreeFarm(getFileName(day))::processPart1,
            ChristmasTreeFarm(getFileName(day))::processPart2,
            daysSelected,
            args
        )
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val instance = MainClass()
            instance.processYear(args)
        }
    }
}
