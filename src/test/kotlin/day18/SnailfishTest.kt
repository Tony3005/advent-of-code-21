package day18

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SnailfishTest {
    @Test
    internal fun `test parse pair`() {
        val node = "[[1,9],[8,5]]".toNode()

        assertEquals(1, node.left?.left?.value)
        assertEquals(9, node.left?.right?.value)
        assertEquals(8, node.right?.left?.value)
        assertEquals(5, node.right?.right?.value)
    }

    @Test
    internal fun `test explode`() {
        val node = "[[[[[9,8],1],2],3],4]".toNode()
        node.getFirstNodeToExplode()?.explode()

        assertEquals("[[[[0,9],2],3],4]", node.toString())

        val node2 = "[7,[6,[5,[4,[3,2]]]]]".toNode()
        node2.getFirstNodeToExplode()?.explode()

        assertEquals("[7,[6,[5,[7,0]]]]", node2.toString())

        val node3 = "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]".toNode()
        node3.getFirstNodeToExplode()?.explode()

        assertEquals("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", node3.toString())

        val node4 = "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]".toNode()
        node4.getFirstNodeToExplode()?.explode()

        assertEquals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", node4.toString())
    }

    @Test
    internal fun `test split`() {
        val node = "[[[[0,7],4],[7,[[8,4],9]]],[1,1]]".toNode()
        node.getFirstNodeToExplode()?.explode()
        node.getFirstNodeToSplit()?.split()

        assertEquals("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]", node.toString())
    }

    @Test
    internal fun `test complex example`() {
        val node = "[[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]],[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]]"
            .toNode()

        node.reduce()

        assertEquals("[[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]", node.toString())
    }

    @Test
    internal fun `test full example`() {
        val result = """
            [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
            [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
            [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
            [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
            [7,[5,[[3,8],[1,4]]]]
            [[2,[2,2]],[8,[8,1]]]
            [2,9]
            [1,[[[9,3],9],[[9,0],[0,7]]]]
            [[[5,[7,4]],7],1]
            [[[[4,2],2],6],[8,7]]
        """.trimIndent()
            .lines()
            .fold("") { total, line ->
                if ("" == total)
                    line
                else {
                    val node = "[$total,$line]".toNode()
                    node.reduce()
                    node.toString()
                }
            }

        assertEquals("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", result)
    }

    @Test
    internal fun `test magnitude`() {
        var magnitude = "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"
            .toNode()
            .magnitude()

        assertEquals(3488, magnitude)

        magnitude = "[[1,2],[[3,4],5]]"
            .toNode()
            .magnitude()

        assertEquals(143, magnitude)

        magnitude = "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"
            .toNode()
            .magnitude()

        assertEquals(1384, magnitude)

        magnitude = "[[[[1,1],[2,2]],[3,3]],[4,4]]"
            .toNode()
            .magnitude()

        assertEquals(445, magnitude)

        magnitude = "[[[[3,0],[5,3]],[4,4]],[5,5]]"
            .toNode()
            .magnitude()

        assertEquals(791, magnitude)
    }

    @Test
    internal fun `test find highest pair`() {
        val numbers = """
            [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
            [[[5,[2,8]],4],[5,[[9,9],0]]]
            [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
            [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
            [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
            [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
            [[[[5,4],[7,7]],8],[[8,3],8]]
            [[9,3],[[9,9],[6,[4,9]]]]
            [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
            [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
        """.trimIndent().lines()

        var max = 0
        numbers.forEachIndexed { i, a ->
            numbers.forEachIndexed { j, b ->
                if (i != j) {
                    max = listOf("[$a,$b]".toNode().reduce().magnitude(), max).maxOf { it }
                }
            }
        }

        assertEquals(3993, max)
    }
}
