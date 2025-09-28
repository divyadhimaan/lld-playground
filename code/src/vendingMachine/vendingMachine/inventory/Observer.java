package vendingMachine.inventory;

public interface Observer {
    void update(String productName, int quantity);
}
