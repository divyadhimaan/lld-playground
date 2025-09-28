package vendingMachine;

public class IdleState implements MachineState {
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
