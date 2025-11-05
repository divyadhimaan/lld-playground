package service.dispense.strategy;

import service.dispense.DispenseHandler;

public class GreedyDispenseStrategy implements DispenseStrategy{

    private final DispenseHandler chain;

    public GreedyDispenseStrategy(DispenseHandler chain) {
        this.chain = chain;
    }
    @Override
    public void dispenseAmount(int amount) {
        chain.dispense(amount);
    }
}
