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

    private static void addItemToCart(List<Item> cart, String cartName) {
        // System.out.println("Enter the name of the product:");
        String itemName = sc.next();
        double itemPrice = sc.nextDouble();
        sc.nextLine();
        Item item = new Item(itemName, itemPrice);
        cart.add(item);
        System.out.println(itemName + " has been added to the cart "+cartName);
    }

    private static double calculateTotal(List<Item> cart) {
        return cart.stream().mapToDouble(Item::getPrice).sum();
    }
    /*private static double calculateTotal(List<Item> cart) {
        double total = 0.0;
        for (Item item : cart) {
            total += item.getPrice();
        }
        return total;
    }*/
    private static void removeItemFromCart(List<Item> cart, String itemName) {
        cart.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
        System.out.println(itemName + " has been removed from the cart");
    }

    private static void placeOrder(TreeMap<String, List<Item>> carts) {
        while (true) {
            //System.out.println("\nEnter Cart Name to Place Order:");
            /*System.out.println("Grocery");
            System.out.println("Electronics");
            System.out.println("Clothing");*/
            String cartName = sc.nextLine();
            List<Item> selectedCart = carts.get(cartName);
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
            for (Item item : selectedCart)
                System.out.println("- " + item.getName() + " : Rs." + item.getPrice() + "/-");

           // selectedCart.stream().forEach(item -> System.out.println("- "+ item.getName()+" "+ item.getPrice()));

            System.out.printf("\n%s CART - Total Bill (%d%% discount applied): Rs.%.2f/-\n", cartName.toUpperCase(), (int)(discount * 100), discountedTotal);

            System.out.println("\nOrder Placed!!Thank You!!!");
            //System.out.println("Do you want to Order from Other Carts? (continue/no)");

            selectedCart.clear(); //clear the cart after the order is placed.

            String flag = sc.nextLine().toLowerCase();
            if (flag.equals("no")) {
                System.out.println("Returning to Main Site. Continue Shopping");
                break;
            }
        }
    }
}

