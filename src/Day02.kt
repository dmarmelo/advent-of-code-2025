fun main() {

    fun List<String>.parseInput() = flatMap { line ->
        line.split(",").map { range ->
            val (first, last) = range.split("-")
            first.toLong()..last.toLong()
        }
    }

    fun part1(input: List<LongRange>): Long {
        return input.flatMap { range ->
            range.filter { it.toString().length % 2 == 0 }
                .filter {
                    val string = it.toString()
                    val first = string.take(string.length / 2)
                    val second = string.substring(string.length / 2)
                    first == second
                }
        }.sum()
    }

    fun part2(input: List<LongRange>): Long {
        return input.flatMap { range ->
            range.filter { id ->
                val string = id.toString()
                repeat(string.length / 2) { i ->
                    val size = string.chunked(i + 1)
                        .groupingBy { it }
                        .eachCount()
                        .size
                    if (size == 1) {
                        return@filter true
                    }
                }
                false
            }
        }.sum()
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = """
        11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124
    """.trimIndent().lines().parseInput()
    check(part1(testInput) == 1227775554L)
    check(part2(testInput) == 4174379265L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02").parseInput()
    part1(input).println()
    part2(input).println()
}
