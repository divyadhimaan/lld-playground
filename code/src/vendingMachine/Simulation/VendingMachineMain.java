package Simulation;

import vendingMachine.MyVendingMachine;

import java.util.Scanner;

public class VendingMachineMain {
    public static void main(String[] args){
        MyVendingMachine vendingMachine= new MyVendingMachine();
//        vendingMachine.a
        vendingMachine.showOptions();

        vendingMachine.selectProduct("coke");


        Scanner scanner = new Scanner(System.in);
        System.out.println("Select Payment mode: (note/coin)");
        String paymentMode = scanner.nextLine();

        System.out.println("Enter amount: ");
        double paymentAmount = Double.parseDouble(scanner.nextLine());

        vendingMachine.insertMoney(paymentMode, paymentAmount);

    }
}
