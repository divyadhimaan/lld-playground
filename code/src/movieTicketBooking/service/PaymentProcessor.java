package service;

import model.Payment;
import strategy.CreditCardPayment;
import strategy.PaymentStrategy;
import strategy.UpiPayment;
import model.PaymentStatus;

import java.util.Scanner;

public class PaymentProcessor {
    public static Payment initiatePayment(Scanner scanner, double amount) {
        Payment payment = null;
        boolean paymentSuccess = false;
        while(!paymentSuccess){
            System.out.println("Select Payment Method:");
            System.out.println("1. UPI");
            System.out.println("2. Credit Card");
            System.out.print("Enter choice (1/2): ");
            String choice = scanner.nextLine();

            PaymentStrategy strategy;
            switch (choice) {
                case "1" -> {
                    System.out.print("Enter UPI ID: ");
                    strategy = new UpiPayment(scanner.nextLine());
                }
                case "2" -> {
                    System.out.print("Enter Card Number: ");
                    String cardNumber = scanner.nextLine();
                    System.out.print("Enter Holder Name: ");
                    String holder = scanner.nextLine();
                    System.out.print("Enter Expiry (MM/YY): ");
                    String expiry = scanner.nextLine();
                    System.out.print("Enter CVV: ");
                    String cvv = scanner.nextLine();
                    strategy = new CreditCardPayment(cardNumber, holder, expiry, cvv);
                }
                default -> {
                    System.out.println("Invalid payment option.");
                    continue;
                }
            }

            payment = new Payment(amount, strategy);
            payment.processPayment();

            if (payment.getStatus() == PaymentStatus.SUCCESS) {
                paymentSuccess = true;
                System.out.println("[INFO]: Payment successful!");
            } else {
                System.out.println("[ERROR]: Payment failed.");
                System.out.print("Would you like to retry payment? (yes/no): ");
                String retry = scanner.nextLine().trim().toLowerCase();

                if (!retry.equals("yes")) {
                    System.out.println("[INFO]: Payment cancelled by user.");
                    return payment;
                }
            }
        }
        return payment;
    }
}
