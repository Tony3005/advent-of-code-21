package day10

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SyntaxScoringTest {
    @Test
    fun `test find corrupted character`() {
        val inputLine = "{([(<{}[<>[]}>{[]{[(<()>"

        assertEquals('}', inputLine.findCorruptedCharacter())
    }

    @Test
    internal fun `test find missing characters`() {
        val input = "[({(<(())[]>[[{[]{<()<>>"

        assertEquals("}}]])})]", input.findMissingCharacters())
    }
}