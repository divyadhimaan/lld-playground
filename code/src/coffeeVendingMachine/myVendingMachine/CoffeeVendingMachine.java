package myVendingMachine;
import vendingMachine.VendingMachine;

import java.util.Scanner;

public class CoffeeVendingMachine {
    public static void main(String[] args){
        VendingMachine vm = VendingMachine.getInstance();

        vm.showMenu();

        Runnable task1 = () -> vm.makeCoffee("Espresso", 10);
        Runnable task2 = () -> vm.makeCoffee("Latte", 20);
        Runnable task3 = () -> vm.makeCoffee("Cappuccino", 15);
        Runnable task4 = () -> vm.makeCoffee("Invalid", 10);


        Thread user1 = new Thread(task1, "User-1 (Espresso)");
        Thread user2 = new Thread(task2, "User-2 (Latte)");
        Thread user3 = new Thread(task3, "User-3 (Cappuccino)");
        Thread user4 = new Thread(task4, "User-4 (Invalid)");

        user1.start();
        user2.start();
        user3.start();
        user4.start();
    }
}
