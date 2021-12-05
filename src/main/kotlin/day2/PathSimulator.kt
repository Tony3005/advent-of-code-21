package day2

data class SubmarineState(val horizontalPosition: Int = 0, val depth: Int = 0, val aim: Int = 0)

sealed class Command(val action: (SubmarineState) -> SubmarineState)

fun Command(commandString: String): Command {
    val (command, value) = commandString.split(' ')

    return when (command) {
        "forward" -> MoveForward(value.toInt())
        "up" -> Ascend(value.toInt())
        "down" -> Dive(value.toInt())
        else -> error("Invalid command")
    }
}

class MoveForward(val distance: Int): Command({ state ->
    SubmarineState(
        state.horizontalPosition + distance,
        state.depth + (distance.times(state.aim)),
        state.aim
    )
})

class Dive(val depth: Int): Command({ state ->
    SubmarineState(state.horizontalPosition, state.depth, state.aim + depth)
})

class Ascend(val depth: Int): Command({ state ->
    SubmarineState(state.horizontalPosition, state.depth, state.aim - depth)
})


fun run(state: SubmarineState = SubmarineState(), commands: List<Command>): SubmarineState {
    return commands.fold(state) { currentState, command ->
        command.action(currentState)
    }
}


