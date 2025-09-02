fun validateQuantity(input: Int?): Int? {
    var current = input
    while (true) {
        if (current>=0) {
            return current
        } else {
            println("Input cannot be negative please try again:")
            current = readlnOrNull()?.toIntOrNull()()
        }
    }
}


fun sellMenu(user: Int){
    //choices
}

fun addNewItem(user: Int){

    println("\n============================\n")

    if ((users[user]?.itemIDs?.size ?: 0) > 20){
        println("Cannot add more than 20 items.")
    } else {

        println("Please input the item ID:")
        val id = validateID(false, readlnOrNull()?.toIntOrNull())

        println("Please input your the item name:")
        val name = validateString(readlnOrNull(), 20)

        println("Please input your the item category:")
        val category = validateString(readlnOrNull(), 20)

        println("Please input your the item description:")
        val description = validateString(readlnOrNull(), 20)
        
        println("Please input your the item quantity:")
        val quantity = validateQuantity

        println("Please input your the item price:")
        val price = validatePrice

        println("Press S to save, X to cancel, and R to redo")

        when (readlnOrNull()?.lowercase()) {
            "x" -> return
            "r" -> continue
            "s" -> {

                if (quantity){

                    users[user].itemIDs.add(id)
                    items[id] = Item(id, user, name, category, description, quantity, price)
                }
                println("Item added successfully!")
                return
            }
            else -> println("Invalid input, please try again:")
        }
    }
    return
}

fun editStock(){

}

fun showMyProducts(){

}

fun showLowStock(){

}