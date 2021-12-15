package day14

typealias PolymerizationTable = Map<String, Char>
typealias OccurrencesMap = MutableMap<String, MutableMap<Int, MutableMap<Char, Long>>>

fun List<String>.toPolymerizationTable(): PolymerizationTable {
    val map = mutableMapOf<String, Char>()
    map { line ->
        val (pair, insert) = line.split("->").map { it.trim() }
        map[pair] = insert.toCharArray()[0]
    }

    return map.toMap()
}
fun String.polymerize(table: PolymerizationTable, steps: Int): Map<Char, Long> {
    val map = mutableMapOf<String, MutableMap<Int, MutableMap<Char, Long>>>()
    recursivePolymerization(table, steps, map)

    val occurrences = map.entries.map { pairEntry ->
        pairEntry.value.entries.filter { stepMap ->
            stepMap.key == steps
        }
    }.flatten().flatMap {
        it.value.toList()
    }

    val formattedOccurrences = occurrences.groupBy {
        it.first
    }.mapValues { it.value.fold(0L) { sum, it ->
        sum + it.second
    }}.toMutableMap()

    formattedOccurrences[last()] = formattedOccurrences[last()]!!.plus(1)

    return formattedOccurrences.toList().sortedBy { it.second }.toMap()
}

fun String.recursivePolymerization(table: PolymerizationTable, steps: Int, map: OccurrencesMap) {
    windowed(2).forEachIndexed { _, pair ->
        if (steps > 0) {
            val subList = listOf(pair[0], table[pair]!!, pair[1])

            val firstPair = subList.take(2).joinToString("")
            val secondPair = subList.takeLast(2).joinToString("")

            if (null == map[firstPair]?.get(steps - 1)) {
                firstPair.recursivePolymerization(table, steps - 1, map)
            }

            if (null == map[secondPair]?.get(steps - 1)) {
                secondPair.recursivePolymerization(table, steps - 1, map)
            }

            map[firstPair]!![steps - 1]!!.forEach {
                map.incrementOrInsert(pair, steps, it.key, it.value)
            }

            map[secondPair]!![steps - 1]!!.forEach {
                map.incrementOrInsert(pair, steps, it.key, it.value)
            }
        } else {
            map.incrementOrInsert(pair, steps, pair[0], 1)
        }
    }
}

fun OccurrencesMap.incrementOrInsert(pair: String, steps: Int, char: Char, value: Long) {

    if (null == this[pair]) {
        this[pair] = mutableMapOf(steps to mutableMapOf(char to 0))
    }

    if (null == this[pair]!![steps]) {
        this[pair]!![steps] = mutableMapOf(char to 0)
    }

    if (null == this[pair]!![steps]!![char]) {
        this[pair]!![steps]!![char] = 0
    }

    this[pair]!![steps]!![char] = this[pair]!![steps]!![char]!!.plus(value)
}
