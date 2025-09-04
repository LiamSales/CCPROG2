class BuyItem(val buyer: Int, val item: Int, val quantity: Int)

fun viewAllProducts(sortedList: List<Item>) {
    sortedList.forEach{
        item -> if (item.quantity>0) println("${item.prodID}\t${item.name}\t${item.category}\t${item.price}\t${item.quantity}\t")
    }//make it such that the seller is grouped, is there a groupby function?
    return
}

fun showProductsofSpecific() {

    println("Please provide the seller ID:")
    val id = readlnOrNull()?.toIntOrNull() ?: 0

    users[id]?.itemIDs?.forEach{
        item ->  if (items[item]?.quantity > 0) println("${items[item]?.prodID}\t${items[item]?.name}\t${items[item]?.category}\t${items[item]?.price}\t${items[item]?.quantity}\t")
    }
        
    return
}

fun searchByCategory(sortedList: List<Item>) {
    // string contains
}

fun searchByName(sortedList: List<Item>) {
    // string contains
}

fun addToCart(cart: MutableList<BuyItem>): MutableList<BuyItem> {

}

fun editCart(cart: MutableList<BuyItem>): MutableList<BuyItem> {
    // if cart not empty
}

fun checkOutMenu(cart: MutableList<BuyItem>): String {
    // ask the date
    // decrement the quantity
    // if quantity == 0, remove from list
    // adjust the files
    return "Items checked out successfully!"
}

fun buyMenu() {
    
    val cart = mutableListOf<BuyItem>()
    val sortedList = items.values.toList().sortedBy { it.sellID }


    println("Press V to view all products.\nPress S to show only the products of a specific seller.\nPress C to search for products by category.\nPress N to search for products by name.\n")
    println("Press A to add a product to your cart.\nPress E to edit your cart.\nPress O to check-out.\n")
    println("Press X to exit without checking-out")

    while (true){
                when (readlnOrNull()?.lowercase()) {
                "v" -> viewAllProducts(sortedList)
                "s" -> showProductsofSpecific()
                "c" -> searchByCategory(sortedList)
                "n" -> searchByName(sortedList)
                "a" -> addToCart(cart)
                "e" -> editCart(cart)
                "o" -> checkOutMenu(cart)
                "x" -> return
                else -> println("\tInvalid input. Please try again:\n")
            }
        }
}
