package vendingMachine.inventory;

import vendingMachine.product.Product;
import vendingMachine.product.ProductFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendingInventory implements Subject{

    private static VendingInventory inventoryInstance;
    private final Map<String, Product> productsAvailable;
    private final List<Observer> observers;

    private VendingInventory(){
        this.productsAvailable = new HashMap<>();
        this.observers = new ArrayList<>();
        addProduct("coke", 2);
        addProduct("water", 10);
        observers.add(new RestockService());
    }

    public static synchronized VendingInventory getInstance()
    {
        if(inventoryInstance == null){
            inventoryInstance = new VendingInventory();
        }
        return inventoryInstance;
    }

    public synchronized void addProduct(String productName, int quantity){
        Product product = ProductFactory.createProduct(productName);
        product.setQuantity(quantity);
        productsAvailable.put(productName, product);
        System.out.println("Adding "+ productName + " to inventory.");
        notifyObservers(productName, quantity);
    }

    public synchronized Product getProduct(String productName){
        if(productsAvailable.containsKey(productName)) {
            return productsAvailable.get(productName);
        }
        return null;
    }

    private synchronized void updateProductStock(String productName){
        System.out.println("Updating Stock...");
        getProduct(productName).decrementQuantity();
        System.out.println("Quantity Left: " + getProduct(productName).getQuantity());
    }

    private boolean checkAvailability(String productName){
        int currentQuantity = getProduct(productName).getQuantity();
        if(currentQuantity > 0) {
            System.out.println("Available stock for product " + productName + ": " + currentQuantity);
            return true;
        }
        return false;
    }

    public synchronized void dispense(String productName){
        if(!checkAvailability(productName)){
            throw new IllegalArgumentException("Product not in Stock");
        }
        updateProductStock(productName);
        notifyObservers(productName, getProduct(productName).getQuantity());
    }

    public Map<String, Double> getAllProducts() {
        Map<String, Double> productPriceMap = new HashMap<>();
        for (Map.Entry<String, Product> entry : productsAvailable.entrySet()) {
            productPriceMap.put(entry.getKey(), entry.getValue().getProductPrice());
        }
        return productPriceMap;
    }

    @Override
    public void addObserver(Observer observer){
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String productName, int quantity){
        System.out.println("Notifying observer(s)");
        for(Observer ob: observers){
            ob.update(productName,quantity );
        }
    }


}
