fun main() {
    val code = """
    addIngredient tomatoes 3
    addIngredient onion 2
    chop onion
    chop tomato
    boil water for 5
    season chicken with salt
    simmer soup for 20
    serve
""".trimIndent()

    val lexer = Lexer(code)
    val tokens = lexer.lex()

    val parser = Parser(tokens)
    val nodes = parser.parse()

    val evaluator = Evaluator()
    evaluator.evaluate(nodes)
}
