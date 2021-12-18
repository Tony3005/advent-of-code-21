package day15

import java.util.*

typealias ChitonsMap = List<List<Int>>

data class Point(
    val x: Int,
    val y: Int,
    val value: Int,
    val id: Int,
    val dist: Int = Int.MAX_VALUE,
    val prev: Point? = null
) {
    override fun equals(other: Any?): Boolean = other is Point && x == other.x && y == other.y
    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}

fun ChitonsMap.findBestPath(): Int? {
    val width = first().size
    val result = arrayOfNulls<Point>(size * first().size)
    val queue = PriorityQueue<Point>{ p1, p2 -> p1.dist - p2.dist }
    val visited = arrayOfNulls<Boolean>(size * first().size)
    val target = (size * first().size) - 1

    val sourcePoint = Point(0, 0, 0, this[0][0], 0)
    queue.add(sourcePoint)
    result[0] = sourcePoint

    while (queue.isNotEmpty()) {
        val currentPoint = queue.poll()

        if (null != visited[currentPoint.id]) continue
        visited[currentPoint.id] = true

        val neighbours = listOfNotNull(
            getLeftPoint(currentPoint),
            getTopPoint(currentPoint),
            getRightPoint(currentPoint),
            getBottomPoint(currentPoint)
        )

        neighbours.forEach {
            val neighbourId = (it.second * width) + it.first
            val neighbourValue = this[it.second][it.first]
            if (null != visited[currentPoint.id]) {
                if (null == result[neighbourId] || result[neighbourId]!!.dist > currentPoint.dist + neighbourValue) {
                    val resultPoint = Point(
                        it.first,
                        it.second,
                        neighbourValue,
                        neighbourId,
                        currentPoint.dist + neighbourValue,
                        currentPoint
                    )

                    result[neighbourId] = resultPoint
                    queue.add(resultPoint)
                }
            }
        }
    }

    return result[target]!!.dist
}

fun ChitonsMap.getLeftPoint(p: Point): Pair<Int, Int>? {
    return if (p.x > 0) (p.x - 1 to p.y) else null
}

fun ChitonsMap.getTopPoint(p: Point): Pair<Int, Int>? {
    return if (p.y > 0) (p.x to p.y - 1) else null
}

fun ChitonsMap.getRightPoint(p: Point): Pair<Int, Int>? {
    return if (p.x < this.first().lastIndex) (p.x + 1 to p.y) else null
}

fun ChitonsMap.getBottomPoint(p: Point): Pair<Int, Int>? {
    return if (p.y < lastIndex) (p.x to p.y + 1) else null
}

fun ChitonsMap.expand(n: Int = 5): List<List<Int>> {
    val height = size
    val expandedMapRight = mutableListOf<MutableList<Int>>()

    forEachIndexed {y , row ->
        expandedMapRight.add(mutableListOf())
        for (i in 0 until n) {
            row.forEach {value ->
                expandedMapRight[y].add(value.expand(i))
            }
        }
    }

    val expandedMapBottom = mutableListOf<MutableList<Int>>()

    for (i in 0 until n) {
        expandedMapRight.forEachIndexed {y , row ->
            expandedMapBottom.add(mutableListOf())
            row.forEach {value ->
                expandedMapBottom[y + (i * height)].add(value.expand(i))
            }
        }
    }

    return expandedMapBottom.toList()
}

fun Int.expand(n: Int): Int {
    val newVal = (this + n )

    return if (newVal < 10) newVal else newVal - 9
}
