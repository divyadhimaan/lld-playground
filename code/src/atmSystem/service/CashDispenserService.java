package service;

import atm.ATMReserve;
import model.enums.Denomination;
import service.dispense.DispenseHandler;
import service.dispense.RupeeDispenser;

import java.util.Map;

public class CashDispenserService {
    private final DispenseHandler chain;
    private final ATMReserve reserve;

    public CashDispenserService(ATMReserve reserve) {
        this.reserve = reserve;

        DispenseHandler d2000 = new RupeeDispenser(Denomination.TWO_THOUSAND, reserve);
        DispenseHandler d500 = new RupeeDispenser(Denomination.FIVE_HUNDRED, reserve);
        DispenseHandler d200 = new RupeeDispenser(Denomination.TWO_HUNDRED, reserve);
        DispenseHandler d100 = new RupeeDispenser(Denomination.ONE_HUNDRED, reserve);

        d2000.setNextHandler(d500);
        d500.setNextHandler(d200);
        d200.setNextHandler(d100);

        this.chain = d2000;

    }
    public void dispenseCash(int amount) {
        System.out.println("\n--- Dispensing ₹" + amount + " ---");

        // Try to plan first
        Map<Denomination, Integer> plan = reserve.planDispense(amount, Denomination.TWO_THOUSAND);
        if (plan == null) {
            System.out.println("❌ Cannot dispense ₹" + amount + ". Insufficient denominations.");
            return;
        }
        if (!checkCashAvailability(amount)) {
            System.out.println("❌ ATM has insufficient funds for ₹" + amount);
            return;
        }
        // Commit the plan
        reserve.commitDispense(plan);

        // Physically dispense (simulate)
        chain.dispense(amount);


        System.out.println("--- Dispensing complete ---");
    }
    public boolean checkCashAvailability(int amount) {
        return amount <= reserve.getTotalCash();
    }
}
