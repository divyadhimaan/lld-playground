import java.util.List;

interface IBankAccount {
    String getAccountNumber();
    String getOwner();
    double getBalance();
    List<Transaction> getTransactionHistory();
}
