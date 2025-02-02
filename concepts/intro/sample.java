import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class OrderBefore {
    private String orderId;
    private String customerEmail;
    private List<OrderItem> items;
    private double totalAmount;

    public OrderBefore(String orderId, String customerEmail) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
        this.items = new ArrayList<>();
    }

    public void addItem(OrderItem item) {
        items.add(item);
        calculateTotal();
    }

    // Responsibility 1: Calculate total
    private void calculateTotal() {
        totalAmount = 0;
        for (OrderItem item : items) {
            totalAmount += item.getPrice() * item.getQuantity();
        }
        // Add tax
        totalAmount += totalAmount * 0.15;
    }

    // Responsibility 2: Save to database
    public void saveToDatabase() {
        // Simulating database connection and save
        System.out.println("Connecting to database...");
        System.out.println("Saving order " + orderId + " to database");
        System.out.println("Order saved successfully!");
    }

    // Responsibility 3: Send confirmation email
    public void sendConfirmationEmail() {
        // Simulating email sending
        System.out.println("Connecting to email server...");
        System.out.println("Sending confirmation to " + customerEmail);
        System.out.println("Email sent successfully!");
    }

    // Responsibility 4: Generate invoice
    public void generateInvoice() {
        try {
            FileWriter writer = new FileWriter("invoice_" + orderId + ".txt");
            writer.write("Order Invoice\n");
            writer.write("Order ID: " + orderId + "\n");
            writer.write("Customer: " + customerEmail + "\n");
            writer.write("Items:\n");
            for (OrderItem item : items) {
                writer.write(item.toString() + "\n");
            }
            writer.write("Total Amount: $" + totalAmount);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error generating invoice: " + e.getMessage());
        }
    }
}

