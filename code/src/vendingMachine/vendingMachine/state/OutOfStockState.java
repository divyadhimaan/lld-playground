package vendingMachine.state;

import vendingMachine.strategy.Denomination;
import vendingMachine.VendingMachineController;

public class OutOfStockState implements MachineState {
    private VendingMachineController controller;

    public OutOfStockState(VendingMachineController c){
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
