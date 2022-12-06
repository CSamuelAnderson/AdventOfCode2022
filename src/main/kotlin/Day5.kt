
data class CrateMove(
    val numOfCrates: Int,
    val fromColumn: Int,
    val toColumn: Int
)

class Day5(private val puzzle: List<String>) {
    fun performCrateMovesAndGetTopCrates(): String {
        val initialCratePlacement = puzzle.parseInitialCratePlacement()
        return initialCratePlacement.toString()
//        val crateMoves: CrateMove = puzzle.parseCrateMovements()
//        return crateMoves.fold(initialCratePlacement) { currentCratePlacement, crateMove ->
//            currentCratePlacement.moveCrate(crateMove)
//        }.fold("") { acc, stack ->
//            acc + stack.first()
//
//        }
    }
}

private fun List<String>.parseInitialCratePlacement(): List<List<Char>> {
    //We know there is an empty line between the drawing and the commands, so use that to divide them.
    val rowsOfBoxes  = this.takeWhile { it.isNotBlank() }
        //Now reverse them, because it will be easier to load crates up when they are on the bottom. Also, we won't need the column number, so remove it
        .reversed().take(1)
    //The puzzle is monospaced, so everything that belongs to the same stack also has the same index in their respective row
    //Which means saving off the index of each is enough to give us the column
    val boxCoords = rowsOfBoxes.flatMap { row ->
        row.mapIndexed() { index, box ->
            when(box.isLetter()) {
                true -> Pair(index,box)
                false -> null
            }
        }.filterNotNull()
    }

    return  boxCoords.consolidatePairs().sortedBy { it.first }.map { it.second }
}

private fun <A,B> List<Pair<A,B>>.consolidatePairs(): List<Pair<A, List<B>>>  {
   val map = mutableMapOf<A,List<B>>()
    forEach {
        map.merge(it.first, listOf(it.second)) { old, new ->
            old + new
        }
    }
    return map.toList()
}
