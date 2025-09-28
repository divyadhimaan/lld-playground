package vendingMachine;

public class CoinPaymentStrategy implements PaymentStrategy{
    @Override
    public void handlePayment(double receivedAmount, double deductionAmount){
        System.out.println("Handling coin payment. Deducting: " + deductionAmount);
    }
}
