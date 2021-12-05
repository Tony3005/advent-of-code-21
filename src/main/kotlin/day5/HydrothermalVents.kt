package day5

data class Point(val x: Int, val y: Int)
data class Segment(val a: Point, val b: Point )

fun parseHydrothermalInput(input: List<String>): List<Segment> {
    return input.map { line ->
        val points = line.split("->").map {
            val pointValues = it
                .split(',')
                .map { valueString -> valueString.trim().toInt() }

            Point(pointValues[0], pointValues[1])
        }
        val p1 = points[0]
        val p2 = points[1]

        when {
            p1.y == p2.y && points[0].x < points[1].x -> Segment(points[0], points[1])
            p1.y == p2.y && points[0].x > points[1].x -> Segment(points[1], points[0])
            points[0].y < points[1].y -> Segment(points[0], points[1])
            else -> Segment(points[1], points[0])
        }
    }
}

fun generateVentedPoints(segments: List<Segment>): HashMap<Point, Int> {
    val points = generateVerticalAndHorizontalSegmentPoints(segments)
    val diagonalPoints = generateDiagonalSegmentPoints(segments)

    diagonalPoints.forEach { (point, count) ->
        points[point] = points.getOrDefault(point, 0) + count
    }

    return points
}

fun generateVerticalAndHorizontalSegmentPoints(segments: List<Segment>) : HashMap<Point, Int> {
    val points = HashMap<Point, Int>()
    segments.filter {
        it.a.x == it.b.x || it.a.y == it.b.y
    }.forEach { segment ->
        if (segment.a.y == segment.b.y) {
            for (x in segment.a.x .. segment.b.x) {
                val segmentPoint = Point(x, segment.a.y)
                points[segmentPoint] =  points.getOrDefault(segmentPoint, 0) + 1
            }
        }

        if (segment.a.x == segment.b.x) {
            for (y in segment.a.y .. segment.b.y) {
                val segmentPoint = Point(segment.a.x, y)
                points[segmentPoint] =  points.getOrDefault(segmentPoint, 0) + 1
            }
        }
    }

    return points
}

fun generateDiagonalSegmentPoints(segments: List<Segment>) : HashMap<Point, Int> {
    val points = HashMap<Point, Int>()
    segments.filter {
        it.a.x != it.b.x && it.a.y != it.b.y
    }.forEach { segment->
        when {
            segment.a.x < segment.b.x && segment.a.y < segment.b.y -> {
                for (x in segment.a.x .. segment.b.x) {
                    val y = segment.a.y + (x - segment.a.x)
                    val segmentPoint = Point(x, y)
                    points[segmentPoint] = points.getOrDefault(segmentPoint, 0) + 1
                }
            }
            segment.a.x < segment.b.x && segment.a.y > segment.b.y -> {
                for (x in segment.a.x .. segment.b.x) {
                    val y = segment.b.y + (x - segment.a.x)
                    val segmentPoint = Point(x, y)
                    points[segmentPoint] = points.getOrDefault(segmentPoint, 0) + 1
                }
            }
            segment.a.x > segment.b.x && segment.a.y > segment.b.y -> {
                for (x in segment.b.x .. segment.a.x ) {
                    val y = segment.b.y + (segment.a.x - x)
                    val segmentPoint = Point(x, y)
                    points[segmentPoint] = points.getOrDefault(segmentPoint, 0) + 1
                }
            }
            segment.a.x > segment.b.x && segment.a.y < segment.b.y -> {
                for (x in segment.b.x .. segment.a.x ) {
                    val y = segment.a.y + (segment.a.x - x)
                    val segmentPoint = Point(x, y)
                    points[segmentPoint] = points.getOrDefault(segmentPoint, 0) + 1
                }
            }
        }
    }

    return points
}
