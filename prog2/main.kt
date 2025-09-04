import kotlin.system.exitProcess
import kotlin.io.readlnOrNull
import java.io.File

class User(
    val id: Int,
    val password: String,
    val name: String,
    val address: String,
    val contact: Int
) {
    val itemIDs = mutableListOf<Int>()
}

class Item(
    val prodID: Int,
    val sellID: Int,
    var name: String,
    var category: String,
    var description: String,
    var quantity: Int,
    var price: Float
) {
    fun subtractQuantity(decrement: Int) {
        if (decrement <= this.quantity) this.quantity -= decrement
        else println("Not enough stock available.")
    }

    fun replenish(stock: Int) { this.quantity += stock }
    fun changeCategory(newCategory: String) { this.category = newCategory }
    fun changeDescription(newDescription: String) { this.description = newDescription }
    fun changePrice(newPrice: Float) { this.price = newPrice }
}

class Transaction(val buyer: Int, val seller: Int, val date: String) {
    private val itemArray = mutableListOf<Int>()
    data class TransactionItem(val prodID: Int, val quantity: Int, val price: Float)
    private val items = mutableListOf<TransactionItem>()
}

val userfile = File("Users")
val itemfile = File("Items")

val users = mutableMapOf<Int, User>()
val items = mutableMapOf<Int, Item>()

fun setUpFiles() {
    if (!userfile.exists()) userfile.createNewFile()  
    if (!itemfile.exists()) itemfile.createNewFile()  
    getUsers()
    getItems()
    return
}

fun getUsers() {
    val lines = userfile.readLines()
    var i = 0
    while (i < lines.size) {
        if (lines[i].isEmpty()) { i++; continue }
        if (i + 3 >= lines.size) break
        val idPass = lines[i].split(" ", limit = 2)
        val id = idPass[0].toInt()
        val password = if (idPass.size > 1) idPass[1] else ""
        val name = lines[i + 1]
        val address = lines[i + 2]
        val contact = lines[i + 3].toInt()
        users[id] = User(id, password, name, address, contact)
        i += 5
    } return
}

fun getItems() {
    val lines = itemfile.readLines()
    var i = 0
    while (i < lines.size) {
        if (lines[i].isEmpty()) { i++; continue }
        if (i + 4 >= lines.size) break
        val prodsell = lines[i].split(" ", limit = 2)
        val prodID = prodsell[0].toInt()
        val sellID = prodsell[1].toInt()
        val name = lines[i + 1]
        val category = lines[i + 2]
        val description = lines[i + 3]
        val quantprice = lines[i + 4].split(" ", limit = 2)
        val quantity = quantprice[0].toInt()
        val price = quantprice[1].toFloat()
        items[prodID] = Item(prodID, sellID, name, category, description, quantity, price)
        i += 6
    } return
}

fun validateString(input: String?, max: Int): String {
    var current = input
    while (true) {
        if (current != null && current.length <= max && current.isNotEmpty()) return current
        else {
            println("Input can only be up to $max characters long. Please try again:")
            current = readlnOrNull()
        }
    }
}

fun validateID(type: Boolean, idInput: Int?): Int {
    var id = idInput
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
    if (users.size < 100) {
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
            val contact = readlnOrNull()?.toIntOrNull() ?: 0 //should have validation?
            println("Press S to save, X to cancel, and R to redo")
            when (readlnOrNull()?.lowercase()) {
                "x" -> return
                "r" -> continue
                "s" -> {
                    users[id] = User(id, password, name, address, contact)
                    println("User registered successfully!")
                    //save userfile, rewrite the old one
                    return
                }
                else -> println("Invalid input, please try again:")
            }
        }
    } else {
        println("Sorry, maximum number of users reached.")
        return
    }
}

fun login() {
    while (true) {
        println("\n============================\n")
        println("Press x then enter at the username to go back to the main menu")
        println("Username (ID):")
        val username = readlnOrNull()
        if (username?.lowercase() == "x") return
        val userID = username?.toIntOrNull()
        println("Password:")
        val password = readlnOrNull()
        if (userID != null && users[userID]?.password == password) {
            userMenu(userID)
            return
        }
        println("Invalid input. Please try again.")
    }
}

fun userMenu(user: Int) {
    println("Welcome, ${users[user]?.name}!")
    println("Press B to enter the buy menu.\nPress S to enter the sell menu.\nPress any other key to log-out.")
    when (readlnOrNull()?.lowercase()) {
        "b" -> buyMenu(user)
        "s" -> sellMenu(user)
        else -> return
    }
    return
}

fun main() {
    setUpFiles()
    while (true) {
        println("\n============================\n")
        println("Press R to register.\nPress L to log-in.\nPress A for admin options.\nPress X to exit the program.")
        when (readlnOrNull()?.lowercase()) {
            "r" -> register()
            "l" -> login()
            "a" -> admin()
            "x" -> exitProcess(0)
            else -> println("\tInvalid input.\n")
        }
    }
}
