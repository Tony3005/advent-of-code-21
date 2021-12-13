package day12

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PassagePathingTest {

    @Test
    internal fun `test find path count`() {
        val count = """
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end
        """.trimIndent()
            .lines()
            .toGraph()
            ?.countPathsToEnd()

        assertEquals(36, count)
    }
}
