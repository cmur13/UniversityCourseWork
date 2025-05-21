class Evaluator {
    private val ingredients = mutableSetOf<String>() // Using a set to avoid duplicates

    fun evaluate(nodes: List<Node>) {
        // Add explicitly defined ingredients
        for (node in nodes) {
            when (node) {
                is AddIngredientNode -> {
                    // Add the ingredient name without the quantity
                    ingredients.add(node.name)
                }
                // For 'season' and other commands, add implicit ingredients
                is SeasonNode -> {
                    // Only add the ingredient used for seasoning (e.g., "salt", "chicken")
                    ingredients.add(node.item)
                    ingredients.add(node.seasoning)
                }
                else -> {}
            }
        }

        // Print out the ingredient list
        println("Ingredients needed:")
        ingredients.forEach { println(it) }

        // Print out the steps for the recipe
        println("\nSteps to make the recipe:")
        for (node in nodes) {
            when (node) {
                is AddIngredientNode -> println("Add ${node.amount} ${if (node.amount == 1) "cup" else "cups"} of ${node.name}.")
                is MixNode -> println("Mix ${node.item}.")
                is BakeNode -> println("Bake at ${node.temperature}° for ${node.time} minutes.")
                is ChopNode -> println("Chop ${node.item} into small pieces.")
                is BoilNode -> println("Boil ${node.item} for ${node.time} minutes.")
                is SeasonNode -> println("Season ${node.item} with ${node.seasoning}.")
                is SimmerNode -> println("Simmer ${node.item} for ${node.time} minutes on low heat.")
                is ConditionalNode -> {
                    println("If ${node.condition}, do:")
                    node.commands.forEach { evaluate(listOf(it)) }
                }
                is RepeatNode -> {
                    repeat(node.times) {
                        node.commands.forEach { evaluate(listOf(it)) }
                    }
                }
                is ServeNode -> println("Serve the dish!")
            }
        }
    }
}







