package vendingMachine;

import java.util.HashMap;
import java.util.Map;

class Espresso implements CoffeeProduct {
    int price;
    Map<String, Integer> ingredients = new HashMap<>();

    Espresso() {
        price = 5;
        ingredients.put("coffeeShot", 2);
        ingredients.put("Water", 1);
    }

    @Override
    public void makeCoffee() {
        System.out.println("Making Espresso...");
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
