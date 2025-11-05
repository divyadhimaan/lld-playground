package atm;

import model.enums.Denomination;

import java.util.*;

public class ATMReserve {
    private final List<Denomination> denominationList;
    private final Map<Denomination, Integer> noteCounts;

    ATMReserve(Map<Denomination, Integer> initial){
        this.noteCounts = new EnumMap<>(Denomination.class);
        this.noteCounts.putAll(initial);

        this.denominationList = new ArrayList<>(Arrays.asList(Denomination.values()));
        //descending order
        this.denominationList.sort((a,b)-> Integer.compare(b.getValue(), a.getValue()));
    }

    /**
     * Try to create a dispense plan starting from a given minimum denomination index.
     * Does NOT mutate the reserve. Returns null if impossible.
     * startFrom is the denomination to start from (e.g., TWO_THOUSAND or FIVE_HUNDRED).
     */
    public synchronized Map<Denomination, Integer> planDispense(int amount, Denomination startFrom) {
        if (amount <= 0 || amount % 100 != 0) return null; // assume 100 is smallest unit

        Map<Denomination, Integer> plan = new EnumMap<>(Denomination.class);
        int remaining = amount;

        // iterate denominations but start at `startFrom`
        boolean startAdding = false;
        for (Denomination denom : denominationList) {
            if (denom == startFrom) startAdding = true;
            if (!startAdding) continue;

            int value = denom.getValue();
            int available = noteCounts.getOrDefault(denom, 0);
            if (available <= 0) continue;

            int needed = remaining / value;
            int used = Math.min(needed, available);
            if (used > 0) {
                plan.put(denom, used);
                remaining -= used * value;
            }
        }
        if (remaining == 0) return plan;
        return null; // cannot make exact change starting from that denom
    }

    /**
     * Commit a plan (deduct counts). Atomic with respect to this Reserve.
     * Assumes plan is feasible.
     */
    public synchronized void commitDispense(Map<Denomination,Integer> plan) {
        for (var e : plan.entrySet()) {
            Denomination d = e.getKey();
            int used = e.getValue();
            int cur = noteCounts.getOrDefault(d, 0);
            if (used > cur) throw new IllegalStateException("Plan not feasible at commit time");
            noteCounts.put(d, cur - used);
        }
    }
    public synchronized Map<Denomination,Integer> snapshot() {
        return new EnumMap<>(noteCounts);
    }

    public synchronized void refillCash(Map<Denomination,Integer> refill) {
        for(var entry : refill.entrySet()){
            noteCounts.put(entry.getKey(), noteCounts.getOrDefault(entry.getKey(),0) + entry.getValue());
        }
    }

    public double getTotalCash() {
        return noteCounts.entrySet().stream().mapToInt(
                entry -> entry.getKey().getValue() * entry.getValue()
        ).sum();
    }

}
