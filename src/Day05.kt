import kotlin.math.max
import kotlin.math.min

fun main() {

    data class Database(
        val freshIdRanges: List<LongRange>,
        val availableIds: List<Long>
    )

    fun List<String>.parseInput() = Database(
        freshIdRanges = takeWhile { !it.isBlank() }.map {
            val (start, end) = it.split("-")
            start.toLong()..end.toLong()
        },
        availableIds = takeLastWhile { !it.isBlank() }.map { it.toLong() }
    )

    infix fun LongRange.intersects(other: LongRange) = first <= other.last && last >= other.first
    infix fun LongRange.merge(other: LongRange) = min(first, other.first) ..max(last, other.last)

    fun part1(input: Database): Long {
        return input.availableIds.count { id ->
            input.freshIdRanges.any { id in it }
        }.toLong()
    }

    fun part2(input: Database): Long {
        val sorted = input.freshIdRanges.sortedBy { it.first }

        val first = sorted.first()
        val rest = sorted.drop(1)

        val mergedRanges = rest.fold(listOf(first)) { acc, range ->
            val last = acc.last()
            if (last intersects range) {
                val newRange = last merge range
                acc.dropLast(1) + listOf(newRange)
            } else {
                acc + listOf(range)
            }
        }

        return mergedRanges.sumOf { it.last - it.first + 1 }
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = """
        3-5
        10-14
        16-20
        12-18

        1
        5
        8
        11
        17
        32
    """.trimIndent().lines().parseInput()
    check(part1(testInput) == 3L)
    check(part2(testInput) == 14L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05").parseInput()
    part1(input).println()
    part2(input).println()
}
