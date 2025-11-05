package atm;

import model.enums.TransactionType;

public interface ATMState {
    public void insertCard();
    public void ejectCard();
    public void enterPin(int pin);
    public void SelectTransaction(TransactionType transactionType);
    public void performTransaction(double amount);
    public void displayBalance();
    public void cancelTransaction();
}

//public class IdleState implements ATMState {
//    private ATM atmInstance;
//
//    IdleState(ATM atm) {
//        this.atmInstance = atm;
//    }
//
//    @Override
//    public void insertCard() {
////        System.out.println("Card inserted. Please enter your PIN.");
////        atmInstance.setCurrentState(atmInstance.getHasCardState());
//    }
//
//}