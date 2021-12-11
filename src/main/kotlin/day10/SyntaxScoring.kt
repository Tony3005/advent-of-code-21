package day10

fun String.findCorruptedCharacter(): Char? {
    val history = mutableListOf<Char>()
    forEach {
        if (it.isOpening()) {
            history.add(it)
        } else {
            if (history.removeLast() != it.getMatchingOpeningChar()) {
                return it
            }
        }
    }

    return null
}

fun String.findMissingCharacters(): String {
    val history = mutableListOf<Char>()
    forEach {
        if (it.isOpening()) history.add(it) else history.removeLast()
    }

    return history.foldRight("") { openingCharacter, missingCharacters ->
        missingCharacters + openingCharacter.getMatchingClosingChar()
    }
}

fun Char.isOpening(): Boolean {
    return setOf('(', '[', '{', '<').contains(this)
}

fun Char.getMatchingOpeningChar(): Char? {
    return when (this) {
        ')' -> '('
        ']' -> '['
        '}' -> '{'
        '>' -> '<'
        else -> null
    }
}

fun Char.getMatchingClosingChar(): Char? {
    return when (this) {
        '(' -> ')'
        '[' -> ']'
        '{' -> '}'
        '<' -> '>'
        else -> null
    }
}



