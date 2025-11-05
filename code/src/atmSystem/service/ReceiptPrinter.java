package service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceiptPrinter {

    public void printReceipt(String receiptData) {
        System.out.println("\n================ RECEIPT ================");
        System.out.println("Date/Time: " + getCurrentTimestamp());
        System.out.println("----------------------------------------");
        System.out.println(receiptData);
        System.out.println("----------------------------------------");
        System.out.println("Thank you for using our ATM!");
        System.out.println("========================================\n");
    }
    private String getCurrentTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
