import atm.ATMReserve;
import model.enums.Denomination;
import service.CashDispenserService;

import java.util.Map;

public class ATMSimulation {
    public static void main(String[] args) {
        // Initialize ATM Service
        // ATMService atmService = new ATMService();

        // Initialize ATM Interface
        // ATMInterface atmInterface = new ATMInterface(atmService);

        // Start the ATM Interface
        // atmInterface.start();
        ATMReserve reserve = new ATMReserve(Map.of(Denomination.FIVE_HUNDRED, 10,
                Denomination.TWO_THOUSAND, 5,
                Denomination.ONE_HUNDRED, 20,
                Denomination.TWO_HUNDRED, 10));
        CashDispenserService cashDispenserService = new CashDispenserService(reserve);
        cashDispenserService.dispenseCash(4200);
    }
}
