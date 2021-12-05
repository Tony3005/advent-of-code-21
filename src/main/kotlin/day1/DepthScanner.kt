package day1

class DepthScanner(private val measurements: List<Int>) {
    fun countIncreasedMeasurements(): Int {
        var counter  = 0
        for (i in 1 until measurements.size) {
           if (measurements[i] > measurements[i-1])
               counter++
        }

        return counter
    }

    fun countIncreasedMeasurementsWithFold(): Int {
        return measurements.foldIndexed(0) { index, counter, value ->
            when {
                index > 0 && value > measurements[index - 1] -> 1 + counter
                else -> counter
            }
        }
    }

    fun countIncreasedMeasurementWindowsWithFold(windowSize: Int = 3): Int {
        return measurements.foldIndexed(0) {index, counter, _ ->
            when {
                index < windowSize -> counter // Do nothing on first window
                isPreviousWindowLower(windowSize, index) -> 1 + counter
                else -> counter
            }
        }
    }

    private fun isPreviousWindowLower(windowSize: Int, index: Int): Boolean {
        val windowSum = measurements.subList(index - windowSize + 1, index + 1).sum()
        val previewWindowSum = measurements.subList(index - windowSize, index).sum()

        return windowSum > previewWindowSum
    }
}
