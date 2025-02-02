
class OCPDemo {
    public static void main(String[] args) {

        Order order = new Order("ORD123", "customer@example.com");
        order.addItem(new OrderItem("Product 1", 29.99, 2));
        order.addItem(new OrderItem("Product 2", 49.99, 1));

        // Calculate total earlier without OCP
        OrderCalculatorOCP calculator = new OrderCalculatorOCP();
        double totalAmount = calculator.calculateTotal(order);
        System.out.println("Total amount: "+totalAmount);

        // New Feature: We want to calculate standard tax and reduced tax -> we dont want to modify existing class
        TaxCalculator taxCalculatorStd = new StandardTaxCalculator();
        double totalAmountWithStdDeduction= order.processOrder(taxCalculatorStd);
        System.out.println("Total Amount after Standard Deduction: $" + totalAmountWithStdDeduction);

        //New Feature: Suppose a new tax rule requires a reduced tax rate for certain products. We can create a new class

        TaxCalculator taxCalculatorRed = new ReducedTaxCalculator();
        double totalAmountWithRedDeduction= order.processOrder(taxCalculatorRed);
        System.out.println("Total Amount after Reduced Deduction: $" + totalAmountWithRedDeduction);

    }
}