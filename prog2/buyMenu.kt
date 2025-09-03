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

fun checkOutMenu() {
    // ask the date
    // decrement the quantity
    // if quantity == 0, remove from list
    // adjust the files
}

fun buyMenu(user: Int) {
    // array of BuyItem called cart, holds 10 at most
    val cart = mutableListOf<BuyItem>()
    println(addToCart(cart))
}
