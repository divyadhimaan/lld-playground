public class BankApplication {
    public static void main(String[] args){

        BankService bankService = BankService.getInstance();

        SavingBankAccount savings = new SavingBankAccount("S1", "Divya", 1000);
        CheckingBankAccount checking = new CheckingBankAccount("C1", "Divya", 500);

        bankService.deposit(savings, 100);
        savings.applyInterest();
        bankService.withdraw(checking, 100);
        bankService.transfer(savings, checking, 50);

        System.out.println("Saving Account Balance: " + savings.getBalance());
        System.out.println("Checking Account Balance: " + checking.getBalance());

    }
}
