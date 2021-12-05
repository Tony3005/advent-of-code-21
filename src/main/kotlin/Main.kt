import day1.DepthScanner
import day2.Command
import day2.run
import day3.co2ScrubberRate
import day3.epsilonRate
import day3.gammaRate
import day3.oxygenRate
import day4.Board
import day5.generateVentedPoints
import day5.parseHydrothermalInput
import java.io.File

fun main() {
    day1()
    day2()
    day3()
    day4()
    day5()
}

fun day1() {
    println("==== Day 1 ===")
    val measurements = File("src/main/resources/adventOfCode1.txt")
        .readLines()
        .map { it.toInt() }

    val depthScanner = DepthScanner(measurements)
    val count = depthScanner.countIncreasedMeasurementsWithFold()
    println("$count increased measurements.")

    val increasedWindowsCount = depthScanner.countIncreasedMeasurementWindowsWithFold()
    println("$increasedWindowsCount increased windows.")
}

fun day2() {
    println("==== Day 2 ===")
    val commands = File("src/main/resources/adventOfCode2.txt")
        .readLines()
        .map {Command(it)}

    val submarineState = run(commands = commands)

    println("Submarine horizontal position: ${submarineState.horizontalPosition}")
    println("Submarine depth: ${submarineState.depth}")
    println("Horizontal position multiplied by depth: ${submarineState.horizontalPosition.times(submarineState.depth)}")
}

fun day3() {
    println("==== Day 3 ===")
    val diagnostic = File("src/main/resources/adventOfCode3.txt")
        .readLines()

    val gammaRate = gammaRate(diagnostic)
    val epsilonRate = epsilonRate(gammaRate)

    println("Gamma Rate: $gammaRate")
    println("EpsilonRate Rate: $epsilonRate")
    println("Power: ${gammaRate.times(epsilonRate)}")

    val oxygenRate = oxygenRate(diagnostic)
    val co2ScrubberRate = co2ScrubberRate(diagnostic)

    println("Oxygen Rate: $oxygenRate")
    println("CO2 Scrubber Rate: $co2ScrubberRate")
    println("Life support: ${oxygenRate.times(co2ScrubberRate)}")
}

fun day4() {
    println("==== Day 4 ===")

    val input = File("src/main/resources/adventOfCode4.txt")
        .readLines().toMutableList()

    val numbers = input.removeFirst().split(',').map { it.toInt() }
    val bingoBoards = input
        .filterNot { it == "" }
        .windowed(5, 5)
        .map { Board(it) }

    var remainingBoards = bingoBoards

    numbers.forEach { number ->
        val (winners, losers) = remainingBoards.map {board ->
            board.checkNumber(number)
            board
        }.partition {
            it.isBingo()
        }

        remainingBoards = losers

        if (winners.size == 1 && losers.isEmpty()) {
            val lastBoardToWin = winners.last()
            val sum = lastBoardToWin.lines.take(5).fold(0) { sum, line ->
                sum + line.cells.sum()
            }
            println("Sum: $sum")
            println("last number: $number")
            println("Final Score: ${number.times(sum)}")

            return
        }
    }
}

fun day5() {
    println("==== Day 5 ===")
    val input = File("src/main/resources/adventOfCode5.txt")
        .readLines()

    val ventedPointsCount = generateVentedPoints(parseHydrothermalInput(input)).count {
        it.value > 1
    }

    println("Overlaps: $ventedPointsCount")
}
