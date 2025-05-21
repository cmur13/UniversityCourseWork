sealed class Node
// Added optional constructs for conditional and repeat steps
data class AddIngredientNode(val name: String, val amount: Int) : Node()
data class MixNode(val item: String) : Node()
data class BakeNode(val temperature: Int, val time: Int) : Node()
data class ChopNode(val item: String) : Node()
data class BoilNode(val item: String, val time: Int) : Node()
data class SeasonNode(val item: String, val seasoning: String) : Node()
data class SimmerNode(val item: String, val time: Int) : Node()
data class ConditionalNode(val condition: String, val commands: List<Node>) : Node()
data class RepeatNode(val times: Int, val commands: List<Node>) : Node()
object ServeNode : Node()

class Parser(private val tokens: List<Token>) {
    private var current = 0

    fun parse(): List<Node> {
        val nodes = mutableListOf<Node>()
        while (current < tokens.size && tokens[current].type != TokenType.END) {
            nodes.add(parseCommand())
        }
        return nodes
    }

    private fun parseCommand(): Node {
        return when (tokens[current].type) {
            TokenType.ADD_INGREDIENT -> parseAddIngredient()
            TokenType.MIX -> parseMix()
            TokenType.BAKE -> parseBake()
            TokenType.CHOP -> parseChop()
            TokenType.BOIL -> parseBoil()
            TokenType.SEASON -> parseSeason()
            TokenType.SIMMER -> parseSimmer()
            TokenType.IF -> parseConditional()
            TokenType.REPEAT -> parseRepeat()
            TokenType.SERVE -> { current++; ServeNode }
            else -> throw IllegalArgumentException("Unexpected token: ${tokens[current]}")
        }
    }

    private fun parseAddIngredient(): Node {
        current++
        val name = tokens[current++].value
        val amount = tokens[current++].value.toInt()
        return AddIngredientNode(name, amount)
    }

    private fun parseMix(): Node {
        current++
        val item = tokens[current++].value
        return MixNode(item)
    }

    private fun parseBake(): Node {
        current++
        if (tokens[current].type != TokenType.AT) throw IllegalArgumentException("Expected 'at' in bake command")
        current++
        val temperature = tokens[current++].value.toInt()
        if (tokens[current].type != TokenType.FOR) throw IllegalArgumentException("Expected 'for' in bake command")
        current++
        val time = tokens[current++].value.toInt()
        return BakeNode(temperature, time)
    }

    private fun parseChop(): Node {
        current++
        val item = tokens[current++].value
        return ChopNode(item)
    }

    private fun parseBoil(): Node {
        current++ // Move past the 'boil' token
        val item = tokens[current++].value // Get the item to boil

        // Check for 'for' keyword
        if (tokens[current].type != TokenType.FOR) {
            throw IllegalArgumentException("Expected 'for' in boil command at token: ${tokens[current].value}")
        }
        current++ // Move past the 'for' token

        // Get the time to boil
        val time = tokens[current++].value.toInt()
        return BoilNode(item, time)
    }

    private fun parseSeason(): Node {
        current++ // Move past 'season'

        // Ensure the next token is the item to season
        if (tokens[current].type != TokenType.WORD) {
            throw IllegalArgumentException("Expected item to season after 'season', found: ${tokens[current]}")
        }
        val item = tokens[current++].value // Get the item to season

        // Ensure the next token is 'with'
        if (tokens[current].type != TokenType.WORD || tokens[current].value != "with") {
            throw IllegalArgumentException("Expected 'with' after item, found: ${tokens[current]}")
        }
        current++ // Move past 'with'

        // Ensure the next token is the seasoning
        if (tokens[current].type != TokenType.WORD) {
            throw IllegalArgumentException("Expected seasoning after 'with', found: ${tokens[current]}")
        }
        val seasoning = tokens[current++].value // Get the seasoning

        return SeasonNode(item, seasoning)
    }

    private fun parseSimmer(): Node {
        current++
        val item = tokens[current++].value
        if (tokens[current].type != TokenType.FOR) throw IllegalArgumentException("Expected 'for' in simmer command")
        current++
        val time = tokens[current++].value.toInt()
        return SimmerNode(item, time)
    }

    private fun parseConditional(): Node {
        current++
        val condition = tokens[current++].value
        val commands = mutableListOf<Node>()
        while (tokens[current].type != TokenType.END_CONDITION) {
            commands.add(parseCommand())
        }
        current++
        return ConditionalNode(condition, commands)
    }

    private fun parseRepeat(): Node {
        current++
        val times = tokens[current++].value.toInt()
        val commands = mutableListOf<Node>()
        while (tokens[current].type != TokenType.END_REPEAT) {
            commands.add(parseCommand())
        }
        current++
        return RepeatNode(times, commands)
    }
}




