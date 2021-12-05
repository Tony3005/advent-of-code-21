package day3

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DiagnosticParserTest {
    @Test
    internal fun `test getting gamma rate`() {
        val diagnostic = listOf(
           "00100",
           "11110",
           "10110",
           "10111",
           "10101",
           "01111",
           "00111",
           "11100",
           "10000",
           "11001",
           "00010",
           "01010",
        )
        val gammaRate = gammaRate(diagnostic)

        assertEquals(0b10110, gammaRate)
    }

    @Test
    internal fun `test getting epsilon rate`() {
        val epsilonRate = epsilonRate(22)

        assertEquals(0b01001, epsilonRate)
    }

    @Test
    internal fun `test getting oxygen rating`() {
        val diagnostic = listOf(
            "00100",
            "11110",
            "10110",
            "10111",
            "10101",
            "01111",
            "00111",
            "11100",
            "10000",
            "11001",
            "00010",
            "01010",
        )
        val oxygenRate = oxygenRate(diagnostic)

        assertEquals(0b10111, oxygenRate)
    }

    @Test
    internal fun `test getting CO2 Scrubber rating`() {
        val diagnostic = listOf(
            "00100",
            "11110",
            "10110",
            "10111",
            "10101",
            "01111",
            "00111",
            "11100",
            "10000",
            "11001",
            "00010",
            "01010",
        )
        val co2ScrubberRate = co2ScrubberRate(diagnostic)

        assertEquals(0b01010, co2ScrubberRate)
    }
}