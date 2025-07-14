package vendingMachine;

public class CoffeeFactory {
    public static CoffeeProduct getCoffee(String type) {
        return switch (type.toLowerCase()) {
            case "espresso" -> new Espresso();
            case "cappuccino" -> new Cappuccino();
            case "latte" -> new Latte();
            default -> throw new IllegalArgumentException("Unknown coffee type: " + type);
        };
    }
}
