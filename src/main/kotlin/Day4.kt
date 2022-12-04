class Day4(private val puzzle: List<String>) {
    fun getNumOfRedundantAssignments(): Int {
        return parseToAssignmentPairs()
            .filter {
                when(it.first.union(it.second)) {
                    it.first, it.second -> true
                    else -> false
                }
            }
            .size
    }

    fun getNumOfOverlappingAssignments(): Int {
        return parseToAssignmentPairs()
            .filter {
                it.first.intersect(it.second).isNotEmpty()
            }
            .size
    }

    private fun parseToAssignmentPairs() = puzzle
        .map { lineOfAssignments ->
                val assignments = lineOfAssignments
                    .split(",")
                    .map { assignment ->
                        val range = assignment.split("-")
                        range.first().toInt()..range.last().toInt()
                    }
                Pair(assignments.first().toSet(), assignments.last().toSet())
            }
}