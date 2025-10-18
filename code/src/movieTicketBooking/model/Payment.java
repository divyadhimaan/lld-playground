package model;


import lombok.Getter;
import lombok.Setter;
import strategy.PaymentStrategy;

import java.util.UUID;

@Getter
public class Payment {
    private final String paymentId;
    private final PaymentStrategy strategy;
    private final double amount;
    @Setter
    private PaymentStatus status;

    public Payment(double amount, PaymentStrategy strategy){
        this.paymentId = 'p' + String.valueOf(UUID.randomUUID());
        this.amount = amount;
        this.strategy = strategy;
        this.status = PaymentStatus.PENDING;
    }

    public void processPayment(){
        System.out.println("Initiating Payment");
        status = strategy.pay(amount) ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;
    }

    public void displayPaymentInfo() {
        System.out.println("===== Payment Details =====");
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Amount: ₹" + amount);
        System.out.println("Method: " + strategy.getClass().getSimpleName());
        System.out.println("Status: " + status);
        System.out.println("===========================");
    }
}
