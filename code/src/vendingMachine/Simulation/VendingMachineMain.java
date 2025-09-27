package Simulation;

import vendingMachine.MyVendingMachine;

import java.util.Scanner;

public class VendingMachineMain {
    public static void main(String[] args){
        MyVendingMachine vendingMachine= new MyVendingMachine();
        vendingMachine.showOptions();

        vendingMachine.selectProduct("coke");


        Scanner scanner = new Scanner(System.in);
        System.out.println("Select Payment mode: (note/coin)");
        String paymentMode = scanner.nextLine();

        System.out.println("Enter amount: ");
        double paymentAmount = Double.parseDouble(scanner.nextLine());

        vendingMachine.insertMoney(paymentMode, paymentAmount);


        vendingMachine.selectProduct("coke");


        System.out.println("Select Payment mode: (note/coin)");
        String paymentMode2 = scanner.nextLine();

        System.out.println("Enter amount: ");
        double paymentAmount2 = Double.parseDouble(scanner.nextLine());

        vendingMachine.insertMoney(paymentMode2, paymentAmount2);

    }
}
