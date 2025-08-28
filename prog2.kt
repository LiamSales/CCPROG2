//pseudo code first, understand the specs

//buying and selling of items
// 100 users, 20 items per seller, set of transactions
// user class buyer/seller , int UserID, password, name, adress, int contact, array of items [20], 
// item class prodID, item name, category, desc, int quanityt (stock), float price, seller ID 
// transaction, date obj, array of 5 items(their price too), referrence to buyer and seller id, total amount (sum of prices), buyer!=seller, all from same seller


//functions
//register as user, all info given, do the return valid thingy but make a parameter for size, unique userID, no need to be 0-99 can be 10000
// admin menu or user menu (sell and buy)

//sellmenu
/*
    add new item, everything except the seller ID, prodID sure
    prodID has to be globally unique
    seller has an item limit fixed array of 20

    after add, sell menu exit to user menu or cancel


*/


//use hashmaps for quick location

//exit user menu
//admin menu
// doesnt edit anything actually, just shows


//steps 1. obj setup, register set up, seller set up, buyer set up, admin