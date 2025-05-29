plugins {
    id("com.gradle.develocity") version("4.0.2")
}

rootProject.name = "advent-of-code-2025"

include("aoc2025")
include("aoc2025:aoc2025-java")

include("utils")
include("utils:utils-java")
include("utils:utils-kotlin")
