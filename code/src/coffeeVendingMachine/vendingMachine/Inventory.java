package vendingMachine;

import java.util.HashMap;
import java.util.Map;

class Inventory {
    private static final int LOW_STOCK_THRESHOLD = 2;
    private static Inventory inventory;
    private final Map<String, Integer> ingredients = new HashMap<>();

    public static synchronized Inventory getInstance() {
        if (inventory == null) {
            inventory = new Inventory();
        }
        return inventory;
    }

    public Inventory() {
        ingredients.put("coffeeShot", 10);
        ingredients.put("Milk", 3);
        ingredients.put("Water", 10);
    }

    public boolean checkIngredients(Map<String, Integer> requiredIngredients) {
        for (Map.Entry<String, Integer> entry : requiredIngredients.entrySet()) {
            if (ingredients.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public void useIngredients(Map<String, Integer> usedIngredients) {


        for (Map.Entry<String, Integer> entry : usedIngredients.entrySet()) {
            String ingredient = entry.getKey();
            int currentStock = ingredients.get(ingredient);
            int newStock = currentStock - entry.getValue();
            ingredients.put(ingredient, newStock);

            if (newStock <= LOW_STOCK_THRESHOLD) {
                System.out.println("⚠️ Low stock warning: " + ingredient + " is low (" + newStock + " left). Please refill soon.");
            }
        }

        trackInventory(usedIngredients);
    }

    public void addIngredients(String ingredient, int quantity) {
        ingredients.put(ingredient, ingredients.getOrDefault(ingredient, 0) + quantity);
    }

    public void refill() {
        ingredients.put("coffeeShot", 10);
        ingredients.put("Milk", 10);
        ingredients.put("Water", 10);
    }

    public void trackInventory(Map<String, Integer> ingredientsUsed) {


        System.out.println("📦 Inventory usage by [" + Thread.currentThread().getName() + "]:");

        synchronized (this) {
            for (Map.Entry<String, Integer> entry : ingredientsUsed.entrySet()) {
                String ingredient = entry.getKey();
                int used = entry.getValue();
                int remaining = ingredients.getOrDefault(ingredient, 0);

                System.out.printf(" - %s: Used %d, Remaining %d\n", ingredient, used, remaining);

                if (remaining <= LOW_STOCK_THRESHOLD) {
                    System.out.printf(" ⚠️  Low stock: %s is below threshold (%d left)\n", ingredient, remaining);
                }
            }
        }
    }
}
