import kotlin.system.exitProcess
import kotlin.io.readlnOrNull

val users = mutableMapOf<Int, User>()
val items = mutableMapOf<Int, Item>()

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
    private val itemArray = mutableListOf<Int>()

    data class TransactionItem(val prodID: Int, val quantity: Int, val price: Float)
    private val items = mutableListOf<TransactionItem>()
}

fun validateString(input: String?, max: Int): String {
    var current = input
    while (true) {
        if (current != null && current.length <= max && current.isNotEmpty()) {
            return current
        } else {
            println("Input can only be up to $max characters long. Please try again:")
            current = readlnOrNull()
        }
    }
}

fun validateID(type: Boolean, idInput: Int?): Int {
    var id = idInput
    // true type for user, false type for item
    if (type) {
        while (true) {
            if (id == null || users.containsKey(id)) {
                println("Invalid ID please try again:")
                id = readlnOrNull()?.toIntOrNull()
            } else return id
        }
    } else {
        while (true) {
            if (id == null || items.containsKey(id)) {
                println("Invalid ID please try again:")
                id = readlnOrNull()?.toIntOrNull()
            } else return id
        }
    }
}

fun register() {

    println("\n============================\n")

    while (true) {
        println("Please input your ID:")
        val id = validateID(true, readlnOrNull()?.toIntOrNull())

        println("Please input your Name:")
        val name = validateString(readlnOrNull(), 20)

        println("Please input your Password:")
        val password = validateString(readlnOrNull(), 10)

        println("Please input your Address:")
        val address = validateString(readlnOrNull(), 30)

        println("Please input your Contact Number:")
        val contact = readlnOrNull()?.toIntOrNull() ?: 0

        println("Press S to save, X to cancel, and R to redo")

        //how do i loop this such that invalid input please try again reprompts this
        when (readlnOrNull()?.lowercase()) {
            "x" -> return
            "r" -> continue
            "s" -> {
                val newUser = User(id, name, password, address, contact)
                users[id] = newUser
                //inserts in the file
                println("User registered successfully!")
                return
            }
            else -> println("Invalid input, please try again:")
        }
    }
}

fun login() {

    while(true) {

        println("\n============================\n")
        println("Press x then enter at the username to go back to the main menu")
        println("Username:")
        val username = readlnOrNull()?.lowerCase()
        if (username == "X") return
        println("Password:")
        val password = readlnOrNull()
        
        if (users[username].?password == password){
            userMenu(username)
            return
        }

        println("Invalid input. Please try again.")

    }
}

fun userMenu(user: Int){

    while(true){

    //x to logout, return, goes to main()
    //b for buy
    buyMenu(user)
    //s for sell
    sellMenu(user)
    //else loops

    }
}

fun main() {
    while (true) {
        println("\n============================\n")
        println("Press R to register. Press L to login. Press X to exit the program.")
        when (readlnOrNull()?.lowercase()) {
            "r" -> register()
            "l" -> login()
            "x" -> exitProcess(0)
            else -> println("\tInvalid input.\n")
        }
    }
}
