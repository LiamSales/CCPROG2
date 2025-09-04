class BuyItem(val buyer: Int, val item: Int, val quantity: Int)

fun viewAllProducts() {
    // just a linear search, x^2, sellerid and (not empty), return
}

fun showProductsofSpecific() {
    // ask, display, return
}

fun searchByCategory() {
    // string contains
}

fun searchByName() {
    // string contains
}

fun addToCart(cart: MutableList<BuyItem>): String {
    return if (cart.size < 10) {
        // placeholder success message
        "Successfully added to cart"
    } else {
        "Cart is too full, unable to add more items"
    }
}

fun editCart(cart: MutableList<BuyItem>) {
    // if cart not empty
}

fun checkOutMenu(cart) {
    // ask the date
    // decrement the quantity
    // if quantity == 0, remove from list
    // adjust the files
}

fun buyMenu(user: Int) {
    
    val cart = mutableListOf<BuyItem>()

    println("Press V to view all products.\nPress S to show only the products of a specific seller.\nPress C to search for products by category.\nPress N to search for products by name.\n")
    println("Press A to add a product to your cart.\nPress E to edit your cart.\nPress O to check-out.\n")
    println("Press X to exit without checking-out")

    while (true){
                when (readlnOrNull()?.lowercase()) {
                "v" -> viewAllProducts()
                "s" -> showProductsofSpecific()
                "c" -> searchByCategory()
                "n" -> searchByName()
                "a" -> addToCart(cart)
                "e" -> editCart(cart)
                "o" -> checkOutMenu(cart)
                "x" -> return
                else -> println("\tInvalid input. Please try again:\n")
            }
        }
}
