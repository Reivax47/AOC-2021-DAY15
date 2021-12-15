fun main() {
    fun part1(input: List<String>): Int? {

        val tableau = create_tableau(input)
        val entree = tableau.first { it.entree }
        val sortie = tableau.first { it.sortie }

        val shorted_distance = mutableMapOf<vertex, Int>()

        tableau.forEach {
            shorted_distance[it] = 1000000000

        }
        shorted_distance[entree] = 0

        val unVisited = tableau.filter { true }.toMutableList()

        while (unVisited.isNotEmpty()) {
            println(unVisited.size)
            var actual: vertex
            var actual_distance: Int
            val trie = shorted_distance.filter { it.value !=  1000000000}.toList().sortedBy { (_, value) -> value }

            var index = 0
            while (!unVisited.contains(trie[index].first)) {
                index++
            }
            actual = trie[index].first
            actual_distance = trie[index].second

            actual.adjacent.forEach {
                val prededente = shorted_distance[it]
                if (prededente != null && prededente > actual_distance + it.poids) {
                        shorted_distance[it] = actual_distance + it.poids

                }
            }
            unVisited.remove(actual)
            if (actual != sortie) {
                shorted_distance.remove(actual)
            }
        }
        return shorted_distance[sortie]
    }

    fun part2(input: List<String>): Int {
        val decal_x = arrayOf(-1, 0, 0, 1)
        val decal_y = arrayOf(0, -1, 1, 0)
        val original_width = input.first().length
        val total_width = original_width * 5
        val original_height = input.size
        val total_height = original_height * 5
        val tableau = create_tableau_fois5(input)

        val visited: Array<BooleanArray> = Array(total_height) { BooleanArray(total_width) }
        val distance: Array<IntArray> = Array(total_height) { IntArray(total_width) { Int.MAX_VALUE } }

        distance[0][0] = 0

        var x_actuel = 0
        var y_actuel = 0
        var distance_actuel = 0

        var restant = visited.filter { it.filter { !it }.isNotEmpty() }
        while (restant.isNotEmpty()) {


            var min = Int.MAX_VALUE
            for (pos_y in 0 until total_height) {
                for (pos_x in 0 until  total_width) {
                    if (distance[pos_y][pos_x] < min && !visited[pos_y][pos_x]) {
                        min = distance[pos_y][pos_x]
                        x_actuel = pos_x
                        y_actuel = pos_y
                        distance_actuel = distance[y_actuel][x_actuel]
                    }
                }
            }

            for (decal in 0 .. 3 ){

                if (x_actuel + decal_x[decal] in 0 until total_width
                    && y_actuel + decal_y[decal] in 0 until total_height
                    && distance_actuel + tableau[y_actuel + decal_y[decal]][x_actuel + decal_x[decal]] < distance[y_actuel + decal_y[decal]][x_actuel + decal_x[decal]]) {
                    distance[y_actuel + decal_y[decal]][x_actuel + decal_x[decal]] = distance_actuel + tableau[y_actuel + decal_y[decal]][x_actuel + decal_x[decal]]
                }
            }

            visited[y_actuel][x_actuel] = true
            restant = visited.filter { it.filter { !it }.isNotEmpty() }

        }

        return distance[total_height - 1][total_width - 1]
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    val input = readInput("Day15")
    check(part1(testInput) == 40)
    println(part1(input))

    check(part2(testInput) == 315)
    println(part2(input))


}


