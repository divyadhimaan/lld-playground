package vendingMachine;import java.util.Map;

public interface CoffeeProduct {
    void makeCoffee();

    int getPrice();

    Map<String, Integer> getIngredients();
}
