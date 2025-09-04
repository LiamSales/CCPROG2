fun showAllUsers() {
    println("ID \tPassword \tName \tAddress \tContact")
    users.forEach { (key, value) -> // iterate key-value pairs properly
        println("${value.id} \t${value.password} \t${value.name} \t${value.address} \t${value.contact}")
    }
}

fun showAllSellers() {
    println("ID \tPassword \tName \tAddress \tContact \t# of Items")
    users.forEach { (key, value) ->
        if (value.itemIDs.isNotEmpty()) {
            println("${value.id} \t${value.password} \t${value.name} \t${value.address} \t${value.contact} \t${value.itemIDs.size}")
        }
    }
}

fun admin() {
    println("\n============================\n")
    println("Please enter the administrator password:")

    val pwInput = readlnOrNull()

    if (pwInput == "H3LLo?") {
        // TODO: Add menu options here for showAllUsers, showAllSellers, etc.
    } else {
        println("\nUnauthorized access not allowed. Press any key to continue")
        readln()
    }
}
