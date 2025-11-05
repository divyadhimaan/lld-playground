package service.dispense;

import atm.ATMReserve;
import model.enums.Denomination;

import java.util.Map;

public class RupeeDispenser implements DispenseHandler{
    private final Denomination denomination;
    private final ATMReserve reserve;
    private DispenseHandler nextHandler;

    public RupeeDispenser(Denomination denomination, ATMReserve reserve) {
        this.denomination = denomination;
        this.reserve = reserve;
    }

    @Override
    public void setNextHandler(DispenseHandler next) {
        this.nextHandler = next;
    }

    @Override
    public void dispense(int amount) {
        if (amount <= 0) return;

        Map<Denomination, Integer> snapshot = reserve.snapshot();

        int available = snapshot.getOrDefault(denomination, 0);
        int value = denomination.getValue();

        if (amount >= value && available > 0) {
            int numNotes = Math.min(amount / value, available);
            int remainder = amount - numNotes * value;

            if (numNotes > 0) {
                reserve.commitDispense(Map.of(denomination, numNotes));
                System.out.println("Dispensing " + numNotes + " notes of ₹" + value);
            }

            if (remainder > 0 && nextHandler != null) nextHandler.dispense(remainder);
        } else if (nextHandler != null) {
            nextHandler.dispense(amount);
        } else {
            System.out.println("⚠️ Unable to dispense exact ₹" + amount + " (insufficient notes).");
        }
    }


}
