
import java.util.ArrayList;
import java.util.List;

class OrderAfterSRP {
    private String orderId;
    private String customerEmail;
    private List<OrderItem> items;

    public OrderAfterSRP(String orderId, String customerEmail) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
        this.items = new ArrayList<>();
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    // Getters
    public String getOrderId() { return orderId; }
    public String getCustomerEmail() { return customerEmail; }
    public List<OrderItem> getItems() { return new ArrayList<>(items); }
}


