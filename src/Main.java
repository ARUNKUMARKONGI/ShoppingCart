/* Implement a Shopping Cart APP in java
-----------------------------------------------
Given the following class Item.java.
-------------------------------------
class Item {
    private String name;
    private double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
}

Create another class named as Solution.java. and implement Shopping Cart.

Assume there are Only 3 Possible Carts: GROCERY, ELECTRONICS AND CLOTHING

Discounts on each Cart should be applied:
GROCERY - 10% on overall Cart Bill.
CLOTHING- 20% on overall Cart Bill.
ELECTRONICS- 30% on overall Cart Bill.

Use following Menu Driven Approach:
------------------------------------
1. Grocery Cart
2. Electronics Cart
3. Clothing Cart
4. Place Order
5. Exit

Input format:
----------------
Enter the choice(1 to 3) for the cart on first line.
Enter the name of the product and price of the product on same line. (Name of the product without spaces)

choice 4 is for Placing the Order.
If the User enters Choice 4,
---------------------------
Another input should be read from the user indicating from which cart to place the order.
for example if the user enters "grocery", all the items in grocery cart should be considered.

Before Placing the order in each cart, ask the user if he/she wants to remove any existing item from the cart, (press yes/no)
If so, remove the item and then place the order after considering the overall discount for each cart as mentioned above.

After Placing the order from any cart, Display the message,
Order Placed!! Thank you!!
and in next line, display the following message,

Do you want to Order from Other Carts?

if user presses "yes" then continue ordering from other carts.
or else display the following message and go back to main site:

Returning to Main Site. Continue Shopping

Note:
If the user enters, invalid cart names "apart from grocery, electronics and clothing" or if the cart is empty,
display the following message and return to main site:

"Can't Place Order: Cart is Either empty or Invalid Cart"
"Returning to Main Site: Continue Shopping"

If the user presses 5 from main menu site then the program should terminate!!

Test Case Example:
-------------------
input=1
Milk 30
1
CurryLeaves 12.56
1
Bournville 79
2
BoatHeadphone 899
2
penDrive 399
4
grocery
no
continue
electronics
yes
pendrive
continue
clothing
5
output=
Welcome to the Shopping App!
Available Carts: Grocery, Electronics, Clothes
Choose a Cart to Add Products:

1. Grocery Cart
2. Electronics Cart
3. Clothing Cart
4. Place Order

Milk has been added to the cart GROCERY
CurryLeaves has been added to the cart GROCERY
Bournville has been added to the cart GROCERY
BoatHeadphone has been added to the cart ELECTRONICS
penDrive has been added to the cart ELECTRONICS

Order Summary:
Items in the grocery Cart:
- Milk : Rs.30.0/-
- CurryLeaves : Rs.12.56/-
- Bournville : Rs.79.0/-

GROCERY CART - Total Bill (10% discount applied): Rs.109.40/-

Order Placed!!Thank You!!!

pendrive has been removed from the cart

Order Summary:
Items in the electronics Cart:
- BoatHeadphone : Rs.899.0/-

ELECTRONICS CART - Total Bill (10% discount applied): Rs.719.20/-

Order Placed!!Thank You!!!

Can't Place Order: Cart is Either empty or Invalid Cart
Returning to Main Site: Continue Shopping

Exiting Shopping App


Test Case Example 2:
----------------------------
input=Welcome to the Shopping App!
Available Carts: Grocery, Electronics, Clothes
Choose a Cart to Add Products:

1. Grocery Cart
2. Electronics Cart
3. Clothing Cart
4. Place Order
1
 50
MILk has been added to the cart GROCERY
1
bread 456.4
bread has been added to the cart GROCERY
4
grocery
remove
milk
milk has been removed from the cart

Order Summary:
Items in the grocery Cart:
- bread : Rs.456.4/-

GROCERY CART - Total Bill (10% discount applied): Rs.410.76/-

Order Placed!!Thank You!!!

*/

import java.util.*;
class Item {
    private String name;
    private double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
}


public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        TreeMap<String, List<Item2>> carts = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        carts.put("Grocery", new ArrayList<>());
        carts.put("Electronics", new ArrayList<>());
        carts.put("Clothing", new ArrayList<>());

        System.out.println("Welcome to the Shopping App!");
        System.out.println("Available Carts: Grocery, Electronics, Clothes");
        System.out.println("Choose a Cart to Add Products:");
        System.out.println("\n1. Grocery Cart");
        System.out.println("2. Electronics Cart");
        System.out.println("3. Clothing Cart");
        System.out.println("4. Place Order");
        System.out.println("5. Exit\n");
        while (true) {
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    addItemToCart(carts.get("grocery"),"GROCERY");
                    break;
                case 2:
                    addItemToCart(carts.get("Electronics"),"ELECTRONICS");
                    break;
                case 3:
                    addItemToCart(carts.get("Clothing"),"CLOTHING");
                    break;
                case 4:
                    placeOrder(carts);
                    break;
                case 5:sc.close();
                    System.out.println("Exiting Shopping App");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        }
    }

    private static void addItemToCart(List<Item2> cart, String cartName) {
        // System.out.println("Enter the name of the product:");
        String itemName = sc.next();
        double itemPrice = sc.nextDouble();

        boolean itemExists = cart.stream().anyMatch(item -> item.getName().equalsIgnoreCase(itemName));
        if (itemExists) {
                System.out.println(itemName + " is already in the cart. Cannot add duplicates.");
                return;
                }
        Item2 item2 = new Item2(itemName, itemPrice);
        cart.add(item2);
        System.out.println(itemName + " has been added to the cart "+cartName);
    }

    private static double calculateTotal(List<Item2> cart) {
        return cart.stream().mapToDouble(Item2::getPrice).sum();
    }

    private static void removeItemFromCart(List<Item2> cart, String itemName) {
        cart.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
        System.out.println(itemName + " has been removed from the cart");
    }

    private static void placeOrder(TreeMap<String, List<Item2>> carts) {
        while (true) {
            //System.out.println("\nEnter Cart Name to Place Order:");
            /*System.out.println("Grocery");
            System.out.println("Electronics");
            System.out.println("Clothing");*/
            String cartName = sc.nextLine();
            List<Item2> selectedCart = carts.get(cartName);
            if (selectedCart == null || selectedCart.isEmpty()) {
                System.out.println("Can't Place Order: Cart is Either empty or Invalid Cart");
                System.out.println("Returning to Main Site: Continue Shopping");
                break;
            }
            double total = calculateTotal(selectedCart);
            // System.out.println("Do you want to remove any product from the " + cartName + " Cart? (remove/no)");
            String remove = sc.nextLine().toLowerCase();
            if (remove.equals("remove")) {
                // System.out.println("Enter the name of the product to remove:");
                String itemToRemove = sc.nextLine();
                removeItemFromCart(selectedCart, itemToRemove);
                total = calculateTotal(selectedCart);
            }
            double discount = 0.0;
            if (cartName.equalsIgnoreCase("Grocery")) {
                discount = 0.1;
            } else if (cartName.equalsIgnoreCase("Electronics")) {
                discount = 0.2;
            } else if (cartName.equalsIgnoreCase("Clothing")) {
                discount = 0.3;
            }
            double discountedTotal = total * (1 - discount);
            System.out.println("\nOrder Summary:");

            System.out.println("Items in the " + cartName + " Cart:");
            for (Item2 item2 : selectedCart) {
                System.out.println("- " + item2.getName() + " : Rs." + item2.getPrice() + "/-");
            }
            // selectedCart.stream().forEach(item -> System.out.println("- "+ item.getName()+" "+ item.getPrice()));

            System.out.printf("\n%s CART - Total Bill (%d%% discount applied): Rs.%.2f/-\n", cartName.toUpperCase(), (int)(discount * 100), discountedTotal);
            System.out.println("\nOrder Placed!!Thank You!!!");
            selectedCart.clear();
            //System.out.println("Do you want to Order from Other Carts? (continue/no)");
            String continueShopping = sc.nextLine().toLowerCase();
            if (continueShopping.equals("no")) {
                System.out.println("Returning to Main Site. Continue Shopping");
                break;
            }
        }
    }
}
