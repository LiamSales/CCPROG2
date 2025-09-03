fun validateQuantity(input: Int?): Int {
    var current = input
    while (true) {
        if (current != null && current >= 0) { // must check for null first
            return current
        } else {
            println("Input cannot be negative, please try again:")
            current = readlnOrNull()?.toIntOrNull()
        }
    }
}

fun validatePrice(input: Float?): Float {
    var current = input
    while (true) {
        if (current != null && current >= 0) { // must check for null first
            return current
        } else {
            println("Input cannot be negative, please try again:")
            current = readlnOrNull()?.toFloatOrNull() // fixed wrong call
        }
    }
}

fun addNewItem(user: Int) {

    println("\n============================\n")

    if ((users[user]?.itemIDs?.size ?: 0) >= 20) { // >= instead of >
        println("Cannot add more than 20 items.")
    } else {

        while (true) { // added to allow R redo to work

            println("Please input the item ID:")
            val id = validateID(false, readlnOrNull()?.toIntOrNull())

            println("Please input the item name:")
            val name = validateString(readlnOrNull(), 20)

            println("Please input the item category:")
            val category = validateString(readlnOrNull(), 20)

            println("Please input the item description:")
            val description = validateString(readlnOrNull(), 20)

            println("Please input the item quantity:")
            val quantity = validateQuantity(readlnOrNull()?.toIntOrNull()) // fixed call

            println("Please input the item price:")
            val price = validatePrice(readlnOrNull()?.toFloatOrNull()) // fixed call

            println("Press S to save, X to cancel, and R to redo")

            when (readlnOrNull()?.lowercase()) {
                "x" -> return
                "r" -> continue
                "s" -> {
                    users[user]?.itemIDs?.add(id)
                    items[id] = Item(id, user, name, category, description, quantity, price)
                    println("Item added successfully!")
                    return
                }
                else -> println("Invalid input, please try again:")
            }
        }
    }
}

fun editStock(user: Int): String {
    // Placeholder: fill logic later
    // Example: Ask for product ID, then ask whether replenish/change price/etc.
    return "Stock editing feature not implemented yet"
}

fun showMyProducts() {
    // Placeholder: display all products owned by user
}

fun showLowStock(user: Int) {
    // Placeholder: loop user's itemIDs, check quantity < 5, print details
}

fun sellMenu(user: Int) {
    // Placeholder: Menu for Add Item, Edit Stock, Show Products, etc.
}
