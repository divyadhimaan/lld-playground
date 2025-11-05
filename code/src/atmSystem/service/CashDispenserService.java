package service;

import service.dispense.DispenseHandler;
import service.dispense.RupeeDispenser;
import service.dispense.strategy.DispenseStrategy;
import service.dispense.strategy.GreedyDispenseStrategy;
import service.dispense.strategy.MixedDispenseStrategy;

public class CashDispenserService {
    private final DispenseStrategy strategy;

    public CashDispenserService(Boolean allowChangeMode) {
        DispenseHandler d2000 = new RupeeDispenser(2000);
        DispenseHandler d500 = new RupeeDispenser(500);
        DispenseHandler d200 = new RupeeDispenser(200);
        DispenseHandler d100 = new RupeeDispenser(100);

        d2000.setNextHandler(d500);
        d500.setNextHandler(d200);
        d200.setNextHandler(d100);

        if(allowChangeMode){
            this.strategy = new MixedDispenseStrategy(d2000);
        }else{
            this.strategy = new GreedyDispenseStrategy(d2000);
        }

    }
    public void dispenseCash(int amount) {
        System.out.println("\n--- Dispensing ₹" + amount + " ---");
        strategy.dispenseAmount(amount);
        System.out.println("--- Dispensing complete ---");
    }
    public boolean checkCashAvailability(int amount) {
        //check with bank service and atm cash reserve
        return true;
    }
}
