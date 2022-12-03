class Day3(private val puzzle: List<String>) {

    fun sumAllNonUniqueItemScores(): Int {
        return puzzle.map { getNonUniqueItemWithinKnapsack(it) }.score()
    }

    fun sumAllGroupBadges(): Int {
        return puzzle.groupKnapsacksIntoThrees().map { it.getSharedItemBetweenKnapsacks() }.score()
    }

    private fun List<String>.groupKnapsacksIntoThrees(): List<List<String>> {
        return this.foldIndexed(listOf()) { index, acc, knapsack ->
            when (index % 3 == 0) {
                true -> acc + listOf(listOf(knapsack))
                false -> acc.dropLast(1) + listOf((acc.last() + listOf(knapsack)))
            }
        }
    }

    private fun List<String>.getSharedItemBetweenKnapsacks(): Char {
        val firstKnapsack = this.first().toHashSet()
        return this.drop(1).fold(firstKnapsack) { sharedChars, knapsack ->
            knapsack.filter { sharedChars.contains(it) }.toHashSet()
        }.first()
    }

    private fun List<Char>.score(): Int {
        val scoreBoard = getScoreBoard()
        return this.fold(0) { acc, char -> acc + (scoreBoard[char] ?: 0)}
    }

    private fun getScoreBoard(): Map<Char, Int> {
        val charList = ('a'..'z').toList() + ('A'..'Z').toList()
        return charList.mapIndexed { index, char ->
            Pair(char, index + 1)
        }.toMap()
    }

    private fun getNonUniqueItemWithinKnapsack(knapsack: String): Char {
        val firstCompartment = knapsack.take(knapsack.length / 2).toHashSet()
        return knapsack.takeLast(knapsack.length / 2).find { firstCompartment.contains(it) }!!
    }
}