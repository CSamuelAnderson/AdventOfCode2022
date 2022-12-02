@JvmInline
value class Calorie(val value: Int)

class Day1 {
    fun getElfWithMostCalories(puzzleInput: List<String>): Int {
        val elvesAndTheirCalories: List<Calorie> = sumEachElfsCals(puzzleInput)
        return elvesAndTheirCalories.maxOf { it.value }
    }

    fun getTopThreeElvesTotalCals(puzzleInput: List<String>): Int {
        val elvesAndTheirCalories: List<Calorie> = sumEachElfsCals(puzzleInput)
        return elvesAndTheirCalories.sortedByDescending { it.value }.take(3).fold(0){acc, calorie ->
            acc + calorie.value}
    }
}

private fun sumEachElfsCals(puzzleInput: List<String>): List<Calorie> {
    return puzzleInput.fold(listOf(Calorie(0))) { acc, s ->
        when (s.isBlank()) {
            true -> acc + Calorie(0)
            false -> acc.dropLast(1) + Calorie(acc.last().value + s.toInt())
        }
    }
}


