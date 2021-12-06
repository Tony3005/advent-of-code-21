package day6

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LanternFishSpawnSimulatorTest {
    @Test
    internal fun `test reproduction`() {
        val input = "3,4,3,1,2"
        val currentState = parseLanternFishStateString(input)

        val expectedState = HashMap<Int, Long>()
        expectedState[1] = 1
        expectedState[2] = 1
        expectedState[3] = 2
        expectedState[4] = 1

        assertEquals(expectedState, currentState)
    }

    @Test
    internal fun `test replication simulation`() {
        val currentState = HashMap<Int, Long>()
        currentState[1] = 1
        currentState[2] = 1
        currentState[3] = 2
        currentState[4] = 1

        val stateAfter18Days = simulateReplication(18, currentState)

        val expectedState = HashMap<Int, Long>()
        expectedState[0] = 3
        expectedState[1] = 5
        expectedState[2] = 3
        expectedState[3] = 2
        expectedState[4] = 2
        expectedState[5] = 1
        expectedState[6] = 5
        expectedState[7] = 1
        expectedState[8] = 4

        assertEquals(expectedState, stateAfter18Days)
    }

    @Test
    internal fun `test total amount of fish`() {
        val currentState = HashMap<Int, Long>()
        currentState[1] = 1
        currentState[2] = 1
        currentState[3] = 2
        currentState[4] = 1

        val stateAfter256Days = simulateReplication(256, currentState)
        val totalFish = stateAfter256Days.toList().map {(_, value) -> value}.sum()

        val expectedFishes = 26984457539
        assertEquals(expectedFishes, totalFish)
    }
}