import java.util.*;

class Item2 {
    private String name;
    private double price;
    private int count;

    public Item2(String name, double price) {
        this.name = name;
        this.price = price;
        this.count = 1;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public void incrementCount() {
        count++;
    }
}

public class Main2 {
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
                    addItemToCart(carts.get("Grocery"), "GROCERY");
                    break;
                case 2:
                    addItemToCart(carts.get("Electronics"), "ELECTRONICS");
                    break;
                case 3:
                    addItemToCart(carts.get("Clothing"), "CLOTHING");
                    break;
                case 4:
                    placeOrder(carts);
                    break;
                case 5:
                    sc.close();
                    System.out.println("Exiting Shopping App");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        }
    }

    private static void addItemToCart(List<Item2> cart, String cartName) {
        String itemName = sc.next();
        double itemPrice = sc.nextDouble();

        Optional<Item2> existingItem = cart.stream()
                .filter(i -> i.getName().equalsIgnoreCase(itemName) && i.getPrice() == itemPrice)
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().incrementCount();
            System.out.println(itemName + " with Same Price Already Exists. Increased the Count.");
        } else {
            Item2 item2 = new Item2(itemName, itemPrice);
            cart.add(item2);
            System.out.println(itemName + " has been added to the cart " + cartName);
        }
    }

    private static double calculateTotal(List<Item2> cart) {
        return cart.stream().mapToDouble(item2 -> item2.getPrice() * item2.getCount()).sum();
    }

    private static void removeItemFromCart(List<Item2> cart, String itemName) {
        cart.removeIf(item2 -> item2.getName().equalsIgnoreCase(itemName));
        System.out.println(itemName + " has been removed from the cart");
    }

    private static void placeOrder(TreeMap<String, List<Item2>> carts) {
        while (true) {
            String cartName = sc.nextLine();
            List<Item2> selectedCart = carts.get(cartName);

            if (selectedCart == null || selectedCart.isEmpty()) {
                System.out.println("Can't Place Order: Cart is Either empty or Invalid Cart");
                System.out.println("Returning to Main Site: Continue Shopping");
                break;
            }

            double total = calculateTotal(selectedCart);
            System.out.println("Do you want to remove any product from the " + cartName + " Cart? (yes/no)");

            String remove = sc.nextLine().toLowerCase();
            if (remove.equals("yes")) {
                System.out.println("Enter the name of the product to remove:");
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
                System.out.println("- " + item2.getName() + " : Rs." + item2.getPrice() + "/- (Quantity: " + item2.getCount() + ")");
            }

            System.out.printf("\n%s CART - Total Bill (%d%% discount applied): Rs.%.2f/-\n", cartName.toUpperCase(), (int) (discount * 100), discountedTotal);
            System.out.println("\nOrder Placed!! Thank You!!!");

            selectedCart.clear();

            System.out.println("Do you want to Order from Other Carts? (yes/no)");
            String continueShopping = sc.nextLine().toLowerCase();

            if (continueShopping.equals("no")) {
                System.out.println("Returning to Main Site. Continue Shopping");
                break;
            }
        }
    }
}
