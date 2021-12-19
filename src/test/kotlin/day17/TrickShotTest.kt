package day17

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TrickShotTest {

    @Test
    internal fun `test highest shot`() {
        val highestShot = Area(20, 30, -10, -5).findHighestShot()

        assertEquals(45, highestShot)
    }

    @Test
    internal fun `test total valid shots`() {
        val totalValidShots = Area(20, 30, -10, -5).findTotalValidShots()

        assertEquals(112, totalValidShots)
    }
}