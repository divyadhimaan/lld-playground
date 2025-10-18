package strategy;

public class UpiPayment implements PaymentStrategy{
    private final String upiId;

    public UpiPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("Processing UPI payment of ₹" + amount + " via " + upiId + "...");
        return true;
    }
}
