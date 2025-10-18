package strategy;

public class CreditCardPayment implements PaymentStrategy{

    private final String cardNumber;
    private final String cardHolderName;
    private final String expiryDate;
    private final String cvv;

    public CreditCardPayment(String cardNumber, String cardHolderName, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("Processing Credit Card payment of ₹" + amount + "...");
        return true;
    }
}
