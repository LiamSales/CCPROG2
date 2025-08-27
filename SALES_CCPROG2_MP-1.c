/***********************************************************************************
**********************
This is to certify that this project is my own work, based on my personal efforts
in studying and applying the concepts learned. I
have constructed the functions and their respective algorithms and corresponding
code by myself. The program was run, tested,
and debugged by my own efforts. I further certify that I have not copied in part or
whole or otherwise plagiarized the work of other
students and/or persons.
Sales, Liam Miguel V. , DLSU ID# 12229148
***********************************************************************************
**********************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAX_USERS 100
#define MAX_PER_SELLER 20
#define MAX_CART 10
//preprocessor directives
typedef struct
{
int prodID;
char name[21];
char category[16];
char description[31];
int quantity;
float price;
int seller;
} item;
typedef struct
{
int userID;
char password[11];
char address[31];
long contact;
char name[21];
} user;
typedef struct
{
int day;
int month;
int year;
} calendar;
typedef struct {
calendar date;
int prodID [5];
int buyer;
int seller;
float total;
} transaction;

// typdef for structs
void registration();
void usermenu();
void userchoose(int ID);
void sellmenu(int ID);
void newitem(int ID);
void editstock(int ID);
void editchoice(int ID, int editID, item** itemlist);
void replenish(int ID, int editID, item** itemlist);
void changeprice(int ID, int editID, item** itemlist);
void changename(int ID, int editID, item** itemlist);
void changecateg(int ID, int editID, item** itemlist);
void changedesc(int ID,int editID, item** itemlist);
void showproducts(int ID, int x);
void lowstock(int ID);
void buymenu(int ID);
void viewall(int ID);
void showbyseller(int ID);
void editcart(int ID);
void checkout(int ID);
void allusers();
void adminchoice();
void adminmenu();
void totalsales();
void sellersales();
void shopaholics();
void leave();
//function prototypes
/* compareusers compares two elements from the array userdata passed by the
accessUsers function by their userID
@param *a - A pointer to the first element to compare.
@param *b - A pointer to the next element directly after *a.
@return A negative integer if userA's userID is less than userB's userID, zero if
both are equal, and a positive integer if userA's userID is greater than userB's
userID
Pre-condition: a and b are expected to be valid pointers to user objects, passed by
accessUsers, to be used by the qsort() function.
*/
int
compareUsers(const void *a,
const void *b)
{
const user *userA = (const user *)a;
const user *userB = (const user *)b;
//compares the userID of each element
return (userA->userID - userB->userID);
}
/* accessUsers reads user data from the file Users.txt opened in another function
and then returns those details sorted in an array of the struct users.
@param *fp - A pointer to the file Users.txt containing user data.
@return userdata, a pointer to the array of users where all the users inside are
properly sortedas the index value is equal to the userID
Pre-condition: The file pointed to by fp contains valid user data in the specified
format.
*/

user*
accessUsers(FILE *fp)
{
int i = 0;
user *userdata = malloc(MAX_USERS * sizeof(user));
//memory allocation
rewind(fp);
while (i < MAX_USERS && fscanf(fp, "%d %[^\n] %[^\n] %[^\n] %ld",
&userdata[i].userID, userdata[i].password, userdata[i].name, userdata[i].address,
&userdata[i].contact) == 5)
i++;
//scans the text file
qsort(userdata, i, sizeof(user), compareUsers);
//sorts the array, where smaller userIDs go first
for (i = 0; i < MAX_USERS; i++)
{
if (userdata[i].userID != i)
{
int targetIndex = userdata[i].userID;
while (userdata[targetIndex].userID != targetIndex)
{
user temp = userdata[targetIndex];
userdata[targetIndex] = userdata[i];
userdata[i] = temp;
targetIndex = userdata[i].userID;
// moves the users to their correct indexes corresponding to their
ID, replaces the empty userIDs with zeros
}
}
}
return userdata;
}
/* acessItems reads user data from the file Items.txt opened in another function
and then returns those details sorted in an array of the struct items.
@param *fp - A pointer to the file Items.txt containing item data.
@param ID - An integer representing the seller ID so that items can be sorted into
columns.
@return itemdata, A pointer to an array of pointers to item objects read from the
file, a 2D array where items should be sorted where the column index is equivalent
to the sellerID
Pre-condition: The file pointed to by fp contains valid item data in the specified
format.
*/
item**
accessItems(FILE *fp,
int ID)
{
int i, prevSeller = -1;
item addItem;
item** itemdata = (item**)malloc(MAX_USERS * sizeof(item*));
//memory allocation
for (i = 0; i < MAX_USERS; i++)
itemdata[i] = (item*)malloc(MAX_PER_SELLER * sizeof(item));

//memory allocation for each row
i=0;
//resets i back to 0
rewind(fp);
while (!feof(fp))
{
fscanf(fp, "%d %d\n%s\n%s\n%s\n%d %f\n\n", &addItem.prodID,
&addItem.seller, addItem.name, addItem.category, addItem.description,
&addItem.quantity, &addItem.price);
//scans the text file, makes sure that the ID argument passed to the
parameter keeps items with the same sellerID
if (addItem.seller == ID)
{
if (prevSeller != -1 && prevSeller != addItem.seller)
i=0;
//resets i back to zero if it falls under a different user
itemdata[addItem.seller][i] = addItem;
i++;
prevSeller = addItem.seller;
}
}
}

return itemdata;

