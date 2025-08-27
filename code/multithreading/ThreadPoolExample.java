import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// WorkerThread simulates a task to be executed by a thread in the pool
class WorkerThread implements Runnable {
    private final int taskId;

    public WorkerThread(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        System.out.println(
                Thread.currentThread().getName() + " is processing task: " + taskId
        );
        try {
            Thread.sleep(2000); // Simulate task execution time
        } catch (InterruptedException e) {
            System.out.println("Task interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
        System.out.println(
                Thread.currentThread().getName() + " finished task: " + taskId
        );
    }
}

// Main class to demonstrate ThreadPool
public class ThreadPoolExample {
    public static void main(String[] args) {
        // Create a fixed thread pool with 3 threads
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // Submit 5 tasks to the thread pool
        for (int i = 1; i <= 5; i++) {
            executorService.submit(new WorkerThread(i));
        }

        // Shutdown the executor service gracefully
        executorService.shutdown();
    }
}
