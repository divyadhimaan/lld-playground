class OrderCalculator {
    private static final double TAX_RATE = 0.15;

    public double calculateTotal(OrderAfterSRP order) {
        double total = 0;

        // Calculate subtotal of all items
        for (OrderItem item : order.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }

        // Add tax to the total
        total += total * TAX_RATE;

        return total;
    }
}
