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

    public addStock(Product product, )


}
