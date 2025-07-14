package vendingMachine;

import java.util.HashMap;
import java.util.Map;

class Latte implements CoffeeProduct {
    int price;
    Map<String, Integer> ingredients = new HashMap<>();

    Latte() {
        price = 20;
        ingredients.put("coffeeShot", 1);
        ingredients.put("Milk", 1);
        ingredients.put("Water", 1);
    }

    @Override
    public void makeCoffee() {
        System.out.println("Making Latte...");
    }

    @Override
    public Map<String, Integer> getIngredients() {
        return ingredients;
    }

    @Override
    public int getPrice() {
        return price;
    }
}
