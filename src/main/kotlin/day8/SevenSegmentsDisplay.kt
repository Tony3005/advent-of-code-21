package day8

const val PATTERN_ONE_SEGMENT_LENGTH = 2
const val PATTERN_FOUR_SEGMENT_LENGTH = 4
const val PATTERN_SEVEN_SEGMENT_LENGTH = 3
const val PATTERN_HEIGHT_SEGMENT_LENGTH = 7

typealias Pattern = String

data class Entry(val signals: List<Pattern>, val output: List<Pattern>)

fun String.toEntry(): Entry {
    val (signals, output) = split('|')
        .map { it.trim() }
        .map { it.split(' ')}

    return Entry(signals, output)
}

fun Pattern.includesPattern(pattern: Pattern): Boolean {
    pattern.forEach {
        if (!this.toCharArray().contains(it)) {
            return false
        }
    }

    return true
}

class Decoder(private val entry: Entry) {
    fun output(): Int {
        return entry.output
            .map {
                decodePattern(it)
            }.joinToString(separator = "") { it.toString() }
            .toInt()
    }

    private fun decodePattern(pattern: Pattern): Int {
        return when {
            pattern.length == PATTERN_ONE_SEGMENT_LENGTH -> 1
            pattern.length == PATTERN_FOUR_SEGMENT_LENGTH -> 4
            pattern.length == PATTERN_SEVEN_SEGMENT_LENGTH -> 7
            pattern.length == PATTERN_HEIGHT_SEGMENT_LENGTH -> 8
            pattern.length == 5 && pattern.includesPattern(getSignalOne()) -> 3
            pattern.length == 6 && pattern.includesPattern(getSignalFour()) -> 9
            pattern.length == 6 && pattern.includesPattern(getSignalSeven()) -> 0
            pattern.length == 6 -> 6
            pattern.toCharArray().map {
                getSignalFour().contains(it)
            }.count { it } == 3 -> 5
            else -> 2
        }
    }

    private fun getSignalOne(): String {
        return entry.signals.first {
            it.length == PATTERN_ONE_SEGMENT_LENGTH
        }
    }

    private fun getSignalFour(): String {
        return entry.signals.first {
            it.length == PATTERN_FOUR_SEGMENT_LENGTH
        }
    }

    private fun getSignalSeven(): String {
        return entry.signals.first {
            it.length == PATTERN_SEVEN_SEGMENT_LENGTH
        }
    }

    private fun getSignalHeight(): String {
        return entry.signals.first {
            it.length == PATTERN_HEIGHT_SEGMENT_LENGTH
        }
    }
}

