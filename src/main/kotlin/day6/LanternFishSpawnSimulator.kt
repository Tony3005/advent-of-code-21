package day6

fun parseLanternFishStateString(input: String): HashMap<Int, Long> {
    val fishList = input.split(',').map{ it.toInt()}

    val state = HashMap<Int, Long>()
    fishList.forEach { dueDays ->
        state.compute(dueDays) { _, value ->
            if (value == null) 1 else value + 1
        }
    }

    return state
}

fun simulateReplication(days: Int, state: HashMap<Int, Long>): HashMap<Int, Long> {
    var currentState = state
    for (day in 1..days) {
        val newState = HashMap<Int, Long>()
        for (i in 0..8) {
            newState[i] = 0
        }

        currentState.forEach { (key, value) ->
            if (0 == key) {
                newState[6] = value
                newState[8] = value
            } else {
                newState[key - 1] = newState[key - 1]!! + value
            }
        }

        currentState = newState
    }

    return currentState
}