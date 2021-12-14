package day8

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SevenSegmentsDisplayTest {
    @Test
    fun `Test parse entries`() {
        val input = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"
        val entry = input.toEntry()
        val expectedEntry = Entry(
            listOf("acedgfb", "cdfbe", "gcdfa", "fbcad", "dab", "cefabd", "cdfgeb", "eafb", "cagedb", "ab"),
            listOf("cdfeb", "fcadb", "cdfeb", "cdbaf")
        )

        assertEquals(expectedEntry, entry)
    }

    @Test
    internal fun `test includes pattern`() {
        val pattern = "cfbegad"
        assertTrue(pattern.includesPattern("fe"))
        assertFalse(pattern.includesPattern("xe"))
    }

    @Test
    internal fun `Test decode output`() {
        val input = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"
        val decoder = Decoder(input.toEntry())

        assertEquals(5353, decoder.output())
    }

    @Test
    internal fun `test decode multiple lines`() {
        val outputSum = """
            be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
            edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
            fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
            fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
            aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
            fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
            dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
            bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
            egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
            gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce
        """.trimIndent()
            .lines()
            .map { it.toEntry() }
            .sumOf {
                val decoder = Decoder(it)
                decoder.output()
            }
        assertEquals(61229, outputSum)
    }
}