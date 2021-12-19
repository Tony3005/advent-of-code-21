import day1.DepthScanner
import day10.findCorruptedCharacter
import day10.findMissingCharacters
import day11.findSynchronizedStep
import day11.runSteps
import day11.toOctopusMap
import day12.countPathsToEnd
import day12.toGraph
import day13.*
import day14.polymerize
import day14.toPolymerizationTable
import day15.expand
import day15.findBestPath
import day16.toBinaryString
import day16.toPacket
import day2.Command
import day2.run
import day3.co2ScrubberRate
import day3.epsilonRate
import day3.gammaRate
import day3.oxygenRate
import day4.Board
import day5.generateVentedPoints
import day5.parseHydrothermalInput
import day6.parseLanternFishStateString
import day6.simulateReplication
import day7.getLowestFuelCost
import day8.Decoder
import day8.toEntry
import day9.findBasinSizes
import day9.findLowestPoints
import day9.toBasinMap
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

fun main() = runBlocking<Unit> {
    launch {day1()}
    launch {day2()}
    launch {day3()}
    launch {day4()}
    launch {day5()}
    launch {day6()}
    launch {day7()}
    launch {day8()}
    launch {day9()}
    launch {day10()}
    launch {day11()}
    launch {day12()}
    launch {day13()}
    launch {day14()}
    launch {day15()}
    launch {day16()}
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

fun day6() {
    println("==== Day 6 ===")
    val input = File("src/main/resources/adventOfCode6.txt").readLines().first()

    val initialState = parseLanternFishStateString(input)
    val totalFish = simulateReplication(256, initialState).toList().sumOf { (_, value) -> value }

    println("Amount of fish: $totalFish")
}

fun day7() {
    println("==== Day 7 ===")
    val crabList = File("src/main/resources/adventOfCode7.txt")
        .readLines()
        .first()
        .split(',')
        .map { it.toInt() }

    val fuel = getLowestFuelCost(crabList)
    println("Fuel needed: $fuel")
}

fun day8() {
    println("==== Day 8 ===")
    val outputSum = File("src/main/resources/adventOfCode8.txt")
        .readLines()
        .map { it.toEntry() }
        .sumOf {
            val decoder = Decoder(it)
            decoder.output()
        }

    println("Output sum: $outputSum")
}

fun day9() {
    println("==== Day 9 ===")
    val riskLevel = File("src/main/resources/adventOfCode9.txt")
        .readLines()
        .toBasinMap()
        .findLowestPoints()
        .sumOf {
            it.value + 1
        }

    println("Risk level: $riskLevel")

    val basinSizesProduct = File("src/main/resources/adventOfCode9.txt")
        .readLines()
        .toBasinMap()
        .findBasinSizes()
        .sortedDescending()
        .take(3)
        .reduce {product, size ->
            product * size
        }

    println("Basins product: $basinSizesProduct")
}

fun day10() {
    println("==== Day 10 ===")
    val input = File("src/main/resources/adventOfCode10.txt").readLines()
    val errorScore = input
        .mapNotNull {
            it.findCorruptedCharacter()
        }
        .sumOf {
            when (it) {
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                else -> 0
            }.toInt()
        }

    println("Error Score: $errorScore")

    val autocompleteScores = input
        .filter { it.findCorruptedCharacter() == null }
        .map { it.findMissingCharacters() }
        .map {
            it.fold(0L) { score, missingCharacter ->
                score * 5 + when(missingCharacter) {
                    ')' -> 1L
                    ']' -> 2
                    '}' -> 3
                    '>' -> 4
                    else -> 0
                }
            }
        }.sorted()

    val autocompleteScore = autocompleteScores[autocompleteScores.lastIndex / 2]

    println("Autocomplete Score: $autocompleteScore")
}

fun day11() {
    println("==== Day 11 ===")

    val flashes = File("src/main/resources/adventOfCode11.txt")
        .readLines()
        .toOctopusMap()
        .runSteps(100)

    println("Number of flashes after 100 steps: $flashes")


    val synchronizedStep = File("src/main/resources/adventOfCode11.txt")
        .readLines()
        .toOctopusMap()
        .findSynchronizedStep()

    println("Synchronized Step: $synchronizedStep")
}

fun day12() {
    println("==== Day 12 ===")
    val pathCount = File("src/main/resources/adventOfCode12.txt")
        .readLines()
        .toGraph()
        ?.countPathsToEnd()

    println("Path count: $pathCount")
}

fun day13() {
    println("==== Day 13 ===")
    val input = File("src/main/resources/adventOfCode13.txt")
        .readLines()

    var transparentPaper = input
        .takeWhile { it != "" }
        .toTransparentPaper()

    println("Points left: ${transparentPaper.foldHorizontally(655).size}")

    val foldSteps = input
        .takeLastWhile { it != "" }
        .toFoldingActions()

    foldSteps.forEach { step ->
        transparentPaper = when (step.first) {
            "x" -> transparentPaper.foldVertically(step.second)
            else -> transparentPaper.foldHorizontally(step.second)
        }
    }

    println(transparentPaper.render())
}

fun day14() {
    println("==== Day 14 ===")
    val base = File("src/main/resources/adventOfCode14.txt")
        .readLines()
        .first()

    val polymerizationTable = File("src/main/resources/adventOfCode14.txt")
        .readLines()
        .takeLastWhile { it != "" }
        .toPolymerizationTable()

    val polymerized = base.polymerize(polymerizationTable, 40)
    val groups = polymerized.entries.sortedBy {
        it.value
    }

    val min = groups.first().value
    val max = groups.last().value
    val result = max - min

    println("Result: $result")
}

fun day15() {
    println("==== Day 15 ===")

    val shortestPath = File("src/main/resources/adventOfCode15.txt")
        .readLines()
        .map { row ->
            row.map { Character.getNumericValue(it) }
        }
        .expand(5)
        .findBestPath()

    println("Shortest path cost: $shortestPath")
}

fun day16() {
    println("==== Day 16 ===")

    val packet =  File("src/main/resources/adventOfCode16.txt")
        .readLines()
        .first()
        .toBinaryString()
        .toPacket()

    println("Version sum: ${packet.toVersionSum()}")
    println("Evaluation Result: ${packet.evaluate()}")
}
