package day9

typealias BasinMap = List<List<Int>>

data class Point(val x: Int, val y: Int, val value: Int)

fun List<String>.toBasinMap(): BasinMap {
    return map { it.toCharArray().map { char -> Character.getNumericValue(char)} }
        .map { it }
}

fun BasinMap.findLowestPoints(): List<Point> {
    return mapIndexed {y, row ->
        row.mapIndexed {x, value ->
            Point(x, y , value)
        }.filter {
            this.checkLowerThanAdjacentPoints(it)
        }
    }.flatten()
}

fun BasinMap.findBasinSizes(): List<Int> {
    val points = findLowestPoints()
    return points.map {
        val visitedPoints = mutableSetOf(it)
        traverseBasin(it, visitedPoints)
    }
}

fun BasinMap.traverseBasin(p: Point, visitedPoints: MutableSet<Point>): Int {
    val nextPoints =  listOfNotNull(
        getLeftPoint(p),
        getTopPoint(p),
        getRightPoint(p),
        getBottomPoint(p)
    ).filterNot { nextPoint ->
        9 == nextPoint.value || (visitedPoints.any { visitedPoint ->
            visitedPoint.x == nextPoint.x && visitedPoint.y == nextPoint.y
        })
    }

    nextPoints.forEach {
        visitedPoints.add(it)
    }

    return 1 + nextPoints.sumOf { traverseBasin(it, visitedPoints) }
}

fun BasinMap.checkLowerThanAdjacentPoints(p: Point): Boolean {
    val checkedValue = this[p.y][p.x]

    val left = getLeftPoint(p)
    val top = getTopPoint(p)
    val right = getRightPoint(p)
    val bottom = getBottomPoint(p)

    return checkedValue < listOfNotNull(left?.value, top?.value, right?.value, bottom?.value).minOrNull()!!
}

fun BasinMap.getLeftPoint(p: Point): Point? {
    return if (p.x > 0) Point(p.x - 1, p.y, this[p.y][p.x - 1]) else null
}

fun BasinMap.getTopPoint(p: Point): Point? {
    return if (p.y > 0) Point(p.x, p.y - 1, this[p.y - 1][p.x]) else null
}

fun BasinMap.getRightPoint(p: Point): Point? {
    return if (p.x < this.first().lastIndex) Point(p.x + 1, p.y, this[p.y][p.x + 1]) else null
}

fun BasinMap.getBottomPoint(p: Point): Point? {
    return if (p.y < lastIndex) Point(p.x, p.y + 1, this[p.y + 1][p.x]) else null
}
