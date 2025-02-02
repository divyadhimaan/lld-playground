
class SRPDemo {
    public static void main(String[] args) {

        //----------------------- Using code without SRP
        OrderBeforeSRP order1 = new OrderBeforeSRP("ORD123", "customer@example.com");

        order1.addItem(new OrderItem("Product 1", 29.99, 2));
        order1.addItem(new OrderItem("Product 2", 49.99, 1));

        order1.saveToDatabase();
        order1.sendConfirmationEmail();
        order1.generateInvoice();

        System.out.println("Order processing completed!");

        //-----------------------------USING SRP ---------------------------------------//

        OrderAfterSRP order2 = new OrderAfterSRP("ORD123", "customer@example.com");
        order2.addItem(new OrderItem("Product 1", 29.99, 2));
        order2.addItem(new OrderItem("Product 2", 49.99, 1));

        // Calculate total
        OrderCalculator calculator = new OrderCalculator();
        double totalAmount = calculator.calculateTotal(order2);

        // Save to database
        OrderRepository repository = new OrderRepository();
        repository.save(order2);

        // Send confirmation email
        EmailService emailService = new EmailService();
        emailService.sendConfirmationEmail(order2);

        // Generate invoice
        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        invoiceGenerator.generateInvoice(order2, totalAmount);
    }
}