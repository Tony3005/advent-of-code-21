package day13

typealias TransparentPaper = Set<Pair<Int, Int>>
typealias FoldingActions = List<Pair<String, Int>>

fun List<String>.toTransparentPaper(): TransparentPaper {
    return map { point ->
        point.split(',').map{it.toInt()}
    }
    .map { pointList -> Pair(pointList[0], pointList[1]) }
    .toSet()
}

fun List<String>.toFoldingActions(): FoldingActions {
    return map {
        val (axis, value) = it
            .split(' ')
            .last()
            .split('=')

        Pair(axis, value.toInt())
    }
}

fun TransparentPaper.foldVertically(x: Int): TransparentPaper {
    val newSet = mutableSetOf<Pair<Int, Int>>()
    forEach {
        if (x < it.first) {
            newSet.add(Pair(x - (it.first - x), it.second))
        } else {
            newSet.add(Pair(it.first, it.second))
        }
    }

    return newSet.toSet()
}

fun TransparentPaper.foldHorizontally(y: Int): TransparentPaper {
    val newSet = mutableSetOf<Pair<Int, Int>>()
    forEach {
        if (y < it.second) {
            newSet.add(Pair(it.first, y - (it.second - y)))
        } else {
            newSet.add(Pair(it.first, it.second))
        }
    }

    return newSet
}

fun TransparentPaper.render(): String {
    val maxX = fold(0) { max, pair ->
        if (pair.first > max) pair.first else max
    }

    val minX = fold(maxX) { min, pair ->
        if (pair.first < min) pair.first else min
    }

    val maxY = fold(0) { max, pair ->
        if (pair.second > max) pair.second else max
    }

    val minY = fold(maxY) { min, pair ->
        if (pair.second < min) pair.second else min
    }
    var row = ""
    for (y in minY..maxY) {
        for (x in minX..maxX) {
            row += if (contains(Pair(x, y))) "#" else "."
        }

        row += "\n"
    }

    return row.removeSuffix("\n")
}
