fun main() {
//    val day1Puzzle = Day1()
//    println(day1Puzzle.getElfWithMostCalories(PuzzleInput.get("inputDay1")))
//    println(day1Puzzle.getTopThreeElvesTotalCals(PuzzleInput.get("inputDay1")))

//    val day2Puzzle = Day2()
//    println(day2Puzzle.parseForHandAndGetScore(PuzzleInput.get("inputDay2")))
//    println(day2Puzzle.parseForRoundOutcomeAndGetScore(PuzzleInput.get("inputDay2")))

//    val day3Puzzle = Day3(PuzzleInput.get("inputDay3"))
//    println(day3Puzzle.sumAllNonUniqueItemScores())
//    println(day3Puzzle.sumAllGroupBadges())

//    val day4Puzzle = Day4(PuzzleInput.get("inputDay4"))
//    println(day4Puzzle.getNumOfRedundantAssignments())
//    println(day4Puzzle.getNumOfOverlappingAssignments())

    val day5Puzzle = Day5(("    [D]    \n" +
            "[N] [C]    \n" +
            "[Z] [M] [P]\n" +
            " 1   2   3 \n" +
            "\n" +
            "move 1 from 2 to 1\n" +
            "move 3 from 1 to 3\n" +
            "move 2 from 2 to 1\n" +
            "move 1 from 1 to 2").split("\n"))
    println(day5Puzzle.performCrateMovesAndGetTopCrates())
}

class PuzzleInput {
    companion object {
        fun get(filename: String): List<String> {
            return this::class.java.getResourceAsStream(filename)?.bufferedReader()?.readLines() ?: listOf("-1")
        }
    }
}