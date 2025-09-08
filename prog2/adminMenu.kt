fun showAllUsers() {
    println("ID \tPassword \tName \tAddress \tContact")
    users.values.sortedBy { it.id }.forEach { u ->
        println("${u.id} \t${u.password} \t${u.name} \t${u.address} \t${u.contact}")
    }
}

fun showAllSellers() {
    println("ID \tPassword \tName \tAddress \tContact \t#Items")
    users.values.sortedBy { it.id }.forEach { u ->
        val count = u.itemIDs.size
        if (count > 0) {
            println("${u.id} \t${u.password} \t${u.name} \t${u.address} \t${u.contact} \t${count}")
        }
    }
}

fun parseDateInput(prompt: String): Pair<LocalDate?, LocalDate?> {
    println("$prompt (YYYY-MM-DD):")
    val start = try {
        LocalDate.parse(readlnOrNull(), DateTimeFormatter.ISO_LOCAL_DATE)
    } catch (e: Exception) {
        null
    }
    println("End date (YYYY-MM-DD):")
    val end = try {
        LocalDate.parse(readlnOrNull(), DateTimeFormatter.ISO_LOCAL_DATE)
    } catch (e: Exception) {
        null
    }
    return Pair(start, end)
}

fun showTotalSalesInDuration() {
    val (start, end) = parseDateInput("Enter start date")
    if (start == null || end == null) {
        println("Invalid dates.")
        return
    }
    val total = transactions.filter {
        val d = try { LocalDate.parse(it.date) } catch (e: Exception) { null }
        d != null && !d.isBefore(start) && !d.isAfter(end)
    }.sumOf { it.totalAmount() }
    println("Total sales from $start to $end = $total")
}

fun showSellersSales() {
    val (start, end) = parseDateInput("Enter start date")
    if (start == null || end == null) {
        println("Invalid dates.")
        return
    }
    val sales = mutableMapOf<Int, Float>()
    transactions.filter {
        val d = try { LocalDate.parse(it.date) } catch (e: Exception) { null }
        d != null && !d.isBefore(start) && !d.isAfter(end)
    }.forEach { t ->
        sales[t.seller] = (sales[t.seller] ?: 0f) + t.totalAmount()
    }
    println("SellerID\tSellerName\tTotalSales")
    sales.forEach { (sid, amt) ->
        println("$sid\t${users[sid]?.name ?: "Unknown"}\t$amt")
    }
}

fun showShopaholics() {
    val (start, end) = parseDateInput("Enter start date")
    if (start == null || end == null) {
        println("Invalid dates.")
        return
    }
    val buyerTotals = mutableMapOf<Int, Float>()
    transactions.filter {
        val d = try { LocalDate.parse(it.date) } catch (e: Exception) { null }
        d != null && !d.isBefore(start) && !d.isAfter(end)
    }.forEach { t ->
        buyerTotals[t.buyer] = (buyerTotals[t.buyer] ?: 0f) + t.totalAmount()
    }
    println("BuyerID\tBuyerName\tTotalBought")
    buyerTotals.forEach { (bid, amt) ->
        println("$bid\t${users[bid]?.name ?: "Unknown"}\t$amt")
    }
}

fun admin() {
    println("\n============================\n")
    println("Please enter the administrator password:")
    val pwInput = readlnOrNull()
    if (pwInput == "H3LLo?") {
        while (true) {
            println("\n--- ADMIN MENU ---")
            println("1. Show All Users")
            println("2. Show All Sellers")
            println("3. Show Total Sales in Given Duration")
            println("4. Show Sellers Sales")
            println("5. Show Shopaholics")
            println("6. Back to Main Menu")
            when (readlnOrNull()?.trim()) {
                "1" -> showAllUsers()
                "2" -> showAllSellers()
                "3" -> showTotalSalesInDuration()
                "4" -> showSellersSales()
                "5" -> showShopaholics()
                "6" -> return
                else -> println("Invalid option.")
            }
        }
    } else {
        println("\nUnauthorized access not allowed. Press Enter to continue")
        readlnOrNull()
    }
}