package day7

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CrabAlinerTest {
    @Test
    internal fun `test best position to aline on`() {
        val positions = "16,1,2,0,4,2,7,1,2,14".split(',').map { it.toInt() }
        val bestPosition = getLowestFuelCost(positions)

        assertEquals(168, bestPosition)
    }
}