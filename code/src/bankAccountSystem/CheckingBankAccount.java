class CheckingBankAccount extends BankAccount{
    private static final double OVERDRAFT_LIMIT = 500;
    public CheckingBankAccount(String accountNumber, String owner, double initialBalance )
    {
        super(accountNumber,owner,initialBalance);
    }

    public boolean canWithdraw(double amount){
        return (getBalance() + OVERDRAFT_LIMIT) >= amount;
    }
}
