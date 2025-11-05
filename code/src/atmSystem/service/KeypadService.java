package service;

import java.util.Scanner;

public class KeypadService {
    private final Scanner scanner = new Scanner(System.in);


    /**
     * Reads numeric input from the keypad (for PIN, amount, or menu options)
     */
    public int getNumericInput(String prompt) {
        System.out.println(prompt + ": ");

        while(!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter again:");
            scanner.next(); // clear invalid input
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    /**
     * Reads PIN input from the keypad (4 Digit numeric)
     */
    public String getPinInput() {
        System.out.println("Enter 4-digit PIN: ");
        String pin = scanner.nextLine().trim();
        while(!pin.matches("\\d{4}")){
            System.out.println("Invalid PIN format. Please enter a 4-digit PIN:");
            pin = scanner.nextLine().trim();
        }
        return pin;
    }

    /**
     * Simulates pressing a function key: ENTER, CLEAR, CANCEL
     */
    public String getFunctionKey(String prompt) {
        System.out.print(prompt + " (Enter/Clear/Cancel): ");
        String key = scanner.nextLine().trim().toUpperCase();

        while (!key.matches("ENTER|CLEAR|CANCEL")) {
            System.out.print("Invalid key. Please press Enter, Clear, or Cancel: ");
            key = scanner.nextLine().trim().toUpperCase();
        }
        return key;
    }
}
