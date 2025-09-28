package vendingMachine;

public class NotePaymentStrategy implements PaymentStrategy{
    @Override
    public void handlePayment(double receivedAmount, double deductionAmount){
        System.out.println("Handling note payment. Deducting: " + deductionAmount);
    }
}
