fun main() {
//    val day1Puzzle = Day1()
//    println(day1Puzzle.getElfWithMostCalories(PuzzleInput.get("inputDay1")))
//    println(day1Puzzle.getTopThreeElvesTotalCals(PuzzleInput.get("inputDay1")))

//    val day2Puzzle = Day2()
//    println(day2Puzzle.parseForHandAndGetScore(PuzzleInput.get("inputDay2")))
//    println(day2Puzzle.parseForRoundOutcomeAndGetScore(PuzzleInput.get("inputDay2")))

    val day3Puzzle = Day3(PuzzleInput.get("inputDay3"))
    println(day3Puzzle.sumAllNonUniqueItemScores())
    println(day3Puzzle.sumAllGroupBadges())
}

class PuzzleInput {
    companion object {
        fun get(filename: String): List<String> {
            return this::class.java.getResourceAsStream(filename)?.bufferedReader()?.readLines() ?: listOf("-1")
        }
    }
}