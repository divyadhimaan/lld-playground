class OrderRepository {
    public void save(OrderAfterSRP order) {
        // Simulating database connection and save
        System.out.println("Connecting to database...");
        System.out.println("Saving order " + order.getOrderId() + " to database");
        System.out.println("Order saved successfully!");
    }
}
