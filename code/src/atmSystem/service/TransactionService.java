package service;

import model.enums.TransactionType;

public class TransactionService {

    void processTransaction(TransactionType transactionType, Double amount) {
        System.out.println("Processing transaction: " + transactionType + " Amount: " + amount);
    }
}
