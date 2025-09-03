fun showAllUsers() {
    println("ID \tPassword \tName \tAddress \tContact")
    users.forEach { (_, user) -> // iterate key-value pairs properly
        println("${user.id} \t${user.password} \t${user.name} \t${user.address} \t${user.contact}")
    }
}

fun showAllSellers() {
    println("ID \tPassword \tName \tAddress \tContact \t# of Items")
    users.forEach { (_, user) ->
        if (user.itemIDs.isNotEmpty()) {
            println("${user.id} \t${user.password} \t${user.name} \t${user.address} \t${user.contact} \t${user.itemIDs.size}")
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
