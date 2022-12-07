class Day6(private val puzzle: List<String>) {

    fun detectEndOfPacket() = detectEnd(4)
    fun detectEndOfMessage() = detectEnd(14)

    private fun detectEnd(scanningLength: Int): Int {
        return puzzle.first().windowed(scanningLength).indexOfFirst(::allUniqueChars) + scanningLength
    }

    private fun allUniqueChars(s: String): Boolean {
        return s.toSet().size == s.length
    }
}