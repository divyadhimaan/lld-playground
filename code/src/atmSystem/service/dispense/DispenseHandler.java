package service.dispense;

public interface DispenseHandler {
    void setNextHandler(DispenseHandler next);
    void dispense(int amount);
}
