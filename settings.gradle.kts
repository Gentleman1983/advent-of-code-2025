plugins {
    id("com.gradle.develocity") version("4.3")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("aoc2025Libs") {
            from(files("gradle/libs.versions.toml"))
        }
    }
}

include("aoc2025")
include("aoc2025:aoc2025-kotlin")

include("advent-of-code-utils")
apply( "advent-of-code-utils/settings.gradle.kts")
project(":aoc-utils").projectDir = file( path = "$rootDir/advent-of-code-utils/aoc-utils")
project(":aoc-utils:aoc-utils-kotlin").projectDir = file( path = "$rootDir/advent-of-code-utils/aoc-utils/aoc-utils-kotlin")
project(":aoc-utils:aoc-utils-java").projectDir = file( path = "$rootDir/advent-of-code-utils/aoc-utils/aoc-utils-java")

rootProject.name = "advent-of-code-2025"
