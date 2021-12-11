package day11

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestOctopusEnergy {
    @Test
    internal fun `test simulate 1 step`() {
        val flashes = """
            11111
            19991
            19191
            19991
            11111
        """.trimIndent()
            .lines()
            .toOctopusMap()
            .runSteps(1)


        assertEquals(9, flashes)
    }

    @Test
    internal fun `test simulate 10 step`() {
        val flashes = """
            5483143223
            2745854711
            5264556173
            6141336146
            6357385478
            4167524645
            2176841721
            6882881134
            4846848554
            5283751526
        """.trimIndent()
            .lines()
            .toOctopusMap()
            .runSteps(10)


        assertEquals(204, flashes)
    }

    @Test
    internal fun `Test find synchronized step`() {
        val step = """
            5483143223
            2745854711
            5264556173
            6141336146
            6357385478
            4167524645
            2176841721
            6882881134
            4846848554
            5283751526
        """.trimIndent()
            .lines()
            .toOctopusMap()
            .findSynchronizedStep()

        assertEquals(195, step)
    }
}
