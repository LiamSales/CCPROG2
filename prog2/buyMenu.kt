data class BuyItem(val buyer: Int, val item: Int, val quantity: Int)

fun viewAllProducts(sortedList: List<Item>) {
    val grouped = sortedList.filter { it.quantity > 0 }.groupBy { it.sellID }
    grouped.forEach { (sellerID, itemsList) ->
        println("Seller ID: $sellerID\tSeller Name: ${users[sellerID]?.name}")
        println("ProdID\tName\tCategory\tPrice\tQty")
        itemsList.sortedBy { it.prodID }.forEach { item ->
            println("${item.prodID}\t${item.name}\t${item.category}\t${item.price}\t${item.quantity}")
        }
        println()
        println("Press N for next seller, X to exit view, or Enter to continue:")
        val cmd = readlnOrNull()
        if (cmd?.lowercase() == "x") return
    }
}

fun showProductsofSpecific() {
    println("Please provide the seller ID:")
    val id = readlnOrNull()?.toIntOrNull()
    if (id == null || users[id] == null) {
        println("Invalid seller ID.")
        return
    }
    println("Products for seller $id (${users[id]?.name}):")
    println("ProdID\tName\tCategory\tPrice\tQty")
    val list = users[id]?.itemIDs?.mapNotNull { items[it] }?.sortedBy { it.prodID } ?: emptyList()
    list.forEach { item ->
        if (item.quantity > 0)
            println("${item.prodID}\t${item.name}\t${item.category}\t${item.price}\t${item.quantity}")
    }
}

fun searchByCategory() {
    println("Please provide the category:")
    val search = readlnOrNull()?.lowercase() ?: ""
    val matches = items.values.filter { it.category.lowercase().contains(search) && it.quantity > 0 }
    if (matches.isEmpty()) {
        println("No results.")
        return
    }
    var idx = 0
    while (idx < matches.size) {
        val v = matches[idx]
        println("ProdID: ${v.prodID}")
        println("Name: ${v.name}")
        println("Category: ${v.category}")
        println("Description: ${v.description}")
        println("Price: ${v.price} | Quantity: ${v.quantity}")
        println("Press N for next, X to exit:")
        val cmd = readlnOrNull()
        if (cmd?.lowercase() == "x") return
        idx++
    }
}

fun searchByName() {
    println("Please provide the name keyword:")
    val search = readlnOrNull()?.lowercase() ?: ""
    val matches = items.values.filter { it.name.lowercase().contains(search) && it.quantity > 0 }
    if (matches.isEmpty()) {
        println("No results.")
        return
    }
    var idx = 0
    while (idx < matches.size) {
        val v = matches[idx]
        println("ProdID: ${v.prodID}")
        println("Name: ${v.name}")
        println("Category: ${v.category}")
        println("Description: ${v.description}")
        println("Price: ${v.price} | Quantity: ${v.quantity}")
        println("Press N for next, X to exit:")
        val cmd = readlnOrNull()
        if (cmd?.lowercase() == "x") return
        idx++
    }
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
            println("Cannot add item you are selling. Press Enter to continue")
            readlnOrNull()
            return
        }
        println("Provide the quantity:")
        val quantity = readlnOrNull()?.toIntOrNull() ?: 0
        if (quantity < 1 || quantity > (items[id]?.quantity ?: 0)) {
            println("Invalid quantity. Press Enter to continue")
            readlnOrNull()
            return
        }
        val entry = BuyItem(user, id, quantity)
        cart.add(entry)
        println("Item added to cart.")
    } else {
        println("Cannot add more items to cart. Maximum capacity reached. Press Enter to continue")
        readlnOrNull()
    }
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

    // Group by seller manually
    val bySeller = cart.groupBy { items[it.item]?.sellID ?: -1 }
    for ((sellerID, list) in bySeller) {
        if (sellerID == -1) continue
        val tItems = mutableListOf<TransactionItem>()
        for (entry in list) {
            val item = items[entry.item] ?: continue
            val usedQty = minOf(entry.quantity, item.quantity)
            if (usedQty <= 0) continue
            tItems.add(TransactionItem(item.prodID, usedQty, item.price))
        }
        if (tItems.isEmpty()) {
            println("No available items for seller $sellerID. Skipping.")
            continue
        }
        val transaction = Transaction(list.first().buyer, sellerID, date, tItems)
        println("\n--- RECEIPT ---")
        println("Seller: $sellerID - ${users[sellerID]?.name ?: "Unknown"}")
        println("Buyer: ${transaction.buyer} - ${users[transaction.buyer]?.name ?: "Unknown"}")
        println("Date: ${transaction.date}")
        println("Qty\tProdID\tName\tUnitPrice\tTotal")
        transaction.items.forEach { it2 ->
            val item = items[it2.prodID]
            val lineTotal = it2.quantity * it2.unitPrice
            println("${it2.quantity}\t${it2.prodID}\t${item?.name ?: "Unknown"}\t${it2.unitPrice}\t$lineTotal")
            item?.quantity = (item?.quantity ?: 0) - it2.quantity
            if (item != null && item.quantity <= 0) {
                users[item.sellID]?.itemIDs?.remove(item.prodID)
            }
        }
        println("Total due: ${transaction.totalAmount()}")
        transactions.add(transaction)
    }
    cart.clear()
    saveItems()
    saveTransactions()
    return "Items checked out successfully!"
}

fun buyMenu(user: Int) {
    val cart = mutableListOf<BuyItem>()
    // refresh sorted list dynamically inside view
    println("Press V to view all products.\nPress S to show only the products of a specific seller.\nPress C to search for products by category.\nPress N to search for products by name.\n")
    println("Press A to add a product to your cart.\nPress E to edit your cart.\nPress O to check-out.\n")
    println("Press X to exit buy menu")
    while (true){
        when (readlnOrNull()?.lowercase()) {
            "v" -> viewAllProducts(items.values.toList().sortedBy { it.sellID })
            "s" -> showProductsofSpecific()
            "c" -> searchByCategory()
            "n" -> searchByName()
            "a" -> addToCart(cart, user)
            "e" -> editCart(cart)
            "o" -> println(checkOutMenu(cart))
            "x" -> return
            else -> println("\tInvalid input. Please try again:\n")
        }
    }
}