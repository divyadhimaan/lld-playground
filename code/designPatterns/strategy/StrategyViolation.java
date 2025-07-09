package strategy;

// VIOLATES Strategy Pattern - Uses conditional logic instead of polymorphism
class ShoppingCart2 {
    private String paymentMethod;

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void checkout(int amount) {
        if (paymentMethod == null) {
            throw new IllegalStateException("Payment method not set");
        }

        // VIOLATION: Using if-else chain instead of strategy objects
        if (paymentMethod.equals("CREDIT_CARD")) {
            System.out.println("Paid " + amount + " using Credit Card");
        } else if (paymentMethod.equals("PAYPAL")) {
            System.out.println("Paid " + amount + " using PayPal");
        } else if (paymentMethod.equals("BANK_TRANSFER")) {
            System.out.println("Paid " + amount + " using Bank Transfer");
        } else {
            throw new IllegalArgumentException("Unknown payment method: " + paymentMethod);
        }
    }
}

public class StrategyViolation {
    public static void main(String[] args) {
        ShoppingCart2 cart = new ShoppingCart2();
        cart.setPaymentMethod("CREDIT_CARD");
        cart.checkout(100); // Output: Paid 100 using Credit Card

        cart.setPaymentMethod("PAYPAL");
        cart.checkout(50); // Output: Paid 50 using PayPal
    }
}
