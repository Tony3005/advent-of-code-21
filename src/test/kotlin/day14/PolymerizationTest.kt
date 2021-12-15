package day14

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PolymerizationTest {

    @Test
    internal fun `test parse polymerization table`() {
        val polymerizationTable = """
            CH -> B
            HH -> N
            CB -> H
            NH -> C
            HB -> C
            HC -> B
            HN -> C
            NN -> C
            BH -> H
            NC -> B
            NB -> B
            BN -> B
            BB -> N
            BC -> B
            CC -> N
            CN -> C
        """.trimIndent()
        .lines()
        .toPolymerizationTable()

        assertEquals('H', polymerizationTable["CB"])
    }

    @Test
    internal fun `test polymerization`() {
        val base = "NNCB"

        val polymerizationTable = """
            CH -> B
            HH -> N
            CB -> H
            NH -> C
            HB -> C
            HC -> B
            HN -> C
            NN -> C
            BH -> H
            NC -> B
            NB -> B
            BN -> B
            BB -> N
            BC -> B
            CC -> N
            CN -> C
        """.trimIndent()
            .lines()
            .toPolymerizationTable()

        val expectedMap = mapOf(
            'N' to 2L,
            'B' to 6,
            'C' to 4,
            'H' to 1
        )
        assertEquals(expectedMap, base.polymerize(polymerizationTable, 2))
    }
}