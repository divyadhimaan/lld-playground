class SharedResource {
    synchronized void waitMethod() {
        try {
            System.out.println(Thread.currentThread().getName() + " waiting...");
            wait(); // releases lock, waits to be notified
            System.out.println(Thread.currentThread().getName() + " resumed!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized void notifyOne() {
        notify(); // wakes up ONE waiting thread
    }

    synchronized void notifyAllThreads() {
        notifyAll(); // wakes up ALL waiting threads
    }
}

public class NotifyExample {
    public static void main(String[] args) throws InterruptedException {
        SharedResource resource = new SharedResource();

        Runnable task = () -> resource.waitMethod();

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");
        Thread t3 = new Thread(task, "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(1000);

        // Try notify() vs notifyAll()
        // resource.notifyOne();      // only ONE thread wakes up
         resource.notifyAllThreads(); // ALL threads wake up
    }
}