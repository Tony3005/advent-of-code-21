package day4

data class Line(val cells: MutableList<Int>)

class Board(stringBoard: List<String>) {
    var lines: List<Line> = listOf()

    init {
        val rows = stringBoard
            .map { line ->
                Line(
                    line.split(" ").filterNot{
                        "" == it
                    }.map { it.toInt() }.toMutableList()
                )
            }

        val columns = mutableListOf<Line>()

        rows.forEach { row ->
            row.cells.forEachIndexed {index, cellValue ->
                if (index >= columns.size)
                    columns.add(Line(mutableListOf()))

                columns[index].cells.add(cellValue)
            }
        }

        lines = rows + columns
    }

    fun checkNumber(number: Int) {
        lines = lines.map {
            Line(it.cells.filterNot { value -> value == number }.toMutableList())
        }
    }

    fun isBingo(): Boolean {
        lines.forEach { line ->
            if (line.cells.isEmpty())
                return true
        }

        return false
    }
}
