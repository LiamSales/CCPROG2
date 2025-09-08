import kotlin.system.exitProcess
import kotlin.io.readlnOrNull
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

data class User(
    val id: Int,
    val password: String,
    val name: String,
    val address: String,
    val contact: Int
) {
    val itemIDs = mutableListOf<Int>()
}

data class Item(
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

data class TransactionItem(val prodID: Int, val quantity: Int, val unitPrice: Float)
data class Transaction(val buyer: Int, val seller: Int, val date: String, val items: List<TransactionItem>) {
    fun totalAmount(): Float = items.sumOf { it.quantity * it.unitPrice }
}

val userfile = File("Users.txt")
val itemfile = File("Items.txt")
val tranfile = File("Transactions.txt")

val users = mutableMapOf<Int, User>()
val items = mutableMapOf<Int, Item>()
val transactions = mutableListOf<Transaction>()

fun setUpFiles() {
    if (!userfile.exists()) userfile.createNewFile()
    if (!itemfile.exists()) itemfile.createNewFile()
    if (!tranfile.exists()) tranfile.createNewFile()
    getItems()
    getUsers()
    getTransactions()
}

fun getUsers() {
    val lines = userfile.readLines()
    var i = 0
    while (i < lines.size) {
        if (lines[i].isEmpty()) { i++; continue }
        if (i + 3 >= lines.size) break
        val idPass = lines[i].split(" ", limit = 2)
        val id = idPass[0].toIntOrNull() ?: run { i += 5; continue }
        val password = if (idPass.size > 1) idPass[1] else ""
        val name = lines[i + 1]
        val address = lines[i + 2]
        val contact = lines[i + 3].toIntOrNull() ?: 0
        users[id] = User(id, password, name, address, contact)
        i += 5
    }
    // link items to users' itemIDs
    items.forEach { (_, item) ->
        users[item.sellID]?.itemIDs?.add(item.prodID)
    }
}

fun getItems() {
    val lines = itemfile.readLines()
    var i = 0
    while (i < lines.size) {
        if (lines[i].isEmpty()) { i++; continue }
        if (i + 4 >= lines.size) break
        val prodsell = lines[i].split(" ", limit = 2)
        val prodID = prodsell.getOrNull(0)?.toIntOrNull() ?: run { i += 6; continue }
        val sellID = prodsell.getOrNull(1)?.toIntOrNull() ?: run { i += 6; continue }
        val name = lines[i + 1]
        val category = lines[i + 2]
        val description = lines[i + 3]
        val quantprice = lines[i + 4].split(" ", limit = 2)
        val quantity = quantprice.getOrNull(0)?.toIntOrNull() ?: 0
        val price = quantprice.getOrNull(1)?.toFloatOrNull() ?: 0f
        items[prodID] = Item(prodID, sellID, name, category, description, quantity, price)
        i += 6
    }
}

fun getTransactions() {
    // Simple parser for Transactions.txt format created by saveTransactions()
    // Each transaction block:
    // DATE|buyer|seller
    // prodID qty unitPrice
    // ...
    // --- (separator)
    if (!tranfile.exists()) return
    val lines = tranfile.readLines()
    var i = 0
    while (i < lines.size) {
        val header = lines[i]
        if (header.isEmpty()) { i++; continue }
        val headerParts = header.split("|")
        if (headerParts.size != 3) { i++; continue }
        val date = headerParts[0]
        val buyer = headerParts[1].toIntOrNull() ?: 0
        val seller = headerParts[2].toIntOrNull() ?: 0
        i++
        val itemsList = mutableListOf<TransactionItem>()
        while (i < lines.size && lines[i] != "---") {
            val p = lines[i].split(" ")
            val pid = p.getOrNull(0)?.toIntOrNull() ?: 0
            val qty = p.getOrNull(1)?.toIntOrNull() ?: 0
            val unit = p.getOrNull(2)?.toFloatOrNull() ?: 0f
            itemsList.add(TransactionItem(pid, qty, unit))
            i++
        }
        // skip separator
        if (i < lines.size && lines[i] == "---") i++
        transactions.add(Transaction(buyer, seller, date, itemsList))
    }
}

fun saveUsers() {
    val sb = StringBuilder()
    users.values.sortedBy { it.id }.forEach { u ->
        sb.append("${u.id} ${u.password}\n")
        sb.append("${u.name}\n")
        sb.append("${u.address}\n")
        sb.append("${u.contact}\n\n")
    }
    userfile.writeText(sb.toString())
}

fun saveItems() {
    val sb = StringBuilder()
    items.values.sortedBy { it.prodID }.forEach { it ->
        sb.append("${it.prodID} ${it.sellID}\n")
        sb.append("${it.name}\n")
        sb.append("${it.category}\n")
        sb.append("${it.description}\n")
        sb.append("${it.quantity} ${it.price}\n\n")
    }
    itemfile.writeText(sb.toString())
}

fun saveTransactions() {
    val sb = StringBuilder()
    transactions.forEach { t ->
        sb.append("${t.date}|${t.buyer}|${t.seller}\n")
        t.items.forEach { it2 ->
            sb.append("${it2.prodID} ${it2.quantity} ${it2.unitPrice}\n")
        }
        sb.append("---\n")
    }
    tranfile.writeText(sb.toString())
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

fun validateID(needUniqueUserID: Boolean, idInput: Int?): Int {
    var id = idInput
    if (needUniqueUserID) {
        while (true) {
            if (id == null || users.containsKey(id)) {
                println("Invalid or existing ID, please enter a new numeric ID:")
                id = readlnOrNull()?.toIntOrNull()
            } else return id
        }
    } else {
        while (true) {
            if (id == null || items.containsKey(id)) {
                println("Invalid or existing product ID, please enter a NEW numeric product ID:")
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
            val contact = readlnOrNull()?.toIntOrNull() ?: 0
            println("Press S to save, X to cancel, and R to redo")

            when (readlnOrNull()?.lowercase()) {
                "x" -> return
                "r" -> continue
                "s" -> {
                    users[id] = User(id, password, name, address, contact)
                    println("User registered successfully!")
                    saveUsers()
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
    println("Press B to enter the buy menu.\nPress S to enter the sell menu.\nPress x to log-out.")
    while (true){
        when (readlnOrNull()?.lowercase()) {
            "b" -> buyMenu(user)
            "s" -> sellMenu(user)
            "x" -> {
                println("Logging out...")
                return
            }
            else -> println("\tInvalid input please try again:.\n")
        }
    }
}

fun main(){
    setUpFiles()
    while (true) {
        println("\n============================\n")
        println("Press R to register.\nPress L to log-in.\nPress A for admin options.\nPress X to exit the program.")
        when (readlnOrNull()?.lowercase()) {
            "r" -> register()
            "l" -> login()
            "a" -> admin()
            "x" -> {
                println("Saving data and exiting...")
                saveUsers()
                saveItems()
                saveTransactions()
                exitProcess(0)
            }
            else -> println("\tInvalid input.\n")
        }
    }
}

/*

notes after vibecoding:

use data class when necessary instead of regular classes, do they work like structs?
sumOf instead of foreach loop

does the underscore in (_,x) tell the compiler to ignore?

figure out how this works
    // link items to users' itemIDs
    items.forEach { (_, item) ->
        users[item.sellID]?.itemIDs?.add(item.prodID)
    }

learn how string builder works

fun saveUsers() {
    val sb = StringBuilder()
    users.values.sortedBy { it.id }.forEach { u ->
        sb.append("${u.id} ${u.password}\n")
        sb.append("${u.name}\n")
        sb.append("${u.address}\n")
        sb.append("${u.contact}\n\n")
    }
    userfile.writeText(sb.toString())
}

> ok this is for adding to the text file, but how does it do the new ones? does it just rebuild the entire dataset?

always have !=null checks


   println("Enter the number of the item to edit, or X to exit:")
    val input = readlnOrNull()
    if (input?.lowercase() == "x") return
    val idx = input?.toIntOrNull()?.minus(1)
    if (idx == null || idx !in cart.indices) {
        println("Invalid selection.")
        return

what does the minus(1) do


learn when to use try catch for errors

*/