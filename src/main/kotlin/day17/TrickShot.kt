package day17

data class Area(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int)
data class Shot(val x: Int, val y: Int, val xV: Int, val yV: Int)

fun Area.findHighestShot(): Int {
    val startX = 0
    val startY = 0

//    var bestShot = 0 to 0
    var topHeight = 0

    (1..200).forEach { xV ->
        (1..200).forEach { yV ->
            var shot = Shot(startX, startY, xV, yV)
            var maxHeight = 0
            var validShot = false
            while ((shot.x < maxX && shot.y > minY)) {
                shot = shot.simulateTurn()

                maxHeight = if (shot.y > maxHeight) shot.y else maxHeight

                if( shot.x <= maxX && shot.y >= minY && shot.x >= minX && shot.y <= maxY) {
                    validShot = true
                    break
                }
            }

            if (maxHeight > topHeight && validShot) {
                topHeight = maxHeight
            }
        }
    }

    return topHeight
}

fun Area.findTotalValidShots(): Int {
    val startX = 0
    val startY = 0

//    var bestShot = 0 to 0
    var totalValidShots = 0

    (1..500).forEach { xV ->
        (-500..500).forEach { yV ->
            var shot = Shot(startX, startY, xV, yV)
            var maxHeight = 0
            var validShot = false
            while ((shot.x < maxX && shot.y > minY)) {
                shot = shot.simulateTurn()

                maxHeight = if (shot.y > maxHeight) shot.y else maxHeight

                if( shot.x <= maxX && shot.y >= minY && shot.x >= minX && shot.y <= maxY) {
                    validShot = true
                    break
                }
            }

            if (validShot) {
                totalValidShots++
            }
        }
    }

    return totalValidShots
}

fun Shot.simulateTurn(): Shot {
    val newXV = when {
        xV > 0 -> xV - 1
        xV < 0 -> xV + 1
        else -> 0
    }

    return Shot(x + xV, y + yV, newXV, yV - 1)
}


