package service;

import model.Denomination;

public class ATMReserve {
//    private Map<Denomination, Integer> noteCounts;

    Boolean hasSufficientCash(double amount) {
        return true;
    }
    void deductCash(double amount) {

    }

    void refillCash(double amount) {

    }

    double getCurrentReserve() {
        return 10000.0;
    }

}
