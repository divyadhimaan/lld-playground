package vendingMachine.state;

import vendingMachine.strategy.Denomination;
import vendingMachine.VendingMachineController;
import vendingMachine.strategy.CoinPaymentStrategy;
import vendingMachine.strategy.NotePaymentStrategy;

public class WaitingForMoneyState implements MachineState {
    private VendingMachineController controller;

    public WaitingForMoneyState(VendingMachineController c){
        this.controller = c;
    }

    @Override
    public void insertMoney(Denomination type, double amount){
        System.out.println("In waiting for Money State");
        switch(type) {
            case COIN -> controller.getPaymentService().setStrategy(new CoinPaymentStrategy());
            case NOTE -> controller.getPaymentService().setStrategy(new NotePaymentStrategy());
        }

        boolean success = controller.getPaymentService().processPayment(amount, controller.getCurrentSelected().getProductPrice());
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
