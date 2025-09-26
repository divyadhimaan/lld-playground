package vendingMachine;


import java.util.Map;

public class VendingMachine {
    private static VendingMachine vendingMachine;
    private final Inventory inventory;
    private PaymentService payment;


    public VendingMachine() {
        this.inventory = Inventory.getInstance();
        this.payment = PaymentService.getInstance();
        System.out.print("Vending Machine Initialized. ");
    }

    public static synchronized VendingMachine getInstance() {
        if (vendingMachine == null) {
            vendingMachine = new VendingMachine();
        }
        return vendingMachine;
    }

    public void showMenu() {
        System.out.println("Available coffees:");
        System.out.println("1. Espresso - $5");
        System.out.println("2. Cappuccino - $10");
        System.out.println("3. Latte - $20");
    }

    public void makeCoffee(String coffeeType, int amount) {
        String user = Thread.currentThread().getName();
        System.out.println("\n===== ☕ Request by " + user + " =====");


        CoffeeProduct coffee;
        try {
            coffee = CoffeeFactory.getCoffee(coffeeType);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid coffee type selected: " + coffeeType);
            System.out.println("Please choose from: Espresso, Cappuccino, or Latte.");
            return;
        }
        synchronized (inventory) {
            System.out.println("Making " + coffeeType + " for $" + coffee.getPrice());
            if (!inventory.checkIngredients(coffee.getIngredients())) {
                System.out.println("Insufficient ingredients for " + coffeeType + ". Please refill.");
                return;
            }

            if (amount < coffee.getPrice()) {
                System.out.println("Insufficient payment. Please pay at least $" + coffee.getPrice());
                return;
            }
            coffee.makeCoffee();
            payment.processPayment(amount, coffee.getPrice());
            inventory.useIngredients(coffee.getIngredients());

            coffee.makeCoffee();

            System.out.println("✅ Here is your freshly brewed " + coffee.getClass().getSimpleName() + "!");
            inventory.trackInventory(coffee.getIngredients());
        }

        System.out.println("===== ✅ Order complete for " + user + " =====\n");
    }

}


