package day4

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BingoSubsystemTest {
    @Test
    internal fun `test parse board`() {
        val stringBoard = """
            22 13 17 11  0
            8  2 23  4 24
            21  9 14 16  7
            6 10  3 18  5
            1 12 20 15 19
        """.trimIndent()

        val board = Board(stringBoard.lines())
        val lines = listOf (
            Line(mutableListOf(22, 13, 17, 11, 0)),
            Line(mutableListOf(8, 2, 23, 4, 24)),
            Line(mutableListOf(21, 9, 14, 16, 7)),
            Line(mutableListOf(6, 10, 3, 18, 5)),
            Line(mutableListOf(1, 12, 20, 15, 19)),
            Line(mutableListOf(22, 8, 21, 6, 1)),
            Line(mutableListOf(13, 2, 9, 10, 12)),
            Line(mutableListOf(17, 23, 14, 3, 20)),
            Line(mutableListOf(11, 4, 16, 18, 15)),
            Line(mutableListOf(0, 24, 7, 5, 19)),
        )

        assertEquals(lines, board.lines)
    }

    @Test
    internal fun `test bingo line`() {
        val stringBoard = """
            22 13 17 11  0
            8  2 23  4 24
            21  9 14 16  7
            6 10  3 18  5
            1 12 20 15 19
        """.trimIndent()

        val board = Board(stringBoard.lines())

        board.checkNumber(8)
        board.checkNumber(2)
        board.checkNumber(23)
        board.checkNumber(4)
        board.checkNumber(24)

        assertTrue(board.isBingo())
    }
}