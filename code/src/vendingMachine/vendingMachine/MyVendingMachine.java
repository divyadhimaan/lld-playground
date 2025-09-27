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
        this.machineState = new IdleState(this);
    }

    public void setState(MachineState state){
        this.machineState = state;
    }


    // one product can be selected at a time
    void selectProduct(String productName){
        machineState.selectProduct(productName);

    }

    void handlePayment(Denomination type, double amount){
        machineState.insertMoney(type, amount);
    }

    void dispenseProduct(){
        machineState.dispenseProduct();

    }

    void setCurrentSelected(Product selected){
        this.currentSelection = selected;
    }

    Product getCurrentSelected(){
        return this.currentSelection;
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

        if(quantity == 0){
            System.out.println("[RestockService] Restock for " + productName + " needed!! ");
        }
    }
}

interface MachineState {
    void insertMoney(Denomination type, double amount);
    void selectProduct(String productName);
    void dispenseProduct();
}

class IdleState implements MachineState {
    private VendingMachineController controller;

    IdleState(VendingMachineController controller) {
        this.controller = controller;
    }

    @Override
    public void selectProduct(String productName){
        Product product = controller.inventory.getProduct(productName);
        if (product == null || product.getQuantity() <= 0) {
            System.out.println("Product not available.");
            controller.setState(new OutOfStockState(controller));
            return;
        }
        controller.setCurrentSelected(product);
        System.out.println("Product " + product.getProductName() + " selected. Price: " + product.getProductPrice());
        controller.setState(new WaitingForMoneyState(controller));

    }

    @Override
    public void insertMoney(Denomination type, double amount){
        System.out.println("Please select a product first.");
    }

    @Override
    public void dispenseProduct() {
        System.out.println("No product selected yet.");
    }
}

class WaitingForMoneyState implements MachineState{
    private VendingMachineController controller;

    WaitingForMoneyState(VendingMachineController c){
        this.controller = c;
    }

    @Override
    public void insertMoney(Denomination type, double amount){
        System.out.println("In waiting for Money State");
        switch(type) {
            case COIN -> controller.paymentService.setStrategy(new CoinPaymentStrategy());
            case NOTE -> controller.paymentService.setStrategy(new NotePaymentStrategy());
        }

        boolean success = controller.paymentService.processPayment(amount, controller.getCurrentSelected().getProductPrice());
        if(success){
            controller.setState(new DispensingProductState(controller));
            controller.dispenseProduct();
        } else {
            controller.setState(new InsufficientFundsState(controller));
            System.out.println("payment failed");
        }
    }
    @Override
    public void selectProduct(String productName){
        System.out.println("Product already selected. Please insert money.");
    }
    @Override
    public void dispenseProduct()
    {
        System.out.println("Cannot dispense. Waiting for money.");
    }
}

class DispensingProductState implements MachineState{
    private VendingMachineController controller;


    DispensingProductState(VendingMachineController c){
        this.controller = c;
    }

    @Override
    public void insertMoney(Denomination type, double amount){
        System.out.println("Dispensing in progress. Cannot insert money now.");
    }
    @Override
    public void selectProduct(String productName){
        System.out.println("Dispensing in progress. Cannot select product now.");
    }
    @Override
    public void dispenseProduct(){
        if(controller.getCurrentSelected() != null && controller.getCurrentSelected().getQuantity() > 0){
            controller.inventory.dispense(controller.getCurrentSelected().getProductName());
            System.out.println("Dispensed: " + controller.getCurrentSelected().getProductName());
            controller.collectMoney();
            controller.setCurrentSelected(null);
        }
        controller.setState(new IdleState(controller));
    }
}

class OutOfStockState implements MachineState{
    private VendingMachineController controller;

    OutOfStockState(VendingMachineController c){
        this.controller = c;
    }

    @Override
    public void insertMoney(Denomination type, double amount){
        System.out.println("Product out of stock. Cannot accept money.");
    }
    @Override
    public void selectProduct(String productName){
        System.out.println("Product out of stock.");
    }
    @Override
    public void dispenseProduct(){
        System.out.println("Product out of stock.");
    }
}

class InsufficientFundsState implements MachineState{
    private VendingMachineController controller;

    InsufficientFundsState(VendingMachineController c){
        this.controller = c;
    }

    @Override
    public void insertMoney(Denomination type, double amount){
        System.out.println("Insufficient funds. Insert more money.");

    }
    @Override
    public void selectProduct(String productName){
        System.out.println("Insert money first.");
    }
    @Override
    public void dispenseProduct(){
        System.out.println("Cannot dispense. Insufficient funds.");
    }
}



