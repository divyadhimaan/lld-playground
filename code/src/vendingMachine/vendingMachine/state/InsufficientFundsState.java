package vendingMachine.state;

import vendingMachine.strategy.Denomination;
import vendingMachine.VendingMachineController;

public class InsufficientFundsState implements MachineState {
    private VendingMachineController controller;

    public InsufficientFundsState(VendingMachineController c){
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
