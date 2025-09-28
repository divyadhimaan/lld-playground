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

