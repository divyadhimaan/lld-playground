package vendingMachine;


import lombok.Setter;

import java.util.*;

// facade
public class MyVendingMachine {

    VendingMachineController controller;

    public MyVendingMachine(){
        controller = new VendingMachineController();
    }

    public void insertMoney(String paymentMode, double amount){
        switch(paymentMode){
            case "coin" -> controller.handlePayment(Denomination.COIN, amount);
            case "note" -> controller.handlePayment(Denomination.NOTE, amount);
            default -> throw new IllegalArgumentException("Invalid payment mode");
        }
    }

    public void selectProduct(String productName){
        System.out.println("Selected Product: "+productName);
        controller.selectProduct(productName);
    }

    public void dispenseProduct(){
        System.out.println("Dispensing Product...");
        controller.dispenseProduct();
    }

    public void showOptions(){
        controller.showOptions();
    }
}

class VendingMachineController {
    VendingInventory inventory;
    VendingPaymentService paymentService;
    MachineState machineState;
    private Product currentSelection;

    VendingMachineController(){
        inventory = VendingInventory.getInstance();
        paymentService = new VendingPaymentService();
        machineState = new MachineState();
    }


    // one product can be selected at a time
    void selectProduct(String productName){
        Product p = inventory.getProduct(productName);

        if(p==null){
            //call out of stock state
            throw new IllegalArgumentException("Product not found");
        }

        if(p.getQuantity() < 0){
            //call out of stock state
            throw new IllegalStateException("Out of stock");
        }

        this.currentSelection = p;
    }

    void handlePayment(Denomination type, double amount){
        switch(type) {
            case COIN -> paymentService.setStrategy(new CoinPaymentStrategy());
            case NOTE -> paymentService.setStrategy(new NotePaymentStrategy());
        }

        boolean success = paymentService.processPayment(amount, currentSelection.getProductPrice());
        if(success){
            dispenseProduct();
        } else {
            //TODO: handle state
//            state = new InsufficientFundsState(this);
            System.out.println("payment failed");
        }
    }

    void dispenseProduct(){
        if(currentSelection != null && currentSelection.getQuantity() > 0){
            inventory.dispense(currentSelection.getProductName());
            System.out.println("Dispensed: " + currentSelection.getProductName());
            collectMoney();
            currentSelection=null;
        }

    }

    void showOptions()
    {
        Map<String, Double> products = inventory.getAllProducts();
        System.out.println("Available Products: ");
        int idx=1;
        for(Map.Entry<String, Double> entry: products.entrySet()){
            System.out.println(idx++ + ". " + entry.getKey() + " - Price: " + entry.getValue());
        }
    }

    void restockProduct(){

    }

    void collectMoney(){

        double change = paymentService.returnChange(currentSelection.getProductPrice());
        System.out.println("User can collect the money: "+ change);
    }
}

class ProductFactory{
    public static Product createProduct(String productName){
        switch(productName.toLowerCase()) {
            case "coke": return new Product("coke", 20.0);
            case "pepsi": return new Product("pepsi", 25.0);
            case "water": return new Product("water", 10.0);
            default: throw new IllegalArgumentException("Invalid product: " + productName);
        }
    }
};

class Product {
    private final String productName;
    private final double productPrice;
    private int quantity;

    Product(String name, double price) {
        this.productName = name;
        this.productPrice = price;
    }

    String getProductName(){
        return this.productName;
    }

    double getProductPrice(){
        return this.productPrice;
    }

    int getQuantity(){
        return this.quantity;
    }

    void setQuantity(int quantity){
        this.quantity = this.quantity + quantity;
    }

    void decrementQuantity(){
        if(this.quantity > 0)
        {
            this.quantity = this.quantity-1;
        }
    }
}

interface Observer {
    void update(String productName, int quantity);
}

interface Subject{
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String productName, int quantity);
}

class VendingInventory implements Subject{

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

    public void addProduct(String productName, int quantity){
        Product product = ProductFactory.createProduct(productName);
        product.setQuantity(quantity);
        productsAvailable.put(productName, product);
        System.out.println("Adding "+ productName + " to inventory.");
        notifyObservers(productName, quantity);
    }

    public Product getProduct(String productName){
        if(productsAvailable.containsKey(productName)) {
            return productsAvailable.get(productName);
        }
        return null;
    }

    private void updateProductStock(String productName){
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

    public void dispense(String productName){
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

    // TODO: Add observer pattern to observe the stock

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
enum Denomination {
    COIN,
    NOTE
}

class VendingPaymentService{
    @Setter
    private PaymentStrategy strategy;
    double amountReceived;

    public boolean processPayment(double amount, double productPrice){
        if (amount < productPrice){
            System.out.println("Not enough funds");
            return false;
        }
        strategy.handlePayment(amount, productPrice);
        this.amountReceived = amount;
        return true;
    }

    public double returnChange(double productPrice){
        double change = amountReceived - productPrice;
        amountReceived = 0; // reset after returning change
        return change;
    }

}

interface PaymentStrategy {
    void handlePayment(double receivedAmount, double deductionAmount);
}

class NotePaymentStrategy implements PaymentStrategy{
    @Override
    public void handlePayment(double receivedAmount, double deductionAmount){
        System.out.println("Handling note payment. Deducting: " + deductionAmount);
    }
}

class CoinPaymentStrategy implements PaymentStrategy{
    @Override
    public void handlePayment(double receivedAmount, double deductionAmount){
        System.out.println("Handling coin payment. Deducting: " + deductionAmount);
    }
}

class RestockService implements Observer {
    @Override
    public void update(String productName, int quantity) {
        System.out.println("[RestockService] Product: " + productName + " new quantity: " + quantity);
    }
}

interface MachineState {
    void insertMoney(Denomination type, double amount);
    void selectProduct(String productName);
    void dispenseProduct();
}

class WaitingForMoney implements MachineState{

}



