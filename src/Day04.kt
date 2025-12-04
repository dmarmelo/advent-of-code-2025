fun main() {

    data class Point(val x: Int, val y: Int)

    fun List<String>.parseInput() = map { line ->
        line.map { it }
    }

    fun getAdjacents(input: List<List<Char>>, point: Point) = sequence {
        sequence {
            yield(Point(point.x, point.y - 1))
            yield(Point(point.x + 1, point.y - 1))
            yield(Point(point.x + 1, point.y))
            yield(Point(point.x + 1, point.y + 1))
            yield(Point(point.x, point.y + 1))
            yield(Point(point.x - 1, point.y + 1))
            yield(Point(point.x - 1, point.y))
            yield(Point(point.x - 1, point.y - 1))
        }.filter { it.x in input[0].indices && it.y in input.indices }
            .map { input[it.y][it.x] }
            .forEach { yield(it) }
    }

    fun part1(input: List<List<Char>>): Long {
        var rollsCount = 0L
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                if (c == '@') {
                    val count = getAdjacents(input, Point(x, y)).count { it == '@' }
                    if (count < 4) {
                        rollsCount++
                    }
                }
            }
        }

        return rollsCount
    }

    fun part2(input: List<List<Char>>): Long {
        val mutableMap = input.map { line -> line.toMutableList() }
        var rollsCount = 0L

        while (true) {
            var rollsRemovedThisPass = 0
            mutableMap.forEachIndexed { y, line ->
                line.forEachIndexed { x, c ->
                    if (c == '@') {
                        val count = getAdjacents(mutableMap, Point(x, y)).count { it == '@' }
                        if (count < 4) {
                            line[x] = 'x'
                            rollsRemovedThisPass++
                        }
                    }
                }
            }

            rollsCount += rollsRemovedThisPass
            if (rollsRemovedThisPass == 0) break
        }

        return rollsCount
    }

    // Test if implementation meets criteria from the description, like:
    //check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = """
        ..@@.@@@@.
        @@@.@.@.@@
        @@@@@.@.@@
        @.@@@@..@.
        @@.@@@@.@@
        .@@@@@@@.@
        .@.@.@.@@@
        @.@@@.@@@@
        .@@@@@@@@.
        @.@.@@@.@.
    """.trimIndent().lines().parseInput()
    check(part1(testInput) == 13L)
    check(part2(testInput) == 43L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04").parseInput()
    part1(input).println()
    part2(input).println()
}
