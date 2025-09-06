class BuyItem(val buyer: Int, val item: Int, val quantity: Int)

fun viewAllProducts(sortedList: List<Item>) {

    val grouped = sortedList.filter { it.quantity > 0 }.groupBy { it.sellID }
    grouped.forEach { (sellerID, items) ->
        println("Seller ID: $sellerID\tSeller Name: ${users[sellerID]?.name}")
        items.forEach { item ->
            println("${item.prodID}\t${item.name}\t${item.category}\t${item.price}\t${item.quantity}")
        }
        println()
    }
    return
}

fun showProductsofSpecific() {
    println("Please provide the seller ID:")
    val id = readlnOrNull()?.toIntOrNull()
    if (id == null || users[id] == null) {
        println("Invalid seller ID.")
        return
    }
    users[id]?.itemIDs?.forEach { itemID ->
        val item = items[itemID]
        if (item != null && item.quantity > 0) {
            println("${item.prodID}\t${item.name}\t${item.category}\t${item.price}\t${item.quantity}")
        }
    }
    return
}

fun searchByCategory() {
    println("Please provide the category:")
    val search = readln().lowercase()
    items.forEach { (_, value) ->
        if (value.category.lowercase().contains(search) && value.quantity > 0)
            println("${value.prodID}\t${value.name}\t${value.category}\t${value.price}\t${value.quantity}")
    }
    return
}

fun searchByName() {
    println("Please provide the name:")
    val search = readln().lowercase()
    items.forEach { (_, value) ->
        if (value.name.lowercase().contains(search) && value.quantity > 0)
            println("${value.prodID}\t${value.name}\t${value.category}\t${value.price}\t${value.quantity}")
    }
    return
}

fun addToCart(cart: MutableList<BuyItem>, user: Int) {
    if (cart.size < 10) {
        println("Provide the product ID:")
        val id = readlnOrNull()?.toIntOrNull()
        if (id == null || items[id] == null) {
            println("Invalid product ID.")
            return
        }
        if (items[id]?.sellID == user) {
            println("Cannot add item you are selling. Press any button to continue")
            readln()
            return
        }
        println("Provide the quantity:")
        val quantity = readlnOrNull()?.toIntOrNull() ?: 0
        if (quantity < 1 || quantity > (items[id]?.quantity ?: 0)) {
            println("Invalid quantity. Press any button to continue")
            readln()
            return
        }
        val entry = BuyItem(user, id, quantity)
        cart.add(entry)
        println("Item added to cart.")
    } else {
        println("Cannot add more items to cart. Maximum capacity reached. Press any button to continue")
        readln()
    }
    return
}

fun editCart(cart: MutableList<BuyItem>) {
    if (cart.isEmpty()) {
        println("Cart is empty.")
        return
    }
    println("Your cart:")
    cart.forEachIndexed { idx, item ->
        val prod = items[item.item]
        println("${idx + 1}. ${prod?.name ?: "Unknown"} (ID: ${item.item}) x${item.quantity}")
    }
    println("Enter the number of the item to edit, or X to exit:")
    val input = readlnOrNull()
    if (input?.lowercase() == "x") return
    val idx = input?.toIntOrNull()?.minus(1)
    if (idx == null || idx !in cart.indices) {
        println("Invalid selection.")
        return
    }
    println("Press R to remove, Q to change quantity, X to cancel:")
    when (readlnOrNull()?.lowercase()) {
        "r" -> {
            cart.removeAt(idx)
            println("Item removed.")
        }
        "q" -> {
            println("Enter new quantity:")
            val newQty = readlnOrNull()?.toIntOrNull() ?: 0
            val prod = items[cart[idx].item]
            if (newQty < 1 || newQty > (prod?.quantity ?: 0)) {
                println("Invalid quantity.")
            } else {
                cart[idx] = BuyItem(cart[idx].buyer, cart[idx].item, newQty)
                println("Quantity updated.")
            }
        }
        "x" -> return
        else -> println("Invalid input.")
    }
}

fun checkOutMenu(cart: MutableList<BuyItem>): String {
    if (cart.isEmpty()) return "Cart is empty."
    println("Enter date of purchase (YYYY-MM-DD):")
    val date = readlnOrNull() ?: "Unknown"
    cart.forEach { entry ->
        val item = items[entry.item]
        if (item != null && item.quantity >= entry.quantity) {
            item.quantity -= entry.quantity
            if (item.quantity == 0) {
                // Optionally remove from user's itemIDs
                users[item.sellID]?.itemIDs?.remove(item.prodID)
            }
        }
    }
    cart.clear()
    return "Items checked out successfully!"
}

fun buyMenu(user: Int) {
    
    val cart = mutableListOf<BuyItem>()
    val sortedList = items.values.toList().sortedBy { it.sellID }


    println("Press V to view all products.\nPress S to show only the products of a specific seller.\nPress C to search for products by category.\nPress N to search for products by name.\n")
    println("Press A to add a product to your cart.\nPress E to edit your cart.\nPress O to check-out.\n")
    println("Press X to exit without checking-out")

    while (true){
                when (readlnOrNull()?.lowercase()) {
                "v" -> viewAllProducts(sortedList)
                "s" -> showProductsofSpecific()
                "c" -> searchByCategory()
                "n" -> searchByName()
                "a" -> addToCart(cart, user)
                "e" -> editCart(cart)
                "o" -> checkOutMenu(cart)
                "x" -> return
                else -> println("\tInvalid input. Please try again:\n")
            }
        }
}
