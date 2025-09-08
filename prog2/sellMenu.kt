
fun validateQuantity(input: Int?): Int {
    var current = input
    while (true) {
        if (current != null && current >= 0) {
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
        if (current != null && current >= 0f) {
            return current
        } else {
            println("Input cannot be negative, please try again:")
            current = readlnOrNull()?.toFloatOrNull()
        }
    }
}

fun addNewItem(user: Int) {
    println("\n============================\n")
    if ((users[user]?.itemIDs?.size ?: 0) >= 20) {
        println("Cannot add more than 20 items.")
    } else {
        while (true) {
            println("Please input the item ID:")
            val id = validateID(false, readlnOrNull()?.toIntOrNull())
            println("Please input the item name:")
            val name = validateString(readlnOrNull(), 20)
            println("Please input the item category:")
            val category = validateString(readlnOrNull(), 20)
            println("Please input the item description:")
            val description = validateString(readlnOrNull(), 30)
            println("Please input the item quantity:")
            val quantity = validateQuantity(readlnOrNull()?.toIntOrNull())
            println("Please input the item price:")
            val price = validatePrice(readlnOrNull()?.toFloatOrNull())
            println("Press S to save, X to cancel, and R to redo")
            when (readlnOrNull()?.lowercase()) {
                "x" -> return
                "r" -> continue
                "s" -> {
                    users[user]?.itemIDs?.add(id)
                    items[id] = Item(id, user, name, category, description, quantity, price)
                    println("Item added successfully!")
                    saveItems()
                    return
                }
                else -> println("Invalid input, please try again:")
            }
        }
    }
}

fun editStock(user: Int) {
    // Show seller's own products
    val myProducts = users[user]?.itemIDs?.mapNotNull { items[it] }?.sortedBy { it.prodID } ?: emptyList()
    if (myProducts.isEmpty()) {
        println("You have no products.")
        return
    }
    println("Your products:")
    println("ProdID\tName\tCat\tPrice\tQty")
    myProducts.forEach { println("${it.prodID}\t${it.name}\t${it.category}\t${it.price}\t${it.quantity}") }
    println("Enter product ID to edit or X to cancel:")
    val input = readlnOrNull()
    if (input?.lowercase() == "x") return
    val pid = input?.toIntOrNull()
    val item = pid?.let { items[it] }
    if (item == null || item.sellID != user) {
        println("Invalid product ID.")
        return
    }
    while (true) {
        println("Editing ${item.name} (ID ${item.prodID}). Choose:")
        println("1. Replenish\n2. Change Price\n3. Change Item Name\n4. Change Category\n5. Change Description\n6. Finish Editing")
        when (readlnOrNull()?.trim()) {
            "1" -> {
                println("Enter quantity to add:")
                val add = readlnOrNull()?.toIntOrNull() ?: 0
                if (add > 0) {
                    item.replenish(add)
                    println("Replenished. New quantity: ${item.quantity}")
                    saveItems()
                } else println("Invalid amount.")
            }
            "2" -> {
                println("Enter new price:")
                val np = readlnOrNull()?.toFloatOrNull()
                if (np != null && np >= 0f) {
                    item.changePrice(np)
                    println("Price updated.")
                    saveItems()
                } else println("Invalid price.")
            }
            "3" -> {
                println("Enter new name (<=20 chars):")
                val nn = validateString(readlnOrNull(), 20)
                item.name = nn
                println("Name updated.")
                saveItems()
            }
            "4" -> {
                println("Enter new category (<=15 chars):")
                val nc = validateString(readlnOrNull(), 15)
                item.changeCategory(nc)
                println("Category updated.")
                saveItems()
            }
            "5" -> {
                println("Enter new description (<=30 chars):")
                val nd = validateString(readlnOrNull(), 30)
                item.changeDescription(nd)
                println("Description updated.")
                saveItems()
            }
            "6" -> {
                println("Finished editing.")
                return
            }
            else -> println("Invalid input.")
        }
    }
}

fun showMyProducts(user: Int) {
    val myProducts = users[user]?.itemIDs?.mapNotNull { items[it] }?.sortedBy { it.prodID } ?: emptyList()
    if (myProducts.isEmpty()) {
        println("You have no products.")
        return
    }
    // Rough column widths
    println("ProdID | Name                | Category       | UnitPrice  | Qty")
    myProducts.forEach { it ->
        println(String.format("%6d | %-20s | %-14s | %10.2f | %3d", it.prodID, it.name, it.category, it.price, it.quantity))
    }
}

fun showLowStock(user: Int) {
    val low = users[user]?.itemIDs?.mapNotNull { items[it] }?.filter { it.quantity < 5 } ?: emptyList()
    if (low.isEmpty()) {
        println("No low-stock products (<5).")
        return
    }
    var idx = 0
    while (idx < low.size) {
        val it = low[idx]
        println("ProdID: ${it.prodID}")
        println("Name: ${it.name}")
        println("Category: ${it.category}")
        println("Description: ${it.description}")
        println("Price: ${it.price} | Quantity: ${it.quantity}")
        println("Press N for next, X to exit")
        val cmd = readlnOrNull()
        if (cmd?.lowercase() == "x") return
        idx++
    }
}

fun sellMenu(user: Int) {
    while (true) {
        println("\n--- SELL MENU ---")
        println("1. Add New Item")
        println("2. Edit Stock")
        println("3. Show My Products")
        println("4. Show My Low Stock Products")
        println("5. Exit Sell Menu")
        when (readlnOrNull()?.trim()) {
            "1" -> addNewItem(user)
            "2" -> editStock(user)
            "3" -> showMyProducts(user)
            "4" -> showLowStock(user)
            "5" -> return
            else -> println("Invalid option.")
        }
    }
}