package vendingMachine;

public class DispensingProductState implements MachineState{
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
