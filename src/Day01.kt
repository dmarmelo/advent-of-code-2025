fun main() {
    val numberOfPositions = 100

    fun List<String>.parseInput() = map { line ->
        line.first() to line.substring(1).toInt()
    }

    fun part1(input: List<Pair<Char, Int>>): Int {
        return input.runningFold(50) { acc, (direction, steps) ->
            when (direction) {
                'R' -> (acc + steps) % numberOfPositions
                'L' -> (acc - steps + numberOfPositions) % numberOfPositions
                else -> error("Invalid Direction")
            }
        }.count { it == 0 }
    }

    fun part2(input: List<Pair<Char, Int>>): Int {
        var pos = 50
        var count = 0

        for ((direction, steps) in input) {
            val rotations = steps / numberOfPositions
            count += rotations

            var offset = steps % numberOfPositions
            if (offset != 0) {
                if (direction == 'L') {
                    offset = -offset
                }

                val newPos = pos + offset
                if (pos != 0 && (newPos !in 1..<numberOfPositions)) {
                    count++
                }
                pos = newPos.mod(numberOfPositions)
            }
        }

        return count
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = """
        L68
        L30
        R48
        L5
        R60
        L55
        L1
        L99
        R14
        L82
    """.trimIndent().lines().parseInput()
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01").parseInput()
    part1(input).println()
    part2(input).println()
}
