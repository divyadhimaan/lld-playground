// Common interface for all employees
interface RestaurantEmployee2 {
    void doWork();
}

// Separate role-specific interfaces
interface DishWasher extends RestaurantEmployee2 {
    void washDishes();
}

interface Server extends RestaurantEmployee2 {
    void serveCustomers();
}

interface Chef extends RestaurantEmployee2 {
    void cookFood();
}

// Waiter implements only relevant interfaces
class Waiter2 implements Server {
    @Override
    public void serveCustomers() {
        System.out.println("Waiter is serving customers.");
    }

    @Override
    public void doWork() {
        serveCustomers();
    }
}


// Client Code
public class InterfaceSegmentationFixed
{
    public static void main(String[] args) {
        RestaurantEmployee2 waiter = new Waiter2();
        waiter.doWork();
    }
}
