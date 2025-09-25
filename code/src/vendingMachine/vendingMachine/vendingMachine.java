package vendingMachine;


import java.util.HashMap;
import java.util.Map;

// facade
public class vendingMachine {

    VendingMachineController controller;



    void insertMoney();

    void selectProduct();

    void dispenseProduct();

    void restock();
}

class VendingMachineController {
    VendingInventory inventory;
    VendingPaymentService paymentService;
    MachineState machineState;
}

class Product {
    private final String productName;
    private final int productPrice;


    Product(String name, int price) {
        this.productName = name;
        this.productPrice = price;
    }

    String getProductName(){
        return this.productName;
    }

    int getProductPrice(){
        return this.productPrice;
    }
}

class VendingInventory{

    private static VendingInventory inventoryInstance;
    private Map<Product, Integer> productStock;

    private VendingInventory(){
        this.productStock = new HashMap<>();
    }

    public static synchronized VendingInventory getInstance()
    {
        if(inventoryInstance == null){
            inventoryInstance = new VendingInventory();
        }
        return inventoryInstance;
    }

    public void addProduct(Product product, int quantity){
        int currentStock = productStock.getOrDefault(product, 0);
        productStock.put(product, currentStock + quantity);
    }

    public void removeStock(Product product, int quantity){
        int currentStock = productStock.get(product);
        if(checkAvailability(product) || currentStock - quantity < 0) {
            System.out.println("There was no stock for the selected item: " + product.getProductName() + ". Machine misbehaving");
            // TODO: go to invalid state
        }
        productStock.put(product, currentStock - quantity);
    }

    public boolean checkAvailability(Product product){
        int currentStock = productStock.getOrDefault(product, 0);
        if(currentStock==0){
            System.out.println("There is no stock for the selected item: " + product.getProductName() + ".");
            return false;
        }
        return true;
    }

    public boolean checkEnoughSupply(Product product, int quantity){
        int currentStock = productStock.getOrDefault(product, 0);
        if(currentStock - quantity < 0){
            System.out.println("Not Enough Stock for product: " + product.getProductName());
            if(currentStock > 0)
                System.out.println("Quantity available: " + currentStock);
            return false;
        }
        return true;
    }


}

class VendingPaymentService{

}

class MachineState {

}
