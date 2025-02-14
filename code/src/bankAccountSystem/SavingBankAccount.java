class SavingBankAccount extends BankAccount {
    private static final double INTEREST_RATE = 0.03;

    public SavingBankAccount(String accountNumber, String owner, double initialBalance){
        super(accountNumber,owner,initialBalance);
    }

    public void applyInterest(){
        double interest = getBalance() * INTEREST_RATE;
        depositInternal(interest);
        System.out.println("Interest applied: "+ interest);
    }
}
