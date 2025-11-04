package service;

public class DisplayService {
    void showMessage(String message) {
        System.out.println(message);
    }
    void displayOptions(String[] options) {
        for (String option : options) {
            System.out.println(option);
        }
    }
}
