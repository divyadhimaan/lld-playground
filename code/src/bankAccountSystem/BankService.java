class BankService {

    private BankService(){}

    private static class BankServiceHolder {
        private static final BankService INSTANCE = new BankService();
    }

    public static BankService getInstance() {
        return BankServiceHolder.INSTANCE;
    }

    public void deposit(BankAccount account, double amount)
    {
        if(amount <= 0)
            throw new IllegalArgumentException("Deposit amount must be positive value");
        account.depositInternal(amount);
        System.out.println(amount + " deposited to " + account.getAccountNumber() + ". New balance: " + account.getBalance());
    }

    public void withdraw(BankAccount account, double amount){
        if(amount <=0)
            throw new IllegalArgumentException("Deposit amount must be positive value");
        if(account instanceof CheckingBankAccount && !((CheckingBankAccount) account).canWithdraw(amount))
            throw new IllegalArgumentException("OverDraft Limit Exceeded");
        if(account.getBalance() < amount)
            throw new IllegalArgumentException("Insufficient Funds");

        account.withdrawInternal(amount);
        System.out.println(amount + " withdrawn from " + account.getAccountNumber() + ". New balance: " + account.getBalance());
    }

    public void transfer(BankAccount fromAccount, BankAccount toAccount, double amount){
        if(fromAccount.equals(toAccount))
            throw new IllegalArgumentException("Both Accounts cannot be same.");

        withdraw(fromAccount, amount);
        deposit(toAccount, amount);

        fromAccount.transactionList.add(new Transaction("Transfer to " + fromAccount.getAccountNumber(), amount));
        toAccount.transactionList.add(new Transaction("Transfer from " + toAccount.getAccountNumber(), amount));
        System.out.println("Transferred " + amount + " from " + fromAccount.getAccountNumber() + " to " + toAccount.getAccountNumber());

    }
}
