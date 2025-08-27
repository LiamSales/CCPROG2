# CCPROG2
Programming with Structured Data Types


Shopping App
Online shopping applications abound nowadays. These provide a convenient way for buyers to browse at different options and to
purchase items without going to the usual brick-and-mortar stores. These applications also provide a venue for budding
entrepreneurs to sell their products without need to pay rent and with reach to many more potential buyers.
For your project, you are to create a shopping program that facilitate the buying and selling of items. This also entails that
the program should maintain (keep track of) the set of at most 100 users, the set of at most 20 items per seller, and the set of
transactions. Below are the descriptions of what each of these should contain.
Users – Users can be buyers and/or sellers. Each user information has a numeric userID, a string of at most 10 characters
for the password, a string of at most 30 characters for the address, a numeric contact number, and a string of at most 20
characters for the name.
Items – Each item has a numeric productID, a string (of at most 20 characters) item name, a string (of at most 15
characters) category, a string (of at most 30 characters) item description, numeric quantity available, numeric unit price, and
numeric sellerID (which corresponds to the seller’s userID)
Transactions – Each transaction contains a date (which further contains the numeric month, day, and year), the set of at
most 5 items, the buyer’s user ID, the seller’s user ID and total amount of the transaction. It should be noted that though there
may be at most 5 items, these may be of different quantity per item and all of these items in the transaction should be from the
same seller. The buyer should also not be the seller.
Your shopping program should have the following features and sub-features: [Note that at the beginning of the program,
the file Users.txt and Items.txt, if existing, should be loaded to the main memory for processing. Note that format of the text files
are listed under Exit.]
1. Register as a User
Once this option is chosen, the user is tasked to input all the necessary information. For simplicity, the userID is
also given by the user as input. However, this userID should be unique (that is, no other such userID exists in the
list of users). This information should be added to the array of Users. After registering, the user is redirected back
to the main menu.
2. User Menu
Once this option is chosen, the user is asked to input his userID and password. If userID and password does not
match any registered user, there should be an error message and the user is redirected back to the main menu.
Upon successful log-in, the user is presented with the following options:
2.1 Sell Menu
2.1.1 Add New Item
In this option, the user can add a new item that he wants to sell. Information for the item
should be encoded by the user (including product ID), except the seller ID – this is
supposed to be automatically stored since the user is logged in. The product ID should be
unique regardless of seller. To ensure that there is no monopoly of the market, each seller
can only sell at most 20 different items. Thus, the program should check that the seller
has not reached the limit yet before allowing the user to add an item to sell. Only 1 item
can be added every time this option is chosen. The data is stored in the Items array.
Afterwhich, the user is shown the Sell Menu again.
2.1.2 Edit Stock
P a g e 2 | 8
In this option, the set of all of this seller’s products is first shown in table format (see
Show My Products for how it is supposed to be displayed). Then, asks the productID
whose information is to be edited.
If an invalid productID is given, a message should be shown, then the user is redirected
back to the Sell Menu.
If a valid productID is given, the following submenu is repeatedly offered to the user until
the user chooses to finish editing.
2.1.2.1 Replenish
The user is tasked to input the quantity that will be ADDED to the existing
quantity for this item.
2.1.2.2 Change Price
The user is asked to input the new unit price.
2.1.2.3 Change Item Name
The user is asked to input the new item name.
2.1.2.4 Change Category
The user is asked to input a new category.
2.1.2.5 Change Description
The user is asked to type a new description.
2.1.2.6 Finish Editing
The user is redirected back to the Sell Menu
2.1.3 Show My Products
The information should be shown in this sequence: productID, item name, category, unit
price, quantity. The data should be shown in table format, sorted in increasing order based on
the productID. Numbers should be right justified, strings should be left justified in their
“columns”. Hint: use the capacity of the containers to determine the size of the “columns”
and refer to CCPROG1 notes on printf().
2.1.4 Show My Low Stock Products
All the information (including product description, but excluding seller’s userID) about each
product whose quantity is BELOW 5 should be shown. Display should be done one at a time
per product. Allow the user to press N to see the next and press X to exit the view.
2.1.5 Exit Sell Menu
The user is directed back to the User Menu.
2.2 Buy Menu
Once this option is chosen, the program checks the current directory if there is a previously saved cart for this
user (see Exit User Menu below) and loads the contents of the binary1 file to main memory for possible
processing.
2.2.1 View all Products
The program displays all items from 1 seller at a time, sorted in increasing order based on
seller ID. So, show seller ID first, then followed by a table of the products of that seller. The
table is similar as the one discussed in Show My Products.
When the user press N, the next seller ID is shown and under it, the table of products of that
seller, and so on. The user can press X to exit this view and go back to the Buy Menu.
2.2.2 Show all Products by a Specific Seller
The program asks the user to input the seller’s ID that he wishes to view. Then, the program
displays the seller’s ID and below it the table of that seller’s products (the table is in same
format as that discussed in Show My Products).
2.2.3 Search Products by Category
P a g e 3 | 8
The program asks the user to input the category he wants to see. Product information should
be displayed similar to how it is presented in Show My Low Count Products, including allowing
the user to type N and X to navigate through the display. Only those products that fit the
given category should be displayed.
2.2.4 Search Products by Name
The program asks the user to input keywords of the product he wants to see. Note that
substring search is applied here, meaning the keyword the user entered may appear as part of
a longer string in the product name of some of the products. For example, if user wants to
search for shirt, then ITEM NAMES that contain shirt, like T-shirt, long-sleeved polo shirt,
couple shirts should all be displayed. Those that match, the displayed information should be
similar to Show My Low Count Products, including allowing the user to type N and X to
navigate through the display.
2.2.5 Add to Cart
Each user can have up to at most 10 different items (of varying quantities) in his cart at any
given time. If the cart is full already, no additional items can be added to the cart and a
suggestion that the user proceed to Edit Cart or Check Out first before adding more items.
The user is asked to input the productID and the quantity that the user wants to buy. Note
that the productID should be existing. The quantity should also be available. Otherwise, error
messages will be displayed and item is not added to the cart. A buyer cannot buy his own
product.
2.2.6 Edit Cart
All items in the Cart will be displayed first following the display format described in Show All
Products by a Specific Seller (of course, no input seller ID is necessary).
2.2.6.1 Remove all items from Seller
The user is asked to input the seller ID. All items in the cart that is sold by this
seller is deleted from the cart.
2.2.6.2 Remove Specific Item
The user is asked to input the product ID. That item in the cart will be removed.
2.2.6.3 Edit Quantity
The user is asked to input the product ID and the new quantity. The item’s
quantity is updated.
2.2.6.4 Finish Edit Cart
The program goes back to Buy Menu.
2.2.7 Check Out Menu
Get the date as input from the user. [This is for testing and our demo purposes. In real life,
the machine’s date is used.] Note that one final check is done with the availability (because
part of the items to be checked out might have been from previously saved items in the cart
and the seller might have updated the information already (like quantity and price). If there is
any change, the buyer should be notified by displaying the old and the new quantity and
price; the changes should also be updated in the cart. The buyer is given notice (message)
that he can still go to Edit Cart.
2.2.7.1 All
All items in the Cart will be bought by the user. Each transaction will contain all
items from the same seller only. A display of summary of each transaction should
be displayed on the screen. The display should look similar to a receipt, that is in
a table format, it should list the following: quantity, product ID, item name, unit
price, total price for item. Then below the table, a total amount due for the
transaction and payable to sellerID and seller name. Transaction information
P a g e 4 | 8
should be added to the binary1 file Transactions.dat. Items already bought should
be removed from the cart and product’s quantity should also be updated in the
Items array. [For simplicity, we omitted the payment facility and delivery facility.
We assume that is already done upon check out.]
2.2.7.2 By a Specific Seller
The user is asked to input the seller ID whose items in cart he wishes to buy
already. A summary of the transaction is shown, transaction data is added to the
binary1 file, items bought should be removed from the cart, quantity in Items
array should be updated. [Refer to All for details.]
2.2.7.3 Specific Item
The user is asked to input the product ID of the item he had included in the cart
to check out. A summary/receipt is displayed, transaction is included in the
binary1 file, item should be removed from the cart, and quantity should be
updated in Items array.
2.2.7.4 Exit Check Out
The program reverts back to the Buy Menu.
2.2.8 Exit Buy Menu
The user is directed back to the User Menu
2.3 Exit User Menu
In this option, if there are any items left in the user’s cart, these will be saved to a binary1 file of Items
with the filename <user’s ID>.bag (example: 123.bag). The program then exits the user menu and goes back to
the main menu.
3. Admin Menu
The user is assumed to be the administrator of the program. For verification, he is asked to input the password.
For simplicity, let us assume that the correct password is “H3LLo?”. If invalid log-in, a message “Unauthorized
access not allowed” is shown, then program goes back to Main Menu. Upon successful log-in, the following
features are available:
3.1 Show All Users
This feature shows all the users arranged by user ID. The display should be in table format in the following
sequence: userID, password, name, address, phone number
3.2 Show All Sellers
The program shows all the users who have items to be sold (i.e., if there exists in the Items array a seller ID
matching the user’s ID). The display should be in table format in the following sequence: userID, password,
name, address, phone number, number of items for sale [not quantity]
3.3 Show Total Sales in Given Duration
The program asks the user to input 2 dates to serve as the start and end dates. The program then checks the
contents of Transactions.dat whose dates fall within the duration from start to the end dates, the total
amount of all the transactions.
3.4 Show Sellers Sales
The program asks the user to input 2 dates to serve as the start and end dates. The program then checks the
contents of Transactions.dat whose dates fall within the duration from start to the end dates, display the total
sales for each seller in table format in the following sequence: seller ID, seller name, total sales in the duration
3.5 Show Shopaholics
The program asks the user to input 2 dates to serve as the start and end dates. The program then checks the
contents of Transactions.dat whose dates fall within the duration from start to the end dates, display the total
P a g e 5 | 8
amount for each buyer in table format in the following sequence: buyer ID, buyer name, total amount bought
in the duration
3.6 Back to Main Menu
The program reverts back to the Main Menu
4. Exit – The program saves into text file all current data on set of users and set of items. Then the program terminates
properly. The users should be saved in Users.txt with the following format:
<user1 id><space><password><new line>
<name><new line>
<address><new line>
<contact number><new line>
<new line>
<user2 id>><space><password><new line>
<name><new line>
<address><new line>
<contact number><new line>
<new line>
...
<userN id><space><password><new line>
<name><new line>
<address><new line>
<contact number><new line>
<new line>
<eof>
The items should be saved in Items.txt with the following format:
<product1 id><space><seller id><new line>
<item name><new line>
<category><new line>
<description><new line>
<quantity><space><unit price><new line>
<new line>
<product2 id><space><seller id><new line>
<item name><new line>
<category><new line>
<description><new line>
<quantity><space><unit price><new line>
<new line>
...
<productN id><space><seller id><new line>
<item name><new line>
<category><new line>
<description><new line>
<quantity><space><unit price><new line>
<new line>
<eof>
1 - All binary file requirements can also be replaced with text file, but the format has to be decided by the student. Note that text
file requirements cannot be replaced with binary files.
Bonus
P a g e 6 | 8
A maximum of 10 points may be given for features over & above the requirements (other features not conflicting with the given
requirements or changing the requirements). For example: (1) showing sales report on statistics on top selling items, total sales,
and total profit; (2) using top selling items and items with discounts to generate recommended items [top picks] to shoppers; (3)
saving transactions from multiple days and comparing the sales of each day] subject to evaluation of the teacher. Required
features must be completed first before bonus features are credited. Note that use of conio.h, or other advanced C
commands/statements may not necessarily merit bonuses
