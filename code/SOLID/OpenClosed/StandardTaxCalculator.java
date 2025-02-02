public class StandardTaxCalculator implements TaxCalculator {
    private static final double TAX_RATE = 0.15;

    public double calculateTax(double amount) {
        return amount * TAX_RATE;
    }
}
