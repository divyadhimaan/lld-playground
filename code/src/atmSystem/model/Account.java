package model;

import lombok.Getter;
import model.enums.AccountType;

public class Account {
    private final String accountNumber;
    @Getter
    private double balance;
    private AccountType accountType;

    Account(String accountNumber, AccountType accountType){
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.accountType = accountType;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false;
        }
        this.balance -= amount;
        return true;
    }

}
