package day3

fun gammaRate(diagnostic: List<String>): Int {
    return diagnostic
        .map {
            it.toCharArray().map { char -> char.code }
        }.fold(HashMap<Int, Int>()) { counters, intArray ->
            intArray.forEachIndexed { index, value ->
                counters[index] = when {
                    ('1'.code == value) -> counters.getOrDefault(index, 0) + 1
                    else -> counters.getOrDefault(index, 0)
                }
            }
            counters
        }.map {
            when {
                (it.value > diagnostic.size / 2) -> 1
                else -> 0
            }
        }.joinToString("").toInt(2)
}

fun epsilonRate(gammaRate: Int): Int {
    return gammaRate.toString(2).toCharArray().map {
        if ('1' == it) 0 else 1
    }.joinToString("").toInt(2)
}

fun oxygenRate(diagnostic: List<String>) : Int {
    var bitPosition = 0
    var filteredList = diagnostic

    while (filteredList.size > 1) {
        filteredList = filterListByCommonBitSize(bitPosition, filteredList)
        bitPosition ++
    }

    return filteredList.first().toInt(2)
}

fun co2ScrubberRate(diagnostic: List<String>) : Int {
    var bitPosition = 0
    var filteredList = diagnostic

    while (filteredList.size > 1) {
        filteredList = filterListByLeastCommonBitSize(bitPosition, filteredList)
        bitPosition ++
    }

    return filteredList.first().toInt(2)
}

fun filterListByCommonBitSize(bitPosition: Int, list: List<String>): List<String> {
    val counter = list.map {
        it.toCharArray().map { char -> char.code }
    }.count { '1'.code == it[bitPosition] }

    val commonBit = when {
        (counter >= list.size.toFloat() / 2) -> '1'
        else -> '0'
    }

    return list.filter {
        it.toCharArray()[bitPosition] == commonBit
    }
}

fun filterListByLeastCommonBitSize(bitPosition: Int, list: List<String>): List<String> {
    val counter = list.map {
        it.toCharArray().map { char -> char.code }
    }.count { '1'.code == it[bitPosition] }

    val commonBit = when {
        (counter >= list.size.toFloat() / 2) -> '0'
        else -> '1'
    }

    return list.filter {
        it.toCharArray()[bitPosition] == commonBit
    }
}