/* welcome displays a welcome message for the shopping program, including a ASCII
art of a shopping cart.
*/
void
welcome()
{
printf("\t\t\t\t\t
___\n\t\t\t\t__________.//\n\t\t\t\t\\##########||\n\t\
t\t\t \\#########||\n\t\t\t\t _________//\n\t\t\t\t (o)
(o)");
//shopping cart ascii art
printf("\n\
n-------------------------------------------------------------------------------\n\
n");
printf("====================== WELCOME TO THE SHOPPING PROGRAM!
=====================");
printf("\n\
n-------------------------------------------------------------------------------\n\
n");
}
/* choose displays the main menu and asks the user to choose an option
Pre-condition: The user must only input integers, or else it might result in an
infinite loop
*/
void
choose()
{
int x;

printf("\n\t\t\tPlease type 0 to register as a user.\n");
printf("\n\t\t\tPlease type 1 to open the user menu.\n");
printf("\n\t\t\tPlease type 2 to open the admin menu.\n");
printf("\n\t\t\tPlease type 3 to exit the program.\n");
printf("\n\
n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\
n");
printf("\t\t\t");
scanf("%d", &x);
while(x<0||x>3)
{
printf("\n\tSorry, that is an invalid input. Please try again:\n\n");
printf("\t\t\t");
scanf("%d", &x);
}
//makes sure the user only inputs valid integers
printf("\
n*******************************************************************************\n\
n");
switch (x)
{
case 0:
registration();
break;
case 1:
usermenu();
break;
case 2:
adminmenu();
break;
case 3:
leave();
}
//brings the user to the chosen function
}
/* registration handles the registration process for users, asking the current user
to give the necessary information, and checking if it is acceptable.
Pre-condition: The user must only give integers for the UserID and long integers
for the contatct number, otherwise resuklting in an infinite loop.
*/
void
registration()
{
user newuser;
user* existing = NULL;
char ans='N';
int invalid = 0, i, j, found;
int available[MAX_USERS];
FILE *fp;
for (i = 0; i < MAX_USERS; i++)
available[i] = i;
//initializes available ids to make them equal to their index
printf("\t\t\tREGISTER AS A USER");

fp = fopen("Users.txt", "a+");
existing = accessUsers(fp);
do

{

printf("\n\
n*******************************************************************************\n\
n");
printf("\tPlease enter the following details:\n\n");
printf("\tUser ID:\t");
scanf("%d", &newuser.userID);
for (i=0; (i<MAX_USERS) && (!invalid); i++)
if (newuser.userID == existing[i].userID)
invalid=1;
//checks if a userID is already in use
while (invalid || newuser.userID < 0 || newuser.userID > MAX_USERS-1)
{
invalid=0;
if (newuser.userID < 0 || newuser.userID > MAX_USERS-1)
printf("\n\tSorry, that is an invalid input.\n\tPlease
enter an ID from 00-%d.\n\n", MAX_USERS-1);
else
{
printf("\n\tSorry, that ID is currently in use.\n\
tPlease choose another number.\n\n");
printf("\tWould you like to see a list of available
IDs? (Y/N):\t");
scanf(" %c", &ans);
while(ans!='Y'&&ans!='N'&&ans!='y'&&ans!='n')
{
printf("\n\tSorry that is an invalid
input, please try again:\t");

scanf(" %c", &ans);
}
if(ans=='Y'||ans=='y')
{
ans='N';
printf("\tAvailable User IDs:\n\n");
for(i=0; i<MAX_USERS; i++)
{
found = 0;
for (j = 0; j < MAX_USERS; j++)
if (existing[j].userID

== available[i])

found = 1;
if (!found)
printf("%d ", available[i]);
}
printf("\n");
}

}

printf("\n\tUser ID:\t");
scanf("%d", &newuser.userID);
for (i=0; (i<MAX_USERS) && (!invalid); i++)
if (newuser.userID == existing[i].userID)
invalid=1;
//checks the userID again so the loop will continue
}
getchar();
printf("\n\tPassword:\t");
gets(newuser.password);
while(strlen(newuser.password)>10 || strlen(newuser.password) == 0)
{
printf("\n\tSorry, that is an invalid input.\n\tPlease enter a
password not exceeding 10 characters.\n\n");
printf("\tPassword:\t");
gets(newuser.password);
}
printf("\n\tName:\t\t");
gets(newuser.name);
while(strlen(newuser.name)>20 || strlen(newuser.name) == 0)
{
printf("\n\tSorry, that is an invalid input.\n\tPlease enter a
name not exceeding 20 characters.\n\n");
printf("\tName:\t");
gets(newuser.name);
}
printf("\n\tAddress:\t");
gets(newuser.address);
while(strlen(newuser.address)>30 || strlen(newuser.address) == 0)
{
printf("\n\tSorry, that is an invalid input.\n\tPlease enter an
address not exceeding 30 characters.\n\n");
printf("\tAddress:\t");
gets(newuser.address);
}
printf("\n\tContact Number:\t");
scanf("%ld", &newuser.contact);
while (!(newuser.contact>0&&newuser.contact<999999999999))
{
printf("\n\tSorry, that is an invalid input.\n\tPlease enter a
valid phone number not exceeding 11 digits.\n\n");
printf("\tContact Number:\t");
scanf("%ld", &newuser.contact);
}
printf("\n\n\tPlease confirm if the information is correct. (Y/N):\t");
getchar();
scanf(" %c", &ans);
while(ans!='Y'&&ans!='N'&&ans!='y'&&ans!='n')
{
printf("\n\tSorry that is an invalid input, please try again:\
t");

scanf(" %c", &ans);
}

} while (ans=='N'||ans=='n');
//loops if the user wishes to redo it
fprintf(fp,"%d %s\n%s\n%s\n%ld\n\n", newuser.userID, newuser.password,
newuser.name, newuser.address, newuser.contact);
//writes the new user details in the Users.txt file
free(existing);
//frees the allocated memory of the array
fclose(fp);
printf("\n\tThank you, %s. You have been successfully registered.\n\
tRedirecting back to the main menu.\n", newuser.name);
printf("\n\
n******************************************************************************\n\
n");
//returns back to the main menu
choose();
}
/* usermenu is where the user may login with their userID and password.
Pre-condition: The user must only give integers for the UserID, otherwise
resuklting in an infinite loop.
*/
void
usermenu()
{
user* existing = NULL;
int ID, i, valid=0;
char pw[11];
FILE *fp;
printf("\t\t\tUSER MENU");
printf("\n\
n******************************************************************************\n\
n");
printf("\tPlease enter your User ID:\t");
scanf("%d", &ID);
printf("\tPlease enter your Password:\t");
getchar();
gets(pw);
fp = fopen("Users.txt", "r");
existing = accessUsers(fp);
for(i = 0; (i < MAX_USERS) && (!valid) ; i++)
if((ID == existing[i].userID) && (strcmp(pw, existing[i].password) == 0))
valid = 1;
//checks if the ID corresponds with the password
free(existing);
//frees the allocated memory for the array
fclose(fp);
if(valid)
{
printf("\n\tThank you, %s. You have successfully logged-in.",
existing[ID].name);
userchoose(ID);

}
else

printf("\n\tSorry, that was incorrect. Redirecting back to the main
menu.\n\n");
printf("\
n******************************************************************************\n\
n");
choose();
}
/* userchoose asks the logged-in user to choose whether they want to go to the buy
menu, sell menu, or back to the main menu
@param ID - the userID of the logged-in user
Pre-condition: The user must only input integers, or else it might result in an
infinite loop
*/
void
userchoose(int ID)
{
int choice;
printf("\n\
n-------------------------------------------------------------------------------\
n");
printf("\n\t\t\tPlease type 0 to open the sell menu.\n");
printf("\n\t\t\tPlease type 1 to open the buy menu.\n");
printf("\n\t\t\tPlease type 2 to exit the user menu.\n");
printf("\
n-------------------------------------------------------------------------------\n\
n");
printf("\t\t\t");
scanf("%d", &choice);
while(choice<0||choice>2)
{
printf("\n\tSorry, that is an invalid input. Please try again:\n\n");
printf("\t\t\t");
scanf("%d", &choice);
}
//makes sure the user only inputs valid integers
printf("\
n===============================================================================\
n");
switch (choice)
{
case 0:
sellmenu(ID);
break;
case 1:
buymenu(ID);
break;
case 2:
choose();
}
}

/* sellmenu asks the logged-in user to choose the different options as sellers
@param ID - the userID of the logged-in user
Pre-condition: The user must only input integers, or else it might result in an
infinite loop
*/
void
sellmenu(int ID)
{
int choice;
printf("\t\t\tSELL MENU");
printf("\
n===============================================================================\
n");
printf("\n\t\t\tPlease type 0 to add a new item.\n");
printf("\n\t\t\tPlease type 1 to edit the existing stock.\n");
printf("\n\t\t\tPlease type 2 to show a list of all your products.\n");
printf("\n\t\t\tPlease type 3 to show a list of items low in stock.\n");
printf("\n\t\t\tPlease type 4 to exit the sell menu.\n");
printf("\
n-------------------------------------------------------------------------------\n\
n");
printf("\t\t\t");
scanf("%d", &choice);
while(choice<0||choice>4)
{
printf("\n\tSorry, that is an invalid input. Please try again:\n\n");
printf("\t\t\t");
scanf("%d", &choice);
}
//makes sure the user only inputs valid integers
printf("\
n-------------------------------------------------------------------------------\n\
n");

}

switch (choice)
{
case 0:
newitem(ID);
break;
case 1:
editstock(ID);
break;
case 2:
showproducts(ID,1);
break;
case 3:
lowstock(ID);
break;
case 4:
userchoose(ID);
}

/* newitem handles the registration process for new items, asking the current user
to give the necessary information, and checking if it is acceptable.
@param ID - the userID of the logged-in user
Pre-condition: The user must only give integers for the prodID and quantity, as

well as only float values for the price otherwise resuklting in an infinite loop.
*/
void
newitem(int ID)
{
item newitem;
item** itemarray = NULL;
int i, j, invalid=1;
char ans='N';
FILE *fp;
fp = fopen("Items.txt", "a+");
itemarray = accessItems(fp, ID);
for (i = 0; (i<MAX_PER_SELLER) && invalid; i++)
if (itemarray[ID][i].prodID == 0)
invalid=0;
//checks if there is any vacant row in the column of the userID in the 2D
array
if (invalid)
{
invalid=0;
printf("\tSorry, you have too many items listed at the moment.\n\
tPlease do not be a greedy capitalist.\n");
printf("\
n===============================================================================\
n");
sellmenu(ID);
}
else
do
{
printf("\tPlease enter the following details:\n\n");
printf("\n\tProduct ID:\t\t");
scanf("%d", &newitem.prodID);

already exists

for (i=0; (i<MAX_USERS) && !invalid; i++)
for (j=0; j<MAX_PER_SELLER; j++)
if (newitem.prodID == itemarray[i][j].prodID)
invalid=1;
//searches through the entire 2d array to see if a product id

while (invalid || newitem.prodID < 0 || newitem.prodID >
MAX_USERS * MAX_PER_SELLER-1)
{
invalid=0;
MAX_PER_SELLER-1)

if (newitem.prodID < 0 || newitem.prodID > MAX_USERS *

printf("\n\tSorry, that is an invalid input.\n\
tPlease enter an ID from 00-%d.\n\n", MAX_USERS * MAX_PER_SELLER-1);
else
printf("\n\tSorry, that ID is currently in use.\n\
tPlease choose another number.\n\n");

printf("\n\tProduct ID:\t\t");
scanf("%d", &newitem.prodID);

}

for (i=0; i<MAX_USERS; i++)
for (j=0; j<MAX_PER_SELLER; j++)
if (newitem.prodID == itemarray[i][j].prodID)
invalid=1;
//searches again to make the loop continue

getchar();
printf("\n\tProduct Name:\t\t");
gets(newitem.name);
while(strlen(newitem.name)>20 || strlen(newitem.name) == 0)
{
printf("\n\tSorry, that is an invalid input.\n\tPlease
enter a name not exceeding 20 characters.\n\n");
printf("\tProduct Name:\t\t");
gets(newitem.name);
}

0)

printf("\n\tCategory:\t\t");
gets(newitem.category);
while(strlen(newitem.category)>15 || strlen(newitem.category) ==
{

printf("\n\tSorry, that is an invalid input.\n\tPlease
enter a category name not exceeding 15 characters.\n\n");
printf("\tCategory:\t\t");
gets(newitem.category);
}
printf("\n\tDescription:\t\t");
gets(newitem.description);
while(strlen(newitem.description)>30 ||
strlen(newitem.description) == 0 )
{
printf("\n\tSorry, that is an invalid input.\n\tPlease
limit your description to 30 characters.\n\n");
printf("\tDescription:\t\t");
gets(newitem.description);
}
printf("\n\tCurrent Quantity:\t");
scanf("%d", &newitem.quantity);
while(newitem.quantity <= 0)
{
printf("\n\tSorry, that is an invalid input.\n\tPlease
enter a value greater than zero.\n\n");
printf("\tCurrent Quantity:\t");
scanf("%d", &newitem.quantity);
}
printf("\n\tPrice:\t\t\t");
scanf("%f", &newitem.price);
while(newitem.price < 0.0)
{
printf("\n\tSorry, that is an invalid input.\n\tPlease

enter a value greater than or equal to zero.\n\n");
printf("\tPrice:\t\t\t");
scanf("%f", &newitem.price);
}
newitem.seller = ID;
printf("\n\n\tPlease confirm if the information is correct.
(Y/N):\t");

again:\t");

scanf(" %c", &ans);
while(ans!='Y'&&ans!='N'&&ans!='y'&&ans!='n')
{
printf("\n\tSorry that is an invalid input, please try
}

scanf(" %c", &ans);

printf("\
n-------------------------------------------------------------------------------\n\
n");
} while (ans=='N'||ans=='n');
fprintf(fp,"%d %d\n%s\n%s\n%s\n%d %.2f\n\n", newitem.prodID, newitem.seller,
newitem.name, newitem.category, newitem.description, newitem.quantity,
newitem.price);
free(itemarray);
fclose(fp);
printf("\tThank you. You have been successfully registered a new product.\n\
tRedirecting back to the sell menu.\n");
printf("\
n===============================================================================\
n");
sellmenu(ID);
}
/* editstock asks the logged-in user to choose an item they want to edit.
@param ID - the userID of the logged-in user
Pre-condition: The user must only give integers for the prodID otherwise resuklting
in an infinite loop.
*/
void
editstock(int ID)
{
FILE *fp;
item** itemlist = NULL;
int editID, i, invalid=1;
showproducts(ID, 0);
//show the list of products without reverting back to the sellmenu
fp = fopen("Items.txt", "r");
itemlist = accessItems(fp, ID);
printf("\n\n\tPlease input the product ID of the product you wish to edit:\
t");

scanf("%d", &editID);

for (i=0; i < MAX_PER_SELLER && invalid; i++)
if (editID == itemlist[ID][i].prodID)
invalid=0;
//search if the ID is within the column of the seller
if (invalid)
{
printf("\n\n\tSorry, you do not have access to that product ID.\n\
tRedirecting you back to the sell menu.\n");
printf("\
n===============================================================================\
n");
sellmenu(ID);
}
else
editchoice(ID ,i, itemlist);
}
/* editchoice asks the logged-in user to choose what part of their chosen product
do they want to edit.
@param ID - the userID of the logged-in user
@param i - the row index to be passed to array of items in order to locate the
specific product
@param **itemlist - the pointer to the array of pointers to the items, wherein
theuy are sorted by sellerID in the columns
Pre-condition: The user must only give integers, otherwise resulting in an infinite
loop.
*/
void
editchoice(int ID,
int i,
item** itemlist)
{
int choice;
printf("\
n-------------------------------------------------------------------------------\n\
n");
printf("\n\t\t\tPlease type 0 replenish stocks.\n");
printf("\n\t\t\tPlease type 1 to change the price.\n");
printf("\n\t\t\tPlease type 2 to change the item name.\n");
printf("\n\t\t\tPlease type 3 to change the category.\n");
printf("\n\t\t\tPlease type 4 to change the description.\n");
printf("\n\t\t\tPlease type 5 to finish editing.\n");
printf("\
n-------------------------------------------------------------------------------\n\
n");
printf("\t\t\t");
scanf("%d", &choice);
while(choice<0||choice>5)
{
printf("\n\tSorry, that is an invalid input. Please try again:\n\n");
printf("\t\t\t");
scanf("%d", &choice);
}
printf("\
n-------------------------------------------------------------------------------\n\
n");

switch (choice)
{
case 0:
replenish(ID, i, itemlist);
break;
case 1:
changeprice(ID, i, itemlist);
break;
case 2:
changename(ID, i, itemlist);
break;
case 3:
changecateg(ID, i, itemlist);
break;
case 4:
changedesc(ID, i, itemlist);
break;
case 5:
free(itemlist);
sellmenu(ID);
}
}
/* replenish asks the logged-in user how many of the item to add to its current
quantity.
@param ID - the userID of the logged-in user
@param i - the row index to be passed to array of items in order to locate the
specific product
@param **itemlist - the pointer to the array of pointers to the items, wherein
theuy are sorted by sellerID in the columns
Pre-condition: The user must only give integers, otherwise resulting in an infinite
loop.
*/
void
replenish(int ID,
int i,
item** itemlist)
{
int add;
printf("\
n-------------------------------------------------------------------------------\n\
n");
printf("Please input the quantity that will be added to the existing stock:\
t");
scanf("%d", &add);
while (add<1)
{
printf("\n\tSorry, that is an invalid input.\n\tPlease enter a value
greater than zero.\n\n");
printf("Please input the quantity that will be added to the existing
stock:\t");
scanf("%d", &add);
}
itemlist[ID][i].quantity+=add;
printf("\

n-------------------------------------------------------------------------------\n\
n");
printf("New Quantity:\t%d", itemlist[ID][i].quantity);
}

editchoice(ID,i,itemlist);

/* changeprice asks the logged-in user what the new price of the chosen item shall
be.
@param ID - the userID of the logged-in user
@param i - the row index to be passed to array of items in order to locate the
specific product
@param **itemlist - the pointer to the array of pointers to the items, wherein
theuy are sorted by sellerID in the columns
Pre-condition: The user must only give float values, otherwise resulting in an
infinite loop.
*/
void
changeprice(int ID,
int i,
item** itemlist)
{
float newprice;
printf("\
n-------------------------------------------------------------------------------\n\
n");
printf("Please input the new price:\t");
scanf("%f", &newprice);
while (newprice<0)
{
printf("\n\tSorry, that is an invalid input.\n\tPlease enter a value
greater than or equal to zero.\n\n");
printf("Please input the new price:\t");
scanf("%f", &newprice);
}
itemlist[ID][i].price=newprice;
printf("\
n-------------------------------------------------------------------------------\n\
n");
printf("New Price:\t%.2f", itemlist[ID][i].price);
}

editchoice(ID,i, itemlist);

/* changename asks the logged-in user what the new name of the chosen item shall
be.
@param ID - the userID of the logged-in user
@param i - the row index to be passed to array of items in order to locate the
specific product
@param **itemlist - the pointer to the array of pointers to the items, wherein
theuy are sorted by sellerID in the columns
*/
void
changename(int ID,
int i,
item** itemlist)

{

char newname[21];

printf("\
n-------------------------------------------------------------------------------\n\
n");
printf("Please input the new product name:\t");
gets(newname);
while(strlen(newname)>20 || strlen(newname) == 0)
{
printf("\n\tSorry, that is an invalid input.\n\tPlease enter a name not
exceeding 20 characters.\n\n");
printf("Please input the new product name:\t");
gets(newname);
}
//

itemlist[ID][i].name=newname;

printf("\
n-------------------------------------------------------------------------------\n\
n");
printf("New Product Name:\t%s", itemlist[ID][i].name);
editchoice(ID,i,itemlist);
}
/* changecateg asks the logged-in user what the new category of the chosen item
shall be.
@param ID - the userID of the logged-in user
@param i - the row index to be passed to array of items in order to locate the
specific product
@param **itemlist - the pointer to the array of pointers to the items, wherein
theuy are sorted by sellerID in the columns
*/
void
changecateg(int ID,
int i,
item** itemlist)
{
char newcateg[16];
printf("\
n-------------------------------------------------------------------------------\n\
n");
printf("Please input the new product category:\t");
gets(newcateg);
while(strlen(newcateg)>15 || strlen(newcateg) == 0)
{
printf("\n\tSorry, that is an invalid input.\n\tPlease enter a category
name not exceeding 15 characters.\n\n");
printf("Please input the new product category:\t");
gets(newcateg);
}
//

itemlist[ID][i].category=newcateg;

printf("\
n-------------------------------------------------------------------------------\n\
n");

printf("New Category:\t%s", itemlist[ID][i].category);
}

editchoice(ID,i,itemlist);

/* changedesc asks the logged-in user what the new description of the chosen item
shall be.
@param ID - the userID of the logged-in user
@param i - the row index to be passed to array of items in order to locate the
specific product
@param **itemlist - the pointer to the array of pointers to the items, wherein
theuy are sorted by sellerID in the columns
*/
void
changedesc(int ID,
int i,
item** itemlist)
{
char newdesc[31];
printf("\
n-------------------------------------------------------------------------------\n\
n");
printf("Please input the new product description:\t");
gets(newdesc);
while(strlen(newdesc)>30 || strlen(newdesc) == 0)
{
printf("\n\tSorry, that is an invalid input.\n\tPlease enter a
description not exceeding 30 characters.\n\n");
printf("Please input the new product description:\t");
gets(newdesc);
}
//

itemlist[ID][i].description=newdesc;

printf("\
n-------------------------------------------------------------------------------\n\
n");
printf("New Description:\t%s", itemlist[ID][i].description);
}

editchoice(ID,i,itemlist);

/* showproducts shows a table of all the details of each product of the logged in
user.
@param ID - the userID of the logged-in user
@param x - equal to either 1 or 0, to tell whether the function is called by the
sellmenu function (1) or if it was called by the editstock function (0).
*/
void
showproducts(int ID,
int x)
{
int i;
FILE *fp;
item** itemlist = NULL;
fp = fopen("Items.txt", "r");

itemlist = accessItems(fp, ID);
printf("ID\t|Name\t\t\t|Category\t\t|Price\t\t|Qty.");
printf("\
n-------------------------------------------------------------------------------\
n");
for (i=0; i<MAX_PER_SELLER; i++)
{
if (itemlist[ID][i].prodID)
{
printf("%d\t|%s\t\t\t|%s\t\t\t|%.2f\t\t|%d", itemlist[ID]
[i].prodID, itemlist[ID][i].name, itemlist[ID][i].category, itemlist[ID][i].price,
itemlist[ID][i].quantity);
printf("\
n-------------------------------------------------------------------------------\
n");
}
}
fclose(fp);
printf("\n\n");

}

if (x)
sellmenu(ID);

/* lowstock shows all the details of each product with a quantity less than 5,
it prints out the itmes one by one and asks whether the user wants to stop viewing
the list.
@param ID - the userID of the logged-in user
*/
void
lowstock(int ID)
{
int i;
FILE *fp;
item** itemlist;
char choice= 'N';
fp = fopen("Items.txt", "r");
itemlist = accessItems(fp, ID);
for (i=0; i<MAX_PER_SELLER && (choice=='N'||choice=='n'); i++)
{
if (itemlist[ID][i].quantity < 5 && itemlist[ID][i].prodID!=0)
{
printf("\n\tProduct ID:\t%d\n", itemlist[ID][i].prodID);
printf("\n\tProduct Name:\t%s\n", itemlist[ID][i].name);
printf("\n\tCategory:\t%s\n", itemlist[ID][i].category);
printf("\n\tDescription:\t%s\n", itemlist[ID]
[i].description);
printf("\n\tPrice:\t\t%.2f\n", itemlist[ID][i].price);
printf("\n\tQuantity:\t%d\n", itemlist[ID][i].quantity);
printf("\
n-------------------------------------------------------------------------------\
n");
printf("\nPlease type N to see the next product, and X to

exit the view:\t");
='x')

scanf(" %c", &choice);
while(choice!='N'&&choice!='X'&&choice!='n'&&choice!
{

please try again:\t");
}

printf("\n\tSorry that is an invalid input,
scanf(" %c", &choice);

}

}
fclose(fp);
printf("\n\
n-------------------------------------------------------------------------------\
n");
sellmenu(ID);
}
/* buymenu asks the logged-in user to choose the different options as buyers
@param ID - the userID of the logged-in user
Pre-condition: The user must only input integers, or else it might result in an
infinite loop
*/
void
buymenu(int ID)
{
int choice;
printf("\t\t\tBUY MENU");
printf("\
n===============================================================================\
n");
printf("\n\t\t\tPlease type 0 to view all products.\n");
printf("\n\t\t\tPlease type 1 to show products by a specific seller.\n");
printf("\n\t\t\tPlease type 2 to search for products by category.\n");
printf("\n\t\t\tPlease type 3 to search for products by name.\n");
printf("\n\t\t\tPlease type 4 to add a product to your cart.\n");
printf("\n\t\t\tPlease type 5 to edit your cart.\n");
printf("\n\t\t\tPlease type 6 to enter the checkout menu.\n");
printf("\n\t\t\tPlease type 7 to exit the buy menu.\n");
printf("\
n-------------------------------------------------------------------------------\n\
n");
printf("\t\t\t");
scanf("%d", &choice);
while(choice<0||choice>7)
{
printf("\n\tSorry, that is an invalid input. Please try again:\n\
n");
printf("\t\t\t");
scanf("%d", &choice);
}
printf("\
n-------------------------------------------------------------------------------\n\
n");
switch (choice)

//
//
//

}

{
case 0:
viewall(ID);
break;
case 1:
showbyseller(ID);
break;
case 2:
searchbycategory();
break;
case 3:
searchbyname();
break;
case 4:
addtocart(ID);
break;
case 5:
editcart(ID);
break;
case 6:
checkout(ID);
break;
default:
userchoose(ID);
}

/* showproducts shows a table of all the details of each product of all the
sellers.
@param ID - the userID of the logged-in user
*/
void
viewall(int ID)
{
int i, j;
FILE *fp;
item** itemlist = NULL;
char choice= 'N';
fp = fopen("Items.txt", "r");
itemlist = accessItems(fp, ID);
printf("Seller ID\t|Product ID\t|Name\t\t|Category\t\t|Price\t\t|Qty.");
printf("\
n-------------------------------------------------------------------------------\
n");
for(i=0; i<MAX_USERS && (choice=='N'||choice=='n'); i++)
{
for(j=0; j<MAX_PER_SELLER; j++)
{
if (itemlist[i][j].prodID)
{
printf("%d\t|%d\t|%s\t\t\t|%s\t\t\t|%.2f\t\t|
%d",itemlist[i][j].seller, itemlist[i][j].prodID, itemlist[i][j].name, itemlist[i]
[j].category, itemlist[i][j].price, itemlist[i][j].quantity);
printf("\

n-------------------------------------------------------------------------------\
n");
}
}
printf("\nPlease type N to see the next seller, and X to exit the
view:\t");

scanf(" %c", &choice);
while(choice!='N'&&choice!='X'&&choice!='n'&&choice!='x')
{
printf("\n\tSorry that is an invalid input, please try again:\

t");

scanf(" %c", &choice);
}

}

fclose(fp);
printf("\n\n");
buymenu(ID);
}
/* showbyseller a table of all the details of each product of a seller, identified
by a user input which must be equivalent to the seller ID of the seller they are
viewing.
@param ID - the userID of the logged-in user
Pre-condition: The user must only input integers, or else it might result in an
infinite loop
*/
void
showbyseller(int ID)
{
int i, x;
FILE *fp;
item** itemlist = NULL;
printf("\n\tPlease input the Seller ID to see the products of that user:\t");
scanf("%d", &x);
while (x<0 || x>MAX_USERS-1)
{
printf("\n\tSorry, that is an invalid input.\n\tPlease enter an ID from
00-%d.\n\n", MAX_USERS-1);
printf("\tPlease input the Seller ID to see the products of that user:\
t");
scanf("%d", &x);
}
fp = fopen("Items.txt", "r");
itemlist = accessItems(fp, ID);
printf("\n\nID\t|Name\t\t\t|Category\t\t|Price\t\t|Qty.");
printf("\
n-------------------------------------------------------------------------------\
n");
for (i=0; i<MAX_PER_SELLER; i++)
{
if (itemlist[x][i].prodID)

{

printf("%d\t|%s\t\t\t|%s\t\t\t|%.2f\t\t|%d", itemlist[x]
[i].prodID, itemlist[x][i].name, itemlist[x][i].category, itemlist[x][i].price,
itemlist[x][i].quantity);
printf("\
n-------------------------------------------------------------------------------\
n");
}
}
fclose(fp);
printf("\n\n");
buymenu(ID);
}
/* editcart asks the logged-in user to choose what part of their chosen product do
they want to edit.
@param ID - the userID of the logged-in user
Pre-condition: The user must only input integers, or else it might result in an
infinite loop
*/
void
editcart(int ID)
{
int choice;
printf("\n\
n-------------------------------------------------------------------------------\
n");
printf("\n\t\t\tPlease type 0 to remove all times from a seller\n");
printf("\n\t\t\tPlease type 1 to remove a specific item in your cart.\n");
printf("\n\t\t\tPlease type 2 to edit the quantity of a specific item in your
cart.\n");
printf("\n\t\t\tPlease type 3 to finish editing your cart.\n");
printf("\
n-------------------------------------------------------------------------------\n\
n");
printf("\t\t\t");
scanf("%d", &choice);
while(choice<0||choice>3)
{
printf("\n\tSorry, that is an invalid input. Please try again:\n\n");
printf("\t\t\t");
scanf("%d", &choice);
}
}
/* checkout asks the logged-in user to for the date today and then checks out the
products they bought.
@param ID - the userID of the logged-in user
Pre-condition: The user must only input integers, or else it might result in an
infinite loop
*/
void
checkout(int ID)
{
calendar today;

printf("\t\t\tCHECKOUT MENU");
printf("\
n===============================================================================\n\
n");
printf("\tPlease input the current year:\t\t\t\t\t");
scanf("%d", &today.year);
printf("\tPlease input the current month as a number from 1 to 12:\t");
scanf("%d", &today.month);
while (today.month<0 || today.month>12)
{
printf("\n\tSorry, that is an invalid input. Please try again:\n");
printf("\tPlease input the current month as a number from 1 to 12:\t");
scanf("%d", &today.month);
}
printf("\tPlease input what day of the month it is:\t\t\t");
scanf("%d", &today.day);
while (today.day<0 || today.day>31 ||
((today.day>28 && today.month==2) && ((today.year%4!=0) ||
((today.year%4==0)&&(today.year%100==0)&&(today.year%400!=0))))||
((today.day>29 && today.month==2) && (today.year%4==0))||
(today.day>30 && today.month==4) ||
(today.day>30 && today.month==6) ||
(today.day>30 && today.month==9) ||
(today.day>30 && today.month==11) )
{
printf("\n\tSorry, that is an invalid input. Please try again:\n");
printf("\tPlease input what day of the month it is:\t\t\t");
scanf("%d", &today.day);
}
printf("\n\n\t");
switch(today.month)
{
case 1:
printf("January");
break;
case 2:
printf("February");
break;
case 3:
printf("March");
break;
case 4:
printf("April");
break;
case 5:
printf("May");
break;
case 6:
printf("June");
break;
case 7:
printf("July");
break;
case 8:
printf("August");
break;

case 9:
printf("September");
break;
case 10:
printf("October");
break;
case 11:
printf("November");
break;
case 12:
printf("December");
break;
}
printf(" %d, %d\n\n", today.day, today.year);
}
/* adminmenu aks the user to login if they are the administrator, if they do not
pass the verification, they are redirected back to the main menu
*/
void
adminmenu()
{
char pass[]="H3LLo?";
char word[11];
printf("\t\t\tADMIN MENU");
printf("\n\
n******************************************************************************\n\
n");
printf("\tPlease enter the administrator password:\t");
getchar();
scanf("%[^\n]s", word);
if(strcmp(pass,word) == 0)
{
printf("\n\tThank you. You have successfully logged-in.\n");
adminchoice();
}
else
{
printf("\n\tUnauthorized access not allowed.");
printf("\n\
n*******************************************************************************\n\
n");
choose();
}
}
/* checkout asks the administrator to choose what action they want to do.
Pre-condition: The user must only input integers, or else it might result in an
infinite loop
*/
void
adminchoice()
{
int choice;
printf("\

n-------------------------------------------------------------------------------\
n");
printf("\n\tPlease type 0 to show a list of all users.\n");
printf("\n\tPlease type 1 to show a list of all sellers.\n");
printf("\n\tPlease type 2 to show a list of the total sales in a given
duration.\n");
printf("\n\tPlease type 3 to show a list of each seller's sales in a given
duration.\n");
printf("\n\tPlease type 4 to show a list of the shopaholics in a given
duration.\n");
printf("\n\tPlease type 5 to return to the main menu.\n");
printf("\
n-------------------------------------------------------------------------------\
n");
printf("\n\t\t\t");
scanf("%d", &choice);
while(choice<0||choice>5)
{
printf("\n\n\tSorry, that is an invalid input. Please try again:\
n\n");
printf("\t\t\t");
scanf("%d", &choice);
}
printf("\
n-------------------------------------------------------------------------------\
n");

//

switch (choice)
{
case 0:
allusers();
break;
case 1:
allsellers();
break;
case 2:
totalsales();
break;
case 3:
sellersales();
break;
case 4:
shopaholics();
break;
case 5:
printf("\n");
choose();
}

}
/* allusers shows a table of all the details of each user in the Users.txt file
sorted by userID in ascending order.
*/
void
allusers()
{
int i;
FILE *fp;
user* userlist = NULL;

fp = fopen("Users.txt", "r");
userlist = accessUsers(fp);
printf("ID\t|Password\t|Name\t\t|Address\t\t|Phone Number");
printf("\
n-------------------------------------------------------------------------------\
n");
printf("%d\t|%s\t\t|%s\t\t|%s\t\t\t|%ld", userlist[0].userID,
userlist[0].password, userlist[0].name, userlist[0].address, userlist[0].contact);
printf("\
n-------------------------------------------------------------------------------\
n");
for (i=1; i<MAX_USERS; i++)
{
if (userlist[i].userID)
{
printf("%d\t|%s\t\t|%s\t\t|%s\t\t\t|%ld", userlist[i].userID,
userlist[i].password, userlist[i].name, userlist[i].address, userlist[i].contact);
printf("\
n-------------------------------------------------------------------------------\
n");
}
}

}

printf("\n\n");
adminchoice();

/* totalsales asks the administrator for 2 dates to show the total amount of the
transactions for the duration in between the two dates.
Pre-condition: The user must only input integers, or else it might result in an
infinite loop
*/
void
totalsales()
{
calendar today;
int i;
for(i=1; i<3; i++)
{
printf("\tPlease input the starting year:\t\t\t\t\t");
scanf("%d", &today.year);
printf("\tPlease input the starting month as a number from 1 to 12:\
t");

scanf("%d", &today.month);
while (today.month<0 || today.month>12)
{
printf("\n\tSorry, that is an invalid input. Please try again:\

n");

printf("\tPlease input the starting month as a number from 1 to

12:\t");

scanf("%d", &today.month);
}

printf("\tPlease input what day of the month it is:\t\t\t");
scanf("%d", &today.day);
while (today.day<0 || today.day>31 ||
((today.day>28 && today.month==2) && ((today.year%4!=0) ||
((today.year%4==0)&&(today.year%100==0)&&(today.year%400!=0))))||
((today.day>29 && today.month==2) && (today.year%4==0))||
(today.day>30 && today.month==4) ||
(today.day>30 && today.month==6) ||
(today.day>30 && today.month==9) ||
(today.day>30 && today.month==11) )
{
printf("\n\tSorry, that is an invalid input. Please try again:\
n");
}

printf("\tPlease input what day of the month it is:\t\t\t");
scanf("%d", &today.day);

if (i==1)
printf("\n\n\tStarting Date:");
else
printf("\n\n\tEnding Date:");
switch(today.month)
{
case 1:
printf("January");
break;
case 2:
printf("February");
break;
case 3:
printf("March");
break;
case 4:
printf("April");
break;
case 5:
printf("May");
break;
case 6:
printf("June");
break;
case 7:
printf("July");
break;
case 8:
printf("August");
break;
case 9:
printf("September");
break;
case 10:
printf("October");
break;
case 11:
printf("November");
break;
case 12:

printf("December");
break;

}

}
printf(" %d, %d\n\n", today.day, today.year);
}

/* sellersales asks the administrator for 2 dates to show the details of the
sellers different transactions for the duration in between the two dates.
Pre-condition: The user must only input integers, or else it might result in an
infinite loop
*/
void
sellersales()
{
calendar today;
int i;
for(i=1; i<3; i++)
{
printf("\tPlease input the starting year:\t\t\t\t\t");
scanf("%d", &today.year);
printf("\tPlease input the starting month as a number from 1 to 12:\t");
scanf("%d", &today.month);

t");

while (today.month<0 || today.month>12)
{
printf("\n\tSorry, that is an invalid input. Please try again:\n");
printf("\tPlease input the starting month as a number from 1 to 12:\
}

scanf("%d", &today.month);

printf("\tPlease input what day of the month it is:\t\t\t");
scanf("%d", &today.day);
while (today.day<0 || today.day>31 ||
((today.day>28 && today.month==2) && ((today.year%4!=0) ||
((today.year%4==0)&&(today.year%100==0)&&(today.year%400!=0))))||
((today.day>29 && today.month==2) && (today.year%4==0))||
(today.day>30 && today.month==4) ||
(today.day>30 && today.month==6) ||
(today.day>30 && today.month==9) ||
(today.day>30 && today.month==11) )
{
printf("\n\tSorry, that is an invalid input. Please try again:\n");
printf("\tPlease input what day of the month it is:\t\t\t");
scanf("%d", &today.day);
}
if (i==1)
printf("\n\n\tStarting Date:");
else
printf("\n\n\tEnding Date:");
switch(today.month)
{

}

case 1:
printf("January");
break;
case 2:
printf("February");
break;
case 3:
printf("March");
break;
case 4:
printf("April");
break;
case 5:
printf("May");
break;
case 6:
printf("June");
break;
case 7:
printf("July");
break;
case 8:
printf("August");
break;
case 9:
printf("September");
break;
case 10:
printf("October");
break;
case 11:
printf("November");
break;
case 12:
printf("December");
break;
}
printf(" %d, %d\n\n", today.day, today.year);
}

/* shopaholics asks the administrator for 2 dates to show the details of all the
buyers for the duration in between the two dates.
Pre-condition: The user must only input integers, or else it might result in an
infinite loop
*/
void
shopaholics()
{
calendar today;
int i;
for(i=1; i<3; i++)
{
printf("\tPlease input the starting year:\t\t\t\t\t");
scanf("%d", &today.year);
printf("\tPlease input the starting month as a number from 1 to 12:\t");

scanf("%d", &today.month);
while (today.month<0 || today.month>12)
{
printf("\n\tSorry, that is an invalid input. Please try again:\n");
printf("\tPlease input the starting month as a number from 1 to 12:\
t");

scanf("%d", &today.month);
}
printf("\tPlease input what day of the month it is:\t\t\t");
scanf("%d", &today.day);
while (today.day<0 || today.day>31 ||
((today.day>28 && today.month==2) && ((today.year%4!=0) ||
((today.year%4==0)&&(today.year%100==0)&&(today.year%400!=0))))||
((today.day>29 && today.month==2) && (today.year%4==0))||
(today.day>30 && today.month==4) ||
(today.day>30 && today.month==6) ||
(today.day>30 && today.month==9) ||
(today.day>30 && today.month==11) )
{
printf("\n\tSorry, that is an invalid input. Please try again:\n");
printf("\tPlease input what day of the month it is:\t\t\t");
scanf("%d", &today.day);
}
if (i==1)
printf("\n\n\tStarting Date:");
else
printf("\n\n\tEnding Date:");
switch(today.month)
{
case 1:
printf("January");
break;
case 2:
printf("February");
break;
case 3:
printf("March");
break;
case 4:
printf("April");
break;
case 5:
printf("May");
break;
case 6:
printf("June");
break;
case 7:
printf("July");
break;
case 8:
printf("August");
break;
case 9:

printf("September");
break;
case 10:
printf("October");
break;
case 11:
printf("November");
break;
case 12:
printf("December");
break;
}
printf(" %d, %d\n\n", today.day, today.year);
}
}
/* leave asks to confirm whether the user wants to leave the program or go back to
the main menu,
thanks the user if they choose to leave, and shows ASCII art of a shopping cart,
now with a product inside.
*/
void
leave()
{
char ans;
printf("\n\tAre you sure you want to exit the program? (Y/N):\t");
scanf(" %c", &ans);
while(ans!='Y'&&ans!='N'&&ans!='y'&&ans!='n')
{
printf("\n\tSorry that is an invalid input, please try again:\t");
scanf(" %c", &ans);
}
if (ans=='N'||ans=='n')
{
printf("\n\
n*******************************************************************************\n\
n");
choose();
}
else
{

printf("\n\tThank you for using this program! See you again soon!");
printf("\n\n\n\t\t\t\t
______
___\n\t\t\t\t__|_____|__.//\n\t\t\t\
t\\##########||\n\t\t\t\t \\#########||\n\t\t\t\t _________//\n\t\t\t\t (o)
(o)");
}
}
int main()
{
FILE *fp;
int file_size;
fp = fopen("Users.txt", "r");
fseek(fp, 0, SEEK_END);

file_size = ftell(fp);
if (file_size == 0)
{
fclose(fp);
fp = fopen("Users.txt", "w");
fprintf(fp,"0 0\n0\n0\n0\n\n");
}
fclose(fp);
fp = fopen("Items.txt", "r");
fseek(fp, 0, SEEK_END);
file_size = ftell(fp);
if (file_size == 0)
{
fclose(fp);
fp = fopen("Items.txt", "w");
fprintf(fp,"0 0\n0\n0\n0\n0 0.00\n\n");
}
fclose(fp);
welcome();
choose();
return 0;
}

