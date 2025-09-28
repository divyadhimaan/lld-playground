package vendingMachine;

import lombok.Setter;

public class VendingPaymentService{
    @Setter
    private PaymentStrategy strategy;
    double amountReceived;

    public boolean processPayment(double amount, double productPrice){
        if (amount < productPrice){
            System.out.println("Not enough funds");
            return false;
        }
        strategy.handlePayment(amount, productPrice);
        this.amountReceived = amount;
        return true;
    }

    public double returnChange(double productPrice){
        double change = amountReceived - productPrice;
        amountReceived = 0; // reset after returning change
        return change;
    }

}
