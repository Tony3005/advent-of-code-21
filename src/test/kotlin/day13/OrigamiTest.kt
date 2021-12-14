package day13

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OrigamiTest {
    @Test
    internal fun `test one fold`() {
        val input = """
            6,10
            0,14
            9,10
            0,3
            10,4
            4,11
            6,0
            6,12
            4,1
            0,13
            10,12
            3,4
            3,0
            8,4
            1,10
            2,14
            8,10
            9,0
            
            fold along y=7
            fold along x=5
        """.trimIndent()
            .lines()

        var transparentPaper = input.takeWhile { it != "" }.toTransparentPaper()

        val foldSteps = input
            .takeLastWhile { it != "" }
            .toFoldingActions()

        foldSteps.forEach { step ->
            transparentPaper = when (step.first) {
                "x" -> transparentPaper.foldVertically(step.second)
                else -> transparentPaper.foldHorizontally(step.second)
            }
        }

        val count = transparentPaper.size

        assertEquals(16, count)
    }

    @Test
    internal fun `test render transparent paper`() {
        val input = """
            6,10
            0,14
            9,10
            0,3
            10,4
            4,11
            6,0
            6,12
            4,1
            0,13
            10,12
            3,4
            3,0
            8,4
            1,10
            2,14
            8,10
            9,0
            
            fold along y=7
            fold along x=5
        """.trimIndent()
            .lines()

        var transparentPaper = input.takeWhile { it != "" }.toTransparentPaper()

        val foldSteps = input
            .takeLastWhile { it != "" }
            .toFoldingActions()

        foldSteps.forEach { step ->
            transparentPaper = when (step.first) {
                "x" -> transparentPaper.foldVertically(step.second)
                else -> transparentPaper.foldHorizontally(step.second)
            }
        }

        val expectedRender = """
            #####
            #...#
            #...#
            #...#
            #####
        """.trimIndent()

        assertEquals(expectedRender, transparentPaper.render())
    }
}
