package vendingMachine.inventory;

public interface Subject{
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String productName, int quantity);
}
