package vendingMachine;

public interface PaymentStrategy {
    void handlePayment(double receivedAmount, double deductionAmount);
}
