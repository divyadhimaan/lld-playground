package service.dispense;

public class RupeeDispenser implements DispenseHandler{
    private final int denomination;
    private DispenseHandler nextHandler;

    public RupeeDispenser(int denomination) {
        this.denomination = denomination;
    }

    @Override
    public void setNextHandler(DispenseHandler next) {
        this.nextHandler = next;
    }

    @Override
    public void dispense(int amount) {
        if(amount >= denomination){
            int numOfNotes = amount / denomination;
            int remainder = amount % denomination;
            System.out.println("Dispensing " + numOfNotes + " notes of " + denomination + " rupees");
            if(remainder != 0 && nextHandler != null){
                nextHandler.dispense(remainder);
            }
        } else {
            if(nextHandler != null){
                nextHandler.dispense(amount);
            }
        }
    }
}
