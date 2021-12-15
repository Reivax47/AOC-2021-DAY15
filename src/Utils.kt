import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.math.hypot

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

class vertex(
    var x: Int,
    var y: Int,
    var entree: Boolean,
    var sortie: Boolean,
    var poids: Int,
    val adjacent: MutableList<vertex>
)

/**
 *
 */
fun create_tableau(input: List<String>): MutableList<vertex> {
    val tableau = mutableListOf<vertex>()
    val max_x = input.first().length - 1
    val max_y = input.size - 1
    val decal_x = arrayOf(-1, 0, 0, 1)
    val decal_y = arrayOf(0, -1, 1, 0)

    for (y in 0..max_y) {
        for (x in 0..max_x) {
            var adj = mutableListOf<vertex>()
            tableau.add(
                vertex(
                    x = x,
                    y = y,
                    entree = (x == 0 && y == 0),
                    sortie = (x == max_x && y == max_y),
                    poids = input[y][x].toString().toInt(),
                    adjacent = adj
                )
            )
        }
    }

    tableau.forEach { it_vortex ->
        for (decal in 0..3) {

            if (it_vortex.x + decal_x[decal] in 0..max_x && it_vortex.y + decal_y[decal] in 0..max_y) {

                val cible =
                    tableau.first { it.x == it_vortex.x + decal_x[decal] && it.y == it_vortex.y + decal_y[decal] }
                it_vortex.adjacent.add(cible)
            }
        }
    }
    return tableau

}

fun create_tableau_fois5(input: List<String>): Array<IntArray> {

    val original_width = input.first().length
    val total_width = original_width  * 5
    val original_height = input.size
    val total_height = original_height * 5


    var tableau: Array<IntArray> = Array(total_height) { IntArray(total_width) }
    var y = 0
    var x = 0
    input.forEach { it_ligne ->
        x = 0
        val uneLigne = IntArray(total_width)
        it_ligne.forEach {
            val value = it.toString().toInt()
            uneLigne[x] = value

            for (i in 1..4) {

                val new_value = if (value + i <= 9) value +i else value + i - 9
                uneLigne[x + original_width * i] = new_value

            }
            x++
        }
        tableau[y++] = uneLigne

    }

    for (y in 0 until original_height) {

        for (x in 0 until total_width) {

            var value = tableau[y][x]

            for (i in 1 .. 4) {
                val new_value = if (value + i <= 9) value +i else value + i - 9
                tableau[y + original_height * i][x] = new_value
            }
        }
    }

    return tableau
}