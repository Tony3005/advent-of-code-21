package day7

import kotlin.math.abs

fun getLowestFuelCost(positions: List<Int>): Int {
    val groupedCrabs = positions
        .groupBy { it }
        .mapValues { (_, list) -> list.size }

    val min = positions.minOrNull() ?: 0
    val max = positions.maxOrNull() ?: 0

    return (min..max).toList().fold(0) { fuel, checkedPosition ->
        val fuelNeeded = groupedCrabs.toList().fold(0) {sum, (position, numberOfCrabs) ->
            val distance = abs(position - checkedPosition)
            sum + when (position) {
                checkedPosition -> 0
                else -> ((distance.toFloat() / 2) * (1 + distance) * numberOfCrabs).toInt()
            }
        }

       if (0 == fuel || fuel > fuelNeeded) fuelNeeded else fuel
    }
}
