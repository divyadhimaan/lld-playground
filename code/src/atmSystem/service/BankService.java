package service;

public class BankService {

    Boolean validateUser(String cardNumber, String pin) {
        // Placeholder validation logic
        return cardNumber != null && cardNumber.length() == 16;
    }
}
