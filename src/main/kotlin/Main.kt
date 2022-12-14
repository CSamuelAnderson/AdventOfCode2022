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

//    val day5Puzzle = Day5(PuzzleInput.get("inputDay5"))
//    println(day5Puzzle.moveWithCrateMover9000())
//    println(day5Puzzle.moveWithCrateMover9001())

    val day6Puzzle = Day6(PuzzleInput.get("inputDay6"))
    println(day6Puzzle.detectEndOfPacket())
    println(day6Puzzle.detectEndOfMessage())
}

class PuzzleInput {
    companion object {
        fun get(filename: String): List<String> {
            return this::class.java.getResourceAsStream(filename)?.bufferedReader()?.readLines() ?: listOf("-1")
        }
    }
}