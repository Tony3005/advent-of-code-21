package day15

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ChitonsMapTest {

    @Test
    internal fun `test best path`() {
        val inputMap = """
            1163751742
            1381373672
            2136511328
            3694931569
            7463417111
            1319128137
            1359912421
            3125421639
            1293138521
            2311944581
        """.trimIndent()
            .lines()
            .map {
                it.toCharArray().map { Character.getNumericValue(it) }
            }

        inputMap.findBestPath()
        assertEquals(40, inputMap.findBestPath())
    }

    @Test
    internal fun `test expand value`() {
        val value = 9
        assertEquals(9, value.expand(0))
        assertEquals(1, value.expand(1))
        assertEquals(2, value.expand(2))

        val value2 = 8
        assertEquals(8, value2.expand(0))
        assertEquals(9, value2.expand(1))
        assertEquals(1, value2.expand(2))
    }

    @Test
    internal fun `test expand map`() {
        val inputMap = """
            1163751742
            1381373672
            2136511328
            3694931569
            7463417111
            1319128137
            1359912421
            3125421639
            1293138521
            2311944581
        """.trimIndent()
            .lines()
            .map {
                it.toCharArray().map { Character.getNumericValue(it) }
            }

        val expanded = inputMap.expand(5)

        assertEquals(expanded[45][45], 1)
    }
}
