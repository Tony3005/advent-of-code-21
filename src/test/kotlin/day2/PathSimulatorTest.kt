package day2

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PathSimulatorTest {
    @Test
    internal fun `test going forward`() {
        val submarineState = SubmarineState(aim=5)
        val newState = MoveForward(3).action(submarineState)

        assertEquals(SubmarineState(3, 15, 5), newState)
    }

    @Test
    internal fun `test diving`() {
        val action = Dive(3)
        val newState = action.action(SubmarineState())

        assertEquals(3, newState.aim)
    }

    @Test
    internal fun `test ascending`() {
        val command = Ascend(3)
        val newState = command.action(SubmarineState(0, 10, 10))

        assertEquals(7, newState.aim)
    }

    @Test
    internal fun `test command list`() {
        val commands = listOf(
            MoveForward(5),
            Dive(5),
            MoveForward(8),
            Ascend(3),
            Dive(8),
            MoveForward(2)
        )
        val finalState = run(SubmarineState(), commands)

        assertEquals(SubmarineState(15, 60,10), finalState)
    }

    @Test
    internal fun `test generate command list`() {
        val commandListString = listOf(
            "forward 5",
            "forward 2",
            "down 7",
            "up 3",
            "forward 1"
        )

        val commandList = commandListString.map {Command(it)}

        val expectedCommandList = listOf(
            MoveForward(5),
            MoveForward(2),
            Dive(7),
            Ascend(3),
            MoveForward(1)
        )

        assertEquals(expectedCommandList, commandList)
    }

    @Test
    internal fun `test increasing aim`() {
        val submarineState = SubmarineState()

        val newState = Dive(5).action(submarineState)

        assertEquals(SubmarineState(0, 5, 5), newState)
    }
}