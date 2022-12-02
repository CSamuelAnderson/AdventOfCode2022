sealed class Roshambo(val point: Int) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            null -> false
            else -> other::class == this::class
        }
    }

    override fun hashCode(): Int {
        return point
    }
}

object Rock : Roshambo(1)
object Paper : Roshambo(2)
object Scissors : Roshambo(3)

sealed class Hand(val roshambo: Roshambo) {
    class Opponent(value: Roshambo) : Hand(value)
    class Mine(value: Roshambo) : Hand(value)
}

sealed class Outcome(val point: Int)
object Loss : Outcome(0)
object Draw : Outcome(3)
object Win : Outcome(6)

fun Roshambo.beats(): Roshambo {
    return when (this) {
        is Rock -> Scissors
        is Paper -> Rock
        is Scissors -> Paper
    }
}

enum class FirstCode {
    A,
    B,
    C,
}

enum class SecondCode {
    X,
    Y,
    Z
}

fun scoreMyRound(myHand: Hand.Mine): (Hand.Opponent) -> Outcome {
    return { opponentHand ->
        when {
            myHand.roshambo.beats() == opponentHand.roshambo -> Win
            opponentHand.roshambo.beats() == myHand.roshambo -> Loss
            else -> Draw
        }
    }
}

fun getRoshamboForScore(outcome: Outcome): (Hand.Opponent) -> Roshambo {
    return { opponentHand ->
        when(outcome) {
            is Win -> opponentHand.roshambo.beats().beats()
            is Draw -> opponentHand.roshambo
            is Loss -> opponentHand.roshambo.beats()
        }
    }
}


class Day2 {
    fun parseForHandAndGetScore(puzzleInput: List<String>): Int {
        return parsePuzzleForMyHand(puzzleInput).fold(0) { acc, round ->
            println(
                "My hand: ${round.second.roshambo}, Their hand: ${round.first.roshambo}, my point: ${round.second.roshambo.point} + my round: ${
                    scoreMyRound(
                        round.second
                    )(round.first).point
                }"
            )
            acc + round.second.roshambo.point + scoreMyRound(round.second)(round.first).point
        }
    }

    fun parseForRoundOutcomeAndGetScore(puzzleInput: List<String>): Int {
        return parsePuzzleForScore(puzzleInput).fold(0) { acc, round ->
            acc + round.second.point + getRoshamboForScore(round.second)(round.first).point
        }
    }

    private fun <A, B> parsePuzzle(puzzleInput: List<String>): ((FirstCode) -> A) -> ((SecondCode) -> B) -> List<Pair<A, B>> {
        return { decodeFirstItem ->
            { decodeSecondItem ->
                puzzleInput.map {
                    val split = it.split(" ")
                    val first = decodeFirstItem(FirstCode.valueOf(split.first()))
                    val second = decodeSecondItem(SecondCode.valueOf(split.last()))
                    Pair(first, second)
                }
            }
        }
    }

    private fun <B> decodeOnlyOpponentsHand(puzzleInput: List<String>): ((SecondCode) -> B) -> List<Pair<Hand.Opponent, B>> {
        return parsePuzzle<Hand.Opponent, B>(puzzleInput)() {
            when (it) {
                FirstCode.A -> Hand.Opponent(Rock)
                FirstCode.B -> Hand.Opponent(Paper)
                FirstCode.C -> Hand.Opponent(Scissors)
            }
        }
    }

    private fun parsePuzzleForMyHand(puzzleInput: List<String>): List<Pair<Hand.Opponent, Hand.Mine>> {
        return decodeOnlyOpponentsHand<Hand.Mine>(puzzleInput)() {
            when (it) {
                SecondCode.X -> Hand.Mine(Rock)
                SecondCode.Y -> Hand.Mine(Paper)
                SecondCode.Z -> Hand.Mine(Scissors)
            }
        }
    }

    private fun parsePuzzleForScore(puzzleInput: List<String>): List<Pair<Hand.Opponent, Outcome>> {
        return decodeOnlyOpponentsHand<Outcome>(puzzleInput)() {
            when (it) {
                SecondCode.X -> Loss
                SecondCode.Y -> Draw
                SecondCode.Z -> Win
            }
        }
    }
}

