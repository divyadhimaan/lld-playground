import java.util.ArrayList;
import java.util.List;

abstract class BankAccount implements IBankAccount{
    private String accountNumber;
    private String owner;
    private double balance;
    List<Transaction> transactionList;

    public BankAccount(String accountNumber, String owner, double initialBalance)
    {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = initialBalance;
        this.transactionList = new ArrayList<>();
    }

    void depositInternal(double depositAmount){
        balance = balance + depositAmount;
        transactionList.add(new Transaction("Deposit", depositAmount));
    }

    void withdrawInternal(double withdrawAmount){
        balance = balance - withdrawAmount;
        transactionList.add(new Transaction("Withdraw", withdrawAmount));
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public List<Transaction> getTransactionHistory() {
        return transactionList;
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public String getOwner() {
        return owner;
    }
}
