fun main() {

    fun List<String>.parseInput() = map { line ->
        line.map { char -> char.digitToInt() }
    }

    fun solveBank(input: List<Int>, poweredBatteries: Int): List<Long> {
        val indexedBatteries = input.withIndex()

        val (maxIndex, value) = indexedBatteries
            .take(input.size - poweredBatteries + 1)
            .maxBy { it.value }

        val result = listOf(value.toLong())
        return if (poweredBatteries == 1) result
        else (result + solveBank(input.drop(maxIndex + 1), poweredBatteries - 1))
    }

    fun solve(input: List<List<Int>>, poweredBatteries: Int): Long {
        return input.sumOf { bank ->
            solveBank(bank, poweredBatteries).reduce { a, b -> a * 10 + b }
        }
    }

    fun part1(input: List<List<Int>>): Long {
        return solve(input, 2)
    }

    fun part2(input: List<List<Int>>): Long {
        return solve(input, 12)
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = """
        987654321111111
        811111111111119
        234234234234278
        818181911112111
    """.trimIndent().lines().parseInput()
    check(part1(testInput) == 357L)
    check(part2(testInput) == 3121910778619L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03").parseInput()
    part1(input).println()
    part2(input).println()
}
