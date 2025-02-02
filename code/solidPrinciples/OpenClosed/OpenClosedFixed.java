import java.util.ArrayList;
import java.util.List;

interface TaxCalculator2 {
    double calculateTax(double amount);
}

class Order2 {
    private String orderId;
    private String customerEmail;
    private List<OrderItem2> items = new ArrayList<>();

    public Order2(String orderId, String customerEmail) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
    }

    public void addItem(OrderItem2 item) {
        items.add(item);
    }
    public List<OrderItem2> getItems() { return new ArrayList<>(items); }


    public double processOrder(TaxCalculator2 taxCalculator) {
        double total = items.stream().mapToDouble((item) -> item.getPrice() * item.getQuantity()).sum();
        return total + taxCalculator.calculateTax(total);
    }
}
class OrderCalculatorOCP2 {
    private static final double TAX_RATE = 0.15;

    public double calculateTotal(Order2 order) {
        double total = 0;

        // Calculate subtotal of all items
        for (OrderItem2 item : order.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }

        // Add tax to the total
        total += total * TAX_RATE;

        return total;
    }
}

class OrderItem2 {
    private String name;
    private double price;
    private int quantity;

    public OrderItem2(String name, double price, int quantity) {
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

class ReducedTaxCalculator2 implements TaxCalculator2 {
    private static final double TAX_RATE = 0.05;

    public double calculateTax(double amount) {
        return amount * TAX_RATE;
    }
}

class StandardTaxCalculator2 implements TaxCalculator2 {
    private static final double TAX_RATE = 0.15;

    public double calculateTax(double amount) {
        return amount * TAX_RATE;
    }
}




class OpenClosedFixed {
    public static void main(String[] args) {

        Order2 order = new Order2("ORD123", "customer@example.com");
        order.addItem(new OrderItem2("Product 1", 29.99, 2));
        order.addItem(new OrderItem2("Product 2", 49.99, 1));

        // Calculate total earlier without OCP
        OrderCalculatorOCP2 calculator = new OrderCalculatorOCP2();
        double totalAmount = calculator.calculateTotal(order);
        System.out.println("Total amount: "+totalAmount);

        // New Feature: We want to calculate standard tax and reduced tax -> we dont want to modify existing class
        TaxCalculator2 taxCalculatorStd = new StandardTaxCalculator2();
        double totalAmountWithStdDeduction= order.processOrder(taxCalculatorStd);
        System.out.println("Total Amount after Standard Deduction: $" + totalAmountWithStdDeduction);

        //New Feature: Suppose a new tax rule requires a reduced tax rate for certain products. We can create a new class

        TaxCalculator2 taxCalculatorRed = new ReducedTaxCalculator2();
        double totalAmountWithRedDeduction= order.processOrder(taxCalculatorRed);
        System.out.println("Total Amount after Reduced Deduction: $" + totalAmountWithRedDeduction);

    }
}