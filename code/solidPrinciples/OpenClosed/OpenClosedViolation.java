import java.util.ArrayList;
import java.util.List;

interface TaxCalculator {
    double calculateTax(double amount);
}

class Order {
    private String orderId;
    private String customerEmail;
    private List<OrderItem> items = new ArrayList<>();

    public Order(String orderId, String customerEmail) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }
    public List<OrderItem> getItems() { return new ArrayList<>(items); }


    public double processOrder(TaxCalculator taxCalculator) {
        double total = items.stream().mapToDouble((item) -> item.getPrice() * item.getQuantity()).sum();
        return total + taxCalculator.calculateTax(total);
    }
}

class OrderCalculatorOCP {
    private static final double TAX_RATE = 0.15;

    public double calculateTotal(Order order) {
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

class OrderItem {
    private String name;
    private double price;
    private int quantity;

    public OrderItem(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return name + " - $" + price + " x " + quantity;
    }
}




class OpenClosedViolation {
    public static void main(String[] args) {

        Order order = new Order("ORD123", "customer@example.com");
        order.addItem(new OrderItem("Product 1", 29.99, 2));
        order.addItem(new OrderItem("Product 2", 49.99, 1));

        // Calculate total earlier without OCP
        OrderCalculatorOCP calculator = new OrderCalculatorOCP();
        double totalAmount = calculator.calculateTotal(order);
        System.out.println("Total amount: "+totalAmount);


    }
}