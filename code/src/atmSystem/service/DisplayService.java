package service;

public class DisplayService {

    /**
     * Displays a plain message to the user.
     */
    public void showMessage(String message) {
        System.out.println("\n" + message);
    }

    /**
     * Displays a set of menu options to the user with numbering.
     */
    public void displayOptions(String[] options) {
        System.out.println("\nPlease select an option:");
        for (int i = 0; i < options.length; i++) {
            System.out.printf("[%d] %s%n", i + 1, options[i]);
        }
    }

    /**
     * Displays an error message (like invalid PIN, insufficient funds, etc.)
     */
    public void showError(String errorMessage) {
        System.err.println("\nERROR: " + errorMessage);
    }

    /**
     * Displays confirmation or success messages (like transaction complete, cash dispensed, etc.)
     */
    public void showConfirmation(String confirmationMessage) {
        System.out.println("\nCONFIRMATION: " + confirmationMessage);
    }

    /**
     * Clears the display (for simulation, just print separator line)
     */
    public void clearScreen() {
        System.out.println("\n----------------------------------------");
    }
}
