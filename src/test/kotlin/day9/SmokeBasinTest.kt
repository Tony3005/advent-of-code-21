package day9

import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class SmokeBasinTest {
    @Test
    internal fun `test parse input string to map`() {

        val map = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
        """.trimIndent().lines().toBasinMap()

        val expectedMap = listOf(
            listOf(2,1,9,9,9,4,3,2,1,0),
            listOf(3,9,8,7,8,9,4,9,2,1),
            listOf(9,8,5,6,7,8,9,8,9,2),
            listOf(8,7,6,7,8,9,6,7,8,9),
            listOf(9,8,9,9,9,6,5,6,7,8)
        )

        assertContentEquals(expectedMap[0], map[0])
        assertContentEquals(expectedMap[1], map[1])
        assertContentEquals(expectedMap[2], map[2])
        assertContentEquals(expectedMap[3], map[3])
        assertContentEquals(expectedMap[4], map[4])
    }

    @Test
    internal fun `test find lowest map points`() {
        val map = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
        """.trimIndent().lines().toBasinMap()

        val lowestPoints = map.findLowestPoints()

        assertEquals(Point(1, 0, 1), lowestPoints[0])
        assertEquals(Point(9, 0, 0), lowestPoints[1])
        assertEquals(Point(2, 2, 5), lowestPoints[2])
        assertEquals(Point(6, 4, 5), lowestPoints[3])
    }

    @Test
    internal fun `test find basins`() {
        val basins = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
        """.trimIndent()
            .lines()
            .toBasinMap()
            .findBasinSizes()

        assertEquals(3, basins[0])
        assertEquals(9, basins[1])
        assertEquals(14, basins[2])
        assertEquals(9, basins[3])
    }
}
