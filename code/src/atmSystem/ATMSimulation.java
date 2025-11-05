import service.CashDispenserService;

public class ATMSimulation {
    public static void main(String[] args) {
        // Initialize ATM Service
        // ATMService atmService = new ATMService();

        // Initialize ATM Interface
        // ATMInterface atmInterface = new ATMInterface(atmService);

        // Start the ATM Interface
        // atmInterface.start();

        CashDispenserService cashDispenserService = new CashDispenserService(true);
        cashDispenserService.dispenseCash(4200);
    }
}
