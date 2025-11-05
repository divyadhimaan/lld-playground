package service;

public class BankService {

    Boolean validateUser(String cardNumber, String pin) {
        // Placeholder validation logic
        return cardNumber != null && cardNumber.length() == 16;
    }

    double getAccountBalance(String cardNumber) {
        // Placeholder balance retrieval logic
        return 5000.0;
    }

    void updateAccountBalance(String cardNumber, double newBalance) {
        // Placeholder balance update logic
    }

    void logTransaction(String cardNumber, String transactionType, double amount) {
        // Placeholder transaction logging logic
    }
}
