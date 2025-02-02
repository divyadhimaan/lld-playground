import java.io.FileWriter;
import java.io.IOException;

class InvoiceGenerator {
    public void generateInvoice(OrderAfterSRP order, double totalAmount) {
        try {
            FileWriter writer = new FileWriter("invoice_" + order.getOrderId() + ".txt");
            writer.write("Order Invoice\n");
            writer.write("Order ID: " + order.getOrderId() + "\n");
            writer.write("Customer: " + order.getCustomerEmail() + "\n");
            writer.write("Items:\n");
            for (OrderItem item : order.getItems()) {
                writer.write(item.toString() + "\n");
            }
            writer.write("Total Amount: $" + totalAmount);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error generating invoice: " + e.getMessage());
        }
    }
}
