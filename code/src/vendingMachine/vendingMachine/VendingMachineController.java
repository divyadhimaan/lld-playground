package vendingMachine;

import vendingMachine.inventory.VendingInventory;
import vendingMachine.product.Product;
import vendingMachine.state.IdleState;
import vendingMachine.state.MachineState;
import vendingMachine.strategy.Denomination;
import vendingMachine.strategy.VendingPaymentService;

import java.util.Map;

public class VendingMachineController {
    private VendingInventory inventory;
    private ThreadLocal<MachineState> machineState = ThreadLocal.withInitial(() -> new IdleState(this));
    private ThreadLocal<Product> currentSelection;
    private ThreadLocal<VendingPaymentService> paymentService =
            ThreadLocal.withInitial(VendingPaymentService::new);

    VendingMachineController(){
        inventory = VendingInventory.getInstance();
    }

    public VendingInventory getInventory(){
        return inventory;
    }

    public void setState(MachineState state){
        machineState.set(state);
    }

    public MachineState getState(){
        return machineState.get();
    }

    public VendingPaymentService getPaymentService() {
        return paymentService.get();
    }

    // one product can be selected at a time
    void selectProduct(String productName){
        getState().selectProduct(productName);

    }

    void handlePayment(Denomination type, double amount){
        getState().insertMoney(type, amount);
    }

    public void dispenseProduct(){
        getState().dispenseProduct();

    }

    public void setCurrentSelected(Product selected){
        currentSelection.set(selected);
    }

    public Product getCurrentSelected(){
        return currentSelection.get();
    }

    public void showOptions()
    {
        Map<String, Double> products = inventory.getAllProducts();
        System.out.println("Available Products: ");
        int idx=1;
        for(Map.Entry<String, Double> entry: products.entrySet()){
            System.out.println(idx++ + ". " + entry.getKey() + " - Price: " + entry.getValue());
        }
    }


    public void collectMoney(){
        double change = getPaymentService().returnChange(getCurrentSelected().getProductPrice());
        System.out.println("User can collect the money: "+ change);
    }
}
