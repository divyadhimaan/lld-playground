package vendingMachine;

public interface MachineState {
    void insertMoney(Denomination type, double amount);
    void selectProduct(String productName);
    void dispenseProduct();
}
