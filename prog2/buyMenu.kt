class BuyItem(val buyer: Int, val item: Int, val quantity: Int)

fun viewAllProducts(sortedList: List<Item>) {
    sortedList.forEach{
        item -> if (item.quantity>0) println("${item.prodID}\t${item.name}\t${item.category}\t${item.price}\t${item.quantity}\t")
    }//make it such that the seller is grouped, is there a groupby function?
    return
}

fun showProductsofSpecific() {

    println("Please provide the seller ID:")
    val id = readlnOrNull()?.toInt()

    users[id]?.itemIDs?.forEach{
        item ->  if (items[item]?.quantity > 0) println("${items[item]?.prodID}\t${items[item]?.name}\t${items[item]?.category}\t${items[item]?.price}\t${items[item]?.quantity}\t")
    }
        
    return
}

fun searchByCategory() {

    println("Please provide the category:")
    val search = readln()

    items.forEach { (key, value) -> 
        if (value.category.contains(search)) println("${value.prodID}\t${value.name}\t${value.category}\t${value.price}\t${value.quantity}\t")
    }
    return
}

fun searchByName() {

    println("Please provide the name:")
    val search = readln()

    items.forEach { (key, value) -> 
        if (value.name.contains(search)) println("${value.prodID}\t${value.name}\t${value.category}\t${value.price}\t${value.quantity}\t")
    }
    return
}

fun addToCart(cart: MutableList<BuyItem>, user: Int){

    if (cart.size<10){

        println("Provide the product ID:")
        val id = readln().toInt()

        if (items[id]?.sellID == user){
            println("Cannot add item you are selling. Press any button to continue")
            readln()
            return
        } 

        println("Provide the quantity:")

        val quantity = readln().toInt() // loop asking again if quantity<1
        
        val entry = BuyItem(user, id, quantity)

        cart.add(entry)

    } else {
        println("Cannot add more items to cart. Maximum capacity reached. Press any button to continue")
        readln()
    }
    return
}

fun editCart(cart: MutableList<BuyItem>) {
    // if cart not empty
    // remove items from specific seller (linear search)
    // remove specific item
    // edit quantity
    // finish (return)
}


fun checkOutMenu(cart: MutableList<BuyItem>): String {
    // ask the date
    // decrement the quantity
    // if quantity == 0, remove from list
    // adjust the files
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
