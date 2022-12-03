sealed class Roshambo(val point: Int)
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
            is Loss -> opponentHand.roshambo.beats()
            is Draw -> opponentHand.roshambo
        }
    }
}


class Day2 {
    fun parseForHandAndGetScore(puzzleInput: List<String>): Int {
        return decodePuzzleForMyHand(puzzleInput).fold(0) { acc, round ->
            acc + round.second.roshambo.point + scoreMyRound(round.second)(round.first).point
        }
    }

    fun parseForRoundOutcomeAndGetScore(puzzleInput: List<String>): Int {
        return decodePuzzleForScore(puzzleInput).fold(0) { acc, round ->
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

    private fun decodePuzzleForMyHand(puzzleInput: List<String>): List<Pair<Hand.Opponent, Hand.Mine>> {
        return decodeOnlyOpponentsHand<Hand.Mine>(puzzleInput)() {
            when (it) {
                SecondCode.X -> Hand.Mine(Rock)
                SecondCode.Y -> Hand.Mine(Paper)
                SecondCode.Z -> Hand.Mine(Scissors)
            }
        }
    }

    private fun decodePuzzleForScore(puzzleInput: List<String>): List<Pair<Hand.Opponent, Outcome>> {
        return decodeOnlyOpponentsHand<Outcome>(puzzleInput)() {
            when (it) {
                SecondCode.X -> Loss
                SecondCode.Y -> Draw
                SecondCode.Z -> Win
            }
        }
    }
}

