package vendingMachine;


import java.util.HashMap;

// facade
public class vendingMachine {

    VendingMachineController controller;
    VendingInventory inventory;


    void insertMoney();

    void selectProduct();

    void dispenseProduct();

    void restock();
}

class Product {
    String name;



    Product() {

    }
}

class VendingInventory{

    private static VendingInventory inventoryInstance;
    Map<Product, quantity> productquantityMap =  new HashMap<>();

    public static synchronized VendingInventory getInstance()
    {
        if(inventoryInstance == null){
            inventoryInstance = new VendingInventory();
        }
        return inventoryInstance;
    }
}
