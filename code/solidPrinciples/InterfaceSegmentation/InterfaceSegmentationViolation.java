// Single interface forcing all employees to implement unnecessary methods
interface RestaurantEmployee {
    void washDishes();
    void serveCustomers();
    void cookFood();
}

// Waiter class forced to implement irrelevant methods -- Violation of ISP
class Waiter implements RestaurantEmployee {
    @Override
    public void washDishes() {
        throw new UnsupportedOperationException("Waiter doesn't wash dishes!");
    }

    @Override
    public void serveCustomers() {
        System.out.println("Waiter is serving customers.");
    }

    @Override
    public void cookFood() {
        throw new UnsupportedOperationException("Waiter doesn't cook food!");
    }
}
