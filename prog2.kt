import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

// create HashMaps for quick lookup later if needed
// val users = mutableMapOf<Int, User>()
// val items = mutableMapOf<Int, Item>()

class User(
    val id: Int,
    val name: String,
    val password: String,
    val address: String,
    val contact: Int
) {
    val itemIDs = mutableListOf<Int>()

    fun addItem(itemID: Int) {
        if (itemIDs.size < 20) {
            this.itemIDs.add(itemID)
        } else {
            println("Cannot add more than 20 items.")
        }
    }
}

class Item(
    val prodID: Int,
    val sellID: Int,
    var name: String,
    var category: String,
    var description: String,
    var price: Float,
    var quantity: Int
) {
    fun subtractQuantity(decrement: Int) {
        if (decrement <= this.quantity) this.quantity -= decrement
        else println("Not enough stock available.")
    }

    fun replenish(stock: Int) {
        this.quantity += stock
    }

    fun changeCategory(newCategory: String) {
        this.category = newCategory
    }

    fun changeDescription(newDescription: String) {
        this.description = newDescription
    }

    fun changePrice(newPrice: Float) {
        this.price = newPrice
    }
}

class Transaction(
    val buyer: Int,
    val seller: Int,
    val date: String
) {
    // MutableList lets you add multiple items with quantities
    private val itemArray = mutableListOf<Int>()    // replaced Arrayof<Int>(5)

    // Suggestion: use a data class for items inside transaction
    // data class TransactionItem(val prodID: Int, val quantity: Int, val price: Float)
    // private val items = mutableListOf<TransactionItem>()
}

fun validateString(input: String, max: Int): String {
    var current = input
    while (true) {
        if (current.length <= max && current.isNotEmpty()) return current
        else {
            println("Input can only be up to $max characters long. Please try again:")
            current = readln()
        }
    }
}

fun register() {
    // suggestion: ask for user details, validate them, add to users HashMap
}

fun login() {
    // suggestion: check userID & password before entering menu
}

fun userMenu(userID: Int) {
    // fixed println syntax
    println("Welcome, ${userID}!")  
    // suggestion: use when clause for menu choices inside a loop, not recursion
}

fun main() {
    // main loop: keep running until user exits
    register()
    // suggestion: avoid deep function calls; use while(true) for menu navigation
}
