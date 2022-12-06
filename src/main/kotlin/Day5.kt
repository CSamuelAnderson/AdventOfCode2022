data class CrateMove(
    val numOfCrates: Int,
    val fromColumn: Int,
    val toColumn: Int
)

class Day5(private val puzzle: List<String>) {
    fun moveWithCrateMover9000(): String {
        return moveAndGetTopCrates { initialPosition ->
            { crateMove ->
                initialPosition.mapIndexed() { index, stack ->
                    when (index + 1) {
                        crateMove.fromColumn -> stack.dropLast(crateMove.numOfCrates)
                        crateMove.toColumn -> stack + initialPosition[crateMove.fromColumn - 1].takeLast(crateMove.numOfCrates)
                            .reversed()

                        else -> stack
                    }
                }
            }
        }
    }

    fun moveWithCrateMover9001(): String {
        return moveAndGetTopCrates { initialPosition ->
            { crateMove ->
                initialPosition.mapIndexed() { index, stack ->
                    when (index + 1) {
                        crateMove.fromColumn -> stack.dropLast(crateMove.numOfCrates)
                        crateMove.toColumn -> stack + initialPosition[crateMove.fromColumn - 1].takeLast(crateMove.numOfCrates)
                        else -> stack
                    }
                }
            }
        }
    }

    private fun moveAndGetTopCrates(moveCrates: (List<List<Char>>) -> (CrateMove) -> List<List<Char>>): String {
        val initialCratePlacement = puzzle.parseInitialCratePlacement()
        val crateMoves: List<CrateMove> = puzzle.parseCrateMovements()
        return crateMoves.fold(initialCratePlacement) { currentCratePlacement, crateMove ->
            moveCrates(currentCratePlacement)(crateMove)
        }.fold("") { acc, stack ->
            acc + stack.last()
        }
    }
}

private fun List<String>.parseCrateMovements(): List<CrateMove> {
    //We know there is an empty line between the drawing and the commands, so use that to divide them
    val rowsOfCommands = this.dropWhile { it.isNotBlank() }.drop(1)
    return rowsOfCommands.map { row ->
        row.split(" ").filter { it.toIntOrNull() != null }
    }.map { CrateMove(it[0].toInt(), it[1].toInt(), it[2].toInt()) }
}

private fun List<String>.parseInitialCratePlacement(): List<List<Char>> {
    //We know there is an empty line between the drawing and the commands, so use that to divide them.
    val rowsOfBoxes = this.takeWhile { it.isNotBlank() }
        //reverse so the bottom boxes are added on first
        .reversed().drop(1)
    //The puzzle is monospaced, so everything that belongs to the same stack also has the same index in their respective row
    //Which means saving off the index is enough to add it later
    return rowsOfBoxes.flatMap { row ->
        row.mapIndexed() { index, box ->
            when (box.isLetter()) {
                true -> Pair(index, box)
                false -> null
            }
        }.filterNotNull()
    }
        .consolidatePairs().sortedBy { it.first }.map { it.second }
}

private fun <A, B> List<Pair<A, B>>.consolidatePairs(): List<Pair<A, List<B>>> {
    val map = mutableMapOf<A, List<B>>()
    forEach {
        map.merge(it.first, listOf(it.second)) { old, new ->
            old + new
        }
    }
    return map.toList()
}
