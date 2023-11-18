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
        TreeMap<String, List<Item>> carts = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        carts.put("Grocery", new ArrayList<>());
        carts.put("Electronics", new ArrayList<>());
        carts.put("Clothes", new ArrayList<>());

        System.out.println("Welcome to the Shopping App!");
        System.out.println("Available Carts: Grocery, Electronics, Clothes");
        System.out.println("Choose a Cart to Add Products:");

        while (true) {
            System.out.println("\n1. Grocery Cart");
            System.out.println("2. Electronics Cart");
            System.out.println("3. Clothes Cart");
            System.out.println("4. Place Order");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addProductToCart(carts.get("Grocery"));
                    break;
                case 2:
                    addProductToCart(carts.get("Electronics"));
                    break;
                case 3:
                    addProductToCart(carts.get("Clothes"));
                    break;
                case 4:
                    placeOrder(carts);
                    break;
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        }
    }

    private static void addProductToCart(List<Item> cart) {
        System.out.println("Enter the name of the product:");
        String itemName = sc.next();
        double itemPrice = sc.nextDouble();
        sc.nextLine();
        Item item = new Item(itemName, itemPrice);
        cart.add(item);
        System.out.println(itemName + " has been added to the cart");
    }

    private static double calculateTotal(List<Item> cart) {
        return cart.stream().mapToDouble(Item::getPrice).sum();
    }

    private static void removeItemFromCart(List<Item> cart, String itemName) {
        cart.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
        System.out.println(itemName + " has been removed from the cart");
    }

    private static void placeOrder(TreeMap<String, List<Item>> carts) {
        while (true) {
            System.out.println("\nEnter Cart Name to Place Order:");
            System.out.println("Grocery");
            System.out.println("Electronics");
            System.out.println("Clothes");
            String cartName = sc.nextLine();
            List<Item> selectedCart = carts.get(cartName);
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
            } else if (cartName.equalsIgnoreCase("Clothes")) {
                discount = 0.3;
            }
            double discountedTotal = total * (1 - discount);
            System.out.println("\nOrder Summary:");
            System.out.println("Items in the " + cartName + " Cart:");
            for (Item item : selectedCart) {
                System.out.println("- " + item.getName() + " : Rs." + item.getPrice() + "/-");
            }

            System.out.printf("\n%s Cart - Total Bill (10%% discount applied): Rs.%.2f/-\n", cartName, discountedTotal);
            System.out.println("\nOrder Placed!!Thank You!!!");
            System.out.println("Do you want to Order from Other Carts?");
            String continueShopping = sc.nextLine().toLowerCase();
            if (continueShopping.equals("no")) {
                System.out.println("Returning to Main Site. Continue Shopping");
                break;
            }
        }
    }
}
