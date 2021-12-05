package day1

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DepthScannerTest {
    @Test
    internal fun `test increased measurements`() {
        val deptScanner = DepthScanner(listOf(1, 2, 3))
        assertEquals(2, deptScanner.countIncreasedMeasurements())
    }

    @Test
    internal fun `test increased measurements using fold`() {
        val deptScanner = DepthScanner(listOf(1, 2, 3))
        assertEquals(2, deptScanner.countIncreasedMeasurementsWithFold())
    }

    @Test
    internal fun `test increased window using fold`() {
        val deptScanner = DepthScanner(listOf(1, 2, 3, 4))
        assertEquals(1, deptScanner.countIncreasedMeasurementWindowsWithFold())
    }
}
