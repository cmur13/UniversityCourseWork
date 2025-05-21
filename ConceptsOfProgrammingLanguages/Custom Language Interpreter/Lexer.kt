data class Token(val type: TokenType, val value: String)

enum class TokenType {
    ADD_INGREDIENT, MIX, BAKE, SERVE, CHOP, BOIL, SEASON, SIMMER, PLATE, IF, REPEAT, END_CONDITION, END_REPEAT,
    WORD, NUMBER, AT, FOR, END
}

class Lexer(private val input: String) {
    private val tokens = mutableListOf<Token>()
    private var currentIndex = 0

    fun lex(): List<Token> {
        while (currentIndex < input.length) {
            when {
                input.startsWith("addIngredient", currentIndex) -> {
                    tokens.add(Token(TokenType.ADD_INGREDIENT, "addIngredient"))
                    currentIndex += "addIngredient".length
                }
                input.startsWith("if", currentIndex) -> {
                    tokens.add(Token(TokenType.IF, "if"))
                    currentIndex += "if".length
                }
                input.startsWith("repeat", currentIndex) -> {
                    tokens.add(Token(TokenType.REPEAT, "repeat"))
                    currentIndex += "repeat".length
                }
                input.startsWith("endIf", currentIndex) -> {
                    tokens.add(Token(TokenType.END_CONDITION, "endIf"))
                    currentIndex += "endIf".length
                }
                input.startsWith("endRepeat", currentIndex) -> {
                    tokens.add(Token(TokenType.END_REPEAT, "endRepeat"))
                    currentIndex += "endRepeat".length
                }
                input.startsWith("mix", currentIndex) -> {
                    tokens.add(Token(TokenType.MIX, "mix"))
                    currentIndex += "mix".length
                }
                input.startsWith("bake", currentIndex) -> {
                    tokens.add(Token(TokenType.BAKE, "bake"))
                    currentIndex += "bake".length
                }
                input.startsWith("serve", currentIndex) -> {
                    tokens.add(Token(TokenType.SERVE, "serve"))
                    currentIndex += "serve".length
                }
                input.startsWith("chop", currentIndex) -> {
                    tokens.add(Token(TokenType.CHOP, "chop"))
                    currentIndex += "chop".length
                }
                input.startsWith("boil", currentIndex) -> {
                    tokens.add(Token(TokenType.BOIL, "boil"))
                    currentIndex += "boil".length
                }
                input.startsWith("season", currentIndex) -> {
                    tokens.add(Token(TokenType.SEASON, "season"))
                    currentIndex += "season".length
                }
                input.startsWith("simmer", currentIndex) -> {
                    tokens.add(Token(TokenType.SIMMER, "simmer"))
                    currentIndex += "simmer".length
                }
                input.startsWith("plate", currentIndex) -> {
                    tokens.add(Token(TokenType.PLATE, "plate"))
                    currentIndex += "plate".length
                }
                input.startsWith("for", currentIndex) -> {
                    tokens.add(Token(TokenType.FOR, "for"))
                    currentIndex += "for".length
                }
                input[currentIndex].isDigit() -> {
                    val number = input.substring(currentIndex).takeWhile { it.isDigit() }
                    tokens.add(Token(TokenType.NUMBER, number))
                    currentIndex += number.length
                }
                input[currentIndex].isLetter() -> {
                    val word = input.substring(currentIndex).takeWhile { it.isLetter() }
                    tokens.add(Token(TokenType.WORD, word))
                    currentIndex += word.length
                }
                input[currentIndex].isWhitespace() -> currentIndex++
                else -> throw IllegalArgumentException("Unexpected character: ${input[currentIndex]}")
            }

        }
        tokens.add(Token(TokenType.END, ""))
        return tokens
    }
}




