
class WaiterThread extends Thread {
    private final Object lock;

    public WaiterThread(Object restaurantLock) {
        this.lock = restaurantLock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            try {
                System.out.println("Waiter: Waiting for the food to be ready... ⏳");
                lock.wait();  // Waiter enters WAITING state
                System.out.println("Waiter: Food is ready! Delivering to the customer. 🍽️");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class ChefThread extends Thread {
    private final Object lock;

    public ChefThread(Object restaurantLock) {
        this.lock = restaurantLock;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000); // Simulate food preparation time
            synchronized (lock) {
                System.out.println("Chef: Food is ready! Notifying the waiter. 🔔");
                lock.notify(); // Wake up the waiting waiter thread
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
public class RestaurantSimulation {
    public static void main(String[] args) {
        Object restaurantLock = new Object(); // act as lock object

        Thread waiter = new WaiterThread(restaurantLock);
        Thread chef = new ChefThread(restaurantLock);

        waiter.start();
        chef.start();
    }
}
