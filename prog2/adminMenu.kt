fun showAllUsers(){
    println("ID \tPassowrd \tName \tAddress \tContact")
    users.forEach { user ->
        println("${user.value.id} \t${user.value.password} \t${user.value.name} \t${user.value.address} \t${user.value.contact}")
     }
}

fun showAllSellers(){
        println("ID \tPassowrd \tName \tAddress \tContact \t# of Items")
        users.forEach { user ->
        if (user.value.itemIDs.isNotEmpty())
        println("${user.value.id} \t${user.value.password} \t${user.value.name} \t${user.value.address} \t${user.value.contact} \t${user.value.itemIDs.size}")
     }
}

// now all thats needed are the ones with dates

fun admin(){

    println("\n============================\n")
    println("Please enter the administrator password:")

    val pwInput = readlnOrNull()

    if (pwInput == "H3LLo?"){

        

    } else {
        println("\n Unauthorized access not allowed. Press any key to continue")
        readln()
    }
    
    return
}