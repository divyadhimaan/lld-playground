package vendingMachine.inventory;

public class RestockService implements Observer {
    @Override
    public void update(String productName, int quantity) {
        System.out.println("[RestockService] Product: " + productName + " new quantity: " + quantity);

        if(quantity == 0){
            System.out.println("[RestockService] Restock for " + productName + " needed!! ");
        }
    }
}
