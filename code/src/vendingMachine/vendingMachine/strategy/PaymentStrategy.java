package vendingMachine.strategy;

public interface PaymentStrategy {
    void handlePayment(double receivedAmount, double deductionAmount);
}
