package service.dispense.strategy;

import service.dispense.DispenseHandler;
import service.dispense.RupeeDispenser;

public class MixedDispenseStrategy implements DispenseStrategy {
    private final DispenseHandler mainChain;
    private final DispenseHandler midChain;

    public MixedDispenseStrategy(DispenseHandler chain) {
        this.mainChain = chain;

        DispenseHandler d500 = new RupeeDispenser(500);
        DispenseHandler d200 = new RupeeDispenser(200);
        DispenseHandler d100 = new RupeeDispenser(100);
        d500.setNextHandler(d200);
        d200.setNextHandler(d100);
        this.midChain = d500;
    }

    @Override
    public void dispenseAmount(int amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        // Split 70/30 between main chain and mid chain
        int highPart = (int) (amount * 0.7);
        highPart = (highPart / 100) * 100;
        int remainder = amount - highPart;

        System.out.println("Dispensing ₹" + highPart + " using higher denominations...");
        mainChain.dispense(highPart);

        if (remainder > 0) {
            System.out.println("[Change Mode] Dispensing remaining ₹" + remainder + " using smaller notes...");
            midChain.dispense(remainder);
        }

        System.out.println("✅ Total ₹" + amount + " dispensed with change mix.");
    }
}
