import java.util.ArrayList;
import java.util.List;

public class Order {
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