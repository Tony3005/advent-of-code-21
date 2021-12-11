package day11

typealias OctopusMap = List<List<Int>>
typealias MutableOctopusMap = MutableList<MutableList<Int>>
typealias Octopus = Pair<Int, Int>

fun List<String>.toOctopusMap(): OctopusMap {
    return map { row ->
        row.map{ Character.getNumericValue(it) }
    }
}

fun OctopusMap.findSynchronizedStep(): Int {
    var newMap = this.map { it.toMutableList() }.toMutableList()
    var steps = 0
    var flashes = 0
    while (flashes != 100) {
        flashes = newMap.runStep()
        steps ++
    }

    return steps
}

fun OctopusMap.runSteps(steps: Int): Int {
    var newMap = this.map { it.toMutableList() }.toMutableList()
    var flashes = 0
    repeat(steps) {
        flashes += newMap.runStep()
    }

    return flashes
}
fun MutableOctopusMap.runStep(): Int {
    val octopusesToFlash = listOf<Octopus>().toMutableList()
    val flashedOctopuses = HashSet<Octopus>()
    var flashes = 0
    indices.forEach { y ->
        indices.forEach { x ->
            this[y][x] ++
            if (this[y][x] > 9) {
                val octopus = Pair(x, y)
                octopusesToFlash.add(octopus)
                flashedOctopuses.add(octopus)
            }
        }
    }

    while (octopusesToFlash.isNotEmpty()) {
        val octopus = octopusesToFlash.removeFirst()
        incrementAdjacentCells(octopusesToFlash, flashedOctopuses, octopus.first, octopus.second)
    }

    indices.forEach { y ->
        indices.forEach { x ->
            if (this[y][x] > 9) {
                this[y][x] = 0
                flashes ++
            }
        }
    }

    return flashes
}

fun MutableOctopusMap.incrementAdjacentCells(octopusesToFlash: MutableList<Octopus>, flashedOctopuses: HashSet<Octopus>, x: Int, y: Int) {
    //left
    if (x > 0)
        this.incrementCell(octopusesToFlash, flashedOctopuses, x - 1, y)

    //top left
    if (x > 0 && y > 0)
        this.incrementCell(octopusesToFlash, flashedOctopuses, x - 1, y - 1)

    //top
    if (y > 0)
        this.incrementCell(octopusesToFlash, flashedOctopuses, x, y - 1)

    //top right
    if (x < this.first().lastIndex && y > 0)
        this.incrementCell(octopusesToFlash, flashedOctopuses, x + 1, y - 1)

    //right
    if (x < this.first().lastIndex)
        this.incrementCell(octopusesToFlash, flashedOctopuses, x + 1, y)

    //bottom right
    if (x < this.first().lastIndex && y < this.lastIndex)
        this.incrementCell(octopusesToFlash, flashedOctopuses, x + 1 , y + 1)

    //bottom
    if (y < this.lastIndex)
        this.incrementCell(octopusesToFlash, flashedOctopuses, x, y + 1)

    //bottom left
    if (x > 0 && y < this.lastIndex)
        this.incrementCell(octopusesToFlash, flashedOctopuses, x - 1, y + 1)
}

fun MutableOctopusMap.incrementCell(stack: MutableList<Octopus>, flashedOctopuses: HashSet<Octopus>, x: Int, y: Int) {
    this[y][x] ++
    val octopus = Pair(x, y)
    if (this[y][x] > 9 && !flashedOctopuses.contains(octopus)) {
        stack.add(octopus)
        flashedOctopuses.add(octopus)
    }
}
