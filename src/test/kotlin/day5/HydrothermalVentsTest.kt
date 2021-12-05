package day5

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class HydrothermalVentsTest {

    @Test
    internal fun `test parse input`() {
        val input = """
            0,9 -> 5,9
            8,0 -> 0,8
            9,4 -> 3,4
            2,2 -> 2,1
            7,0 -> 7,4
            6,4 -> 2,0
            0,9 -> 2,9
            3,4 -> 1,4
            0,0 -> 8,8
            5,5 -> 8,2
        """.trimIndent()

        val segments = parseHydrothermalInput(input.lines())

        val expectedSegments = listOf(
            Segment(Point(0, 9), Point(5, 9)),
            Segment(Point(8, 0), Point(0, 8)),
            Segment(Point(3, 4), Point(9, 4)),
            Segment(Point(2, 1), Point(2, 2)),
            Segment(Point(7, 0), Point(7, 4)),
            Segment(Point(2, 0), Point(6, 4)),
            Segment(Point(0, 9), Point(2, 9)),
            Segment(Point(1, 4), Point(3, 4)),
            Segment(Point(0, 0), Point(8, 8)),
            Segment(Point(8, 2), Point(5, 5)),
        )

        assertEquals(expectedSegments, segments)
    }

    @Test
    internal fun `test generate vented points`() {
        val input = """
            0,9 -> 5,9
            8,0 -> 0,8
            9,4 -> 3,4
            2,2 -> 2,1
            7,0 -> 7,4
            6,4 -> 2,0
            0,9 -> 2,9
            3,4 -> 1,4
            0,0 -> 8,8
            5,5 -> 8,2
        """.trimIndent()

        val segments = parseHydrothermalInput(input.lines())

        val ventedPoints = generateVentedPoints(segments)
        val expectedPoints = HashMap<Point, Int>()
        expectedPoints[Point(0, 9)] = 2
        expectedPoints[Point(1, 9)] = 2
        expectedPoints[Point(2, 9)] = 2
        expectedPoints[Point(3, 9)] = 1
        expectedPoints[Point(4, 9)] = 1
        expectedPoints[Point(5, 9)] = 1
        expectedPoints[Point(8, 0)] = 1
        expectedPoints[Point(7, 1)] = 2
        expectedPoints[Point(6, 2)] = 1
        expectedPoints[Point(5, 3)] = 2
        expectedPoints[Point(3, 5)] = 1
        expectedPoints[Point(2, 6)] = 1
        expectedPoints[Point(1, 7)] = 1
        expectedPoints[Point(0, 8)] = 1
        expectedPoints[Point(3, 4)] = 2
        expectedPoints[Point(4, 4)] = 3
        expectedPoints[Point(5, 4)] = 1
        expectedPoints[Point(6, 4)] = 3
        expectedPoints[Point(7, 4)] = 2
        expectedPoints[Point(8, 4)] = 1
        expectedPoints[Point(9, 4)] = 1
        expectedPoints[Point(2, 1)] = 1
        expectedPoints[Point(2, 2)] = 2
        expectedPoints[Point(7, 0)] = 1
        expectedPoints[Point(7, 2)] = 1
        expectedPoints[Point(7, 3)] = 2
        expectedPoints[Point(1, 4)] = 1
        expectedPoints[Point(2, 4)] = 1
        expectedPoints[Point(2, 0)] = 1
        expectedPoints[Point(3, 1)] = 1
        expectedPoints[Point(4, 2)] = 1
        expectedPoints[Point(0, 0)] = 1
        expectedPoints[Point(1, 1)] = 1
        expectedPoints[Point(3, 3)] = 1
        expectedPoints[Point(5, 5)] = 2
        expectedPoints[Point(6, 6)] = 1
        expectedPoints[Point(7, 7)] = 1
        expectedPoints[Point(8, 8)] = 1
        expectedPoints[Point(8, 2)] = 1

        assertEquals(expectedPoints, ventedPoints)
    }
}