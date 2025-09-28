package vendingMachine;

public class ProductFactory{
    public static Product createProduct(String productName){
        switch(productName.toLowerCase()) {
            case "coke": return new Product("coke", 20.0);
            case "pepsi": return new Product("pepsi", 25.0);
            case "water": return new Product("water", 10.0);
            default: throw new IllegalArgumentException("Invalid product: " + productName);
        }
    }
}
