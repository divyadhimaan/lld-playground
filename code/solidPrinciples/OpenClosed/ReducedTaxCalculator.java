public class ReducedTaxCalculator implements TaxCalculator {
    private static final double TAX_RATE = 0.05;

    public double calculateTax(double amount) {
        return amount * TAX_RATE;
    }
}
