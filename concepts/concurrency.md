# Threads

> Fundamental units of execution that allows programs to perform multiple tasks concurrently.
> 
> Can utilize [multi-core](#multicore) processors efficiently and improve overall application performance.

# Process vs Thread

| **Aspect**         | **Process**                                                                                                            | **Thread**                                                                                                                                                                                                                         |
|--------------------|------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Definition**     | An independent program in execution, with its own memory space.                                                        | A smaller unit of execution inside a process, sharing the same memory space with other threads of that process.                                                                                                                    |
| **Memory**         | Has **separate** memory (code, data, stack, heap).                                                                     | Shares the process's memory (code, data, heap) but has its **own** stack. , allowing efficient data sharing                                                                                                                        |
| **Communication**  | Requires **Inter-Process Communication (IPC)** mechanisms (e.g., pipes, sockets, or message queues).                   | Can communicate easily via shared variables in the same process.                                                                                                                                                                   |
| **Isolation**      | Independent — if one process crashes, it usually doesn’t crash others.                                                 | Less isolated — if one thread misbehaves (e.g., corrupts shared memory), it can crash the whole process.                                                                                                                           |
| **Overhead**       | Higher (switching between processes is slower).                                                                        | Lower (switching between threads is faster).                                                                                                                                                                                       |
| **Switching Cost** | More expensive due to context switching (saving/restoring process state).                                              | Less expensive due to lighter context switching (only saving/restoring thread state).                                                                                                                                              |
| **Analogy**        | A separate office building (process) with its own rooms (memory).                                                      | A team working in the same office (process), sharing resources but having their own desks (stacks).                                                                                                                                |
| **Usage**          | Use processes for isolation and safety.                                                                                | Use threads for speed and shared work within one application. Improve Responsiveness and performance                                                                                                                               |
| **Example**        | - Running a web server as a separate process, <br/> - Image Processing pipelines(handle large computations seperately) | - **Mobile Apps**, <br/>- **E-Commerce Platforms**(multiple users to browse, add to cart and checkout simultaneously), <br/>- **Audio streaming platforms** (Threads for responsive UI, while continously buffering in background) |

# Key features of Threads
1. **Lightweight**
    - Threads are smaller units of execution within a process.
    - Creating and switching between threads is faster than processes.

2. **Shared Memory Space**
    - All threads in a process share **code**, **data**, and **heap**.
    - Each thread has its **own stack** for function calls and local variables.

3. **Independent Execution Path**
    - Each thread can run independently but still be part of the same process.

4. **Faster Context Switching**
    - Switching between threads is quicker than between processes because less state needs to be saved/restored.

5. **Easy Communication**
    - Since threads share memory, they can communicate without special mechanisms like IPC.
    - Requires **synchronization** to prevent race conditions.

6. **Better Resource Utilization**
    - Threads can run in parallel on multicore CPUs for improved performance.

7. **Not Fully Isolated**
    - A fault in one thread (e.g., memory corruption) can crash the entire process.

# Creating Thread in Java

## 1. Extending the `Thread` class

   ```java
   class MyThread extends Thread{
       @Override
       public void run(){
           System.out.println("Thread is running");
           //some logic
       }
   }
   
   public class ThreadExample {
       public static void main(String[] args) {
           MyThread thread1 = new MyThread();
           
           thread1.start(); // Start the thread1
       }
   }
   ```
   Refer for complete example [here](./../code/multithreading/ThreadExample.java)

   #### Drawbacks:
   - **Single Inheritance Limitation** → Cannot extend another class if you extend `Thread`.
   - **Tight Coupling** → Task logic is tied directly to the thread object.
   - **Manual Management** → You have to handle creation, starting, and stopping of threads yourself.
   - **Poor Scalability** → Creating many threads manually can lead to high memory and CPU overhead.

---

## 2. Implementing the `Runnable` interface

   ```java
   class MyRunnable implements Runnable {
       @Override
       public void run() {
           System.out.println("Thread is running");
           //some logic
       }
   }
   
   public class ThreadExample {
       public static void main(String[] args) {
           Thread thread1 = new Thread(new MyRunnable());
           
           thread1.start(); // Start the thread1
       }
   }
   ```
   Refer for complete example [here](./../code/multithreading/RunnableExample.java)

   ### How `Runnable` Works in Java

   ```plaintext
   [Start Program]
   |
   v
   [Create class implementing Runnable]
   |
   v
   [Override run() method with task code]
   |
   v
   [Create Runnable object]
   |
   v
   [Create Thread object with Runnable as parameter]
   |
   v
   [Call thread.start()]
   |
   v
   [JVM creates new thread]
   |
   v
   [Thread calls run() of Runnable]
   |
   v
   [Task executes in parallel with main thread]
   |
   v
   [Thread finishes execution]
   |
   v
   [End Program]
   ```

   Advantages:
   - **Decoupling** → Task logic is separate from thread management.
   - **Multiple Inheritance** → Can implement multiple interfaces.
   - **Better Resource Management** → Use thread pools for efficient resource utilization.
   - **Easier Testing** → Runnable can be tested independently of threading concerns.
   - Same `Runnable` instance can be shared across multiple threads.
   
   Disadvantages:
   - **More Boilerplate** → Requires more code to set up compared to extending `Thread`.
   - **Manual Thread Creation** → You still need to create and manage threads explicitly.
   - **No Thread Control** → Cannot directly control thread lifecycle (e.g., pause, resume) like with `Thread` class.
   
---

## 3. Implementing `Callable` Interface
   - Similar to `Runnable`, but:
      - Can **return a result**.
      - Can **throw [checked exceptions](#checked-exceptions).**
      - Works with **[Future](#future)** objects to retrieve results asynchronously (after task completion).
      - Used with [ExecutorService](#executor-service) for concurrent execution.
      
      

   ```java
   import java.util.concurrent.Callable;
   import java.util.concurrent.ExecutorService;
   import java.util.concurrent.Executors;
   import java.util.concurrent.Future;
   
   class MyCallable implements Callable<String> {
      @Override
      public String call() throws Exception {
         System.out.println("Callable task is running");
         // some logic
         return "Task Completed";
      }
   }
   
   public class CallableExample {
      public static void main(String[] args) throws Exception {
         ExecutorService executor = Executors.newSingleThreadExecutor();
   
         Future<String> future = executor.submit(new MyCallable()); // Start task in a separate thread
   
         String result = future.get(); // Wait and get the result from call()
   
         System.out.println("Result from Callable: " + result);
   
         executor.shutdown();
      }
   }
   ```
   Refer for complete example [here](./../code/multithreading/CallableExample.java)

   ### Callable Flow
    Callable task  -->  submitted to ExecutorService  -->  runs in a thread
                  ^                                    |
                  |                                    v
            Future object  <--------- result returned via Future.get()
---

## Difference between `Thread`, `Runnable` and `Callable`

| Aspect / Feature               | **Thread**                      | **Runnable**                                | **Callable**                                                    |
| ------------------------------ | ------------------------------- | ------------------------------------------- | --------------------------------------------------------------- |
| **Type**                       | Class                           | Functional Interface                        | Functional Interface                                            |
| **Method to implement**        | `void run()`                    | `void run()`                                | `V call() throws Exception`                                     |
| **Returns value?**             | ❌ No                            | ❌ No                                        | ✅ Yes (returns a value of type `V`)                             |
| **Throws Checked Exceptions?** | ❌ Not allowed                   | ❌ Not allowed                               | ✅ Allowed                                                       |
| **How to execute**             | `thread.start()`                | Create `Thread` → pass Runnable → `start()` | Submit to `ExecutorService` → `submit(callable)` → get `Future` |
| **Thread management**          | Manual                          | Manual                                      | Managed by ExecutorService (thread pool)                        |
| **When to use**                | Very simple/quick demo programs | When task needs to be decoupled from Thread | When task produces **result** and/or **may throw exception**    |


# FAQs

#### Q1. What is the difference between `start()` and `run()` in Java threads?
- The `start()` method begins thread execution by invoking the `run()` method in a new thread of execution.
- While the `run()` method contains the code that defines the thread's task, calling `run()` directly does not start a new thread; it executes the code in the current thread.

    ```java
    class MyThread extends Thread {
        public void run(){
            System.out.println("Thread is running");
        }
    }
    
    class ThreadExample {
        public static void main(String[] args) {
            MyThread thread = new MyThread();
    
            thread.run(); // Executes run() in the current(main) thread, not a new one
            thread.start(); // Starts a new thread and calls run() in that new thread
        }
    }
    ```
  

#### Q2.Can we call the start() method multiple times on the same thread object in Java?
- No, calling `start()` multiple times on the same thread object will throw an `IllegalThreadStateException`.
- A thread can only be started once; subsequent calls to `start()` on the same thread instance are illegal.
    ```java
    class MyThread extends Thread {
        public void run() {
            System.out.println("Thread is running");
        }
    }
    
    class ThreadExample {
        public static void main(String[] args) {
            MyThread thread = new MyThread();
            thread.start(); // First call - valid
            thread.start(); // Second call - throws IllegalThreadStateException
        }
    }
    ```
  
#### Q3. What is thread safety and how can we achieve it in Java?
- **Thread safety** refers to the property of a piece of code or data structure that guarantees safe execution by multiple threads concurrently without causing data corruption or unexpected behavior.
- It can be achieved through:
  - `Synchronization`: Using synchronized blocks or methods to ensure that only one thread can access a resource at a time.
  - `Immutable objects`: Creating objects that cannot be modified after creation, ensuring thread safety by design.
  - `Concurrent collections`: Using thread-safe collections like `ConcurrentHashMap`, `CopyOnWriteArrayList`, etc.
  - `Atomic variables`: Using classes from the `java.util.concurrent.atomic` package, such as `AtomicInteger`, which provide lock-free thread-safe operations.
  - `ThreadLocal`: Using `ThreadLocal` variables to provide each thread with its own instance of a variable, avoiding shared state.

#### Q4. What happens id exception occurs in a thread's run method?
- If an exception occurs in a thread's `run()` method and is not caught, the thread will terminate, and the exception will be printed to the console.
- The exception does not get propagated to the main thread or other threads, and the program may continue running unless the exception is critical (like `OutOfMemoryError`).


#### Q5. What is the difference between `sleep()` and `wait()` in Java?
- `Sleep()`: 
  - Belongs to the `Thread` class.
  - Causes the current thread to pause execution for a specified duration.
  - Does not release any locks held by the thread.
  - Used for delaying execution without affecting synchronization.
- `Wait()`:
  - Belongs to the `Object` class.
  - Causes the current thread to release the lock it holds on an object and wait until another thread calls `notify()` or `notifyAll()` on that object.
  - Used for inter-thread communication and synchronization.
  - Must be called from a synchronized context (inside a synchronized block or method).

#### Q6. What happens to the resource a thread was holding when the `wait()` method is called?
- When a thread calls the `wait()` method on an object, it releases the lock it holds on that object.
- This allows other threads to acquire the lock and proceed with their execution.
- The waiting thread remains in the waiting state until another thread calls `notify()` or `notifyAll()` on the same object, at which point it can attempt to reacquire the lock and continue execution.

#### Q7. What happens when the `notify()` or `notifyAll()` method is called on an object?
- Another thread holding the same lock can call `notify()` or `notifyAll()` on that object to wake up waiting threads.
- `notify()`: Wakes up a single thread that is waiting on the object's monitor. If multiple threads are waiting, one is chosen arbitrarily.
- `notifyAll()`: Wakes up all threads that are waiting on the object's monitor. All awakened threads will compete to acquire the lock once it is released.
- The idle thread(s) will not proceed until they can successfully reacquire the lock on the object. i.e. thread(s) don't start executing immediately after notify/notifyAll is called.

    ```java
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
            resource.notifyOne();      // only ONE thread wakes up
            // resource.notifyAllThreads(); // ALL threads wake up
        }
    }
    
    ```

# Glossary

`Multicore`

- A multicore processor is a single computing component with two or more independent actual processing units (called "cores").
- Each core can read and execute program instructions, allowing for parallel processing.
- This means that a multicore processor can perform multiple tasks simultaneously, improving performance and efficiency for applications that are designed to take advantage of multiple cores.
- Multicore processors are commonly used in modern computers, smartphones, and other devices to enhance multitasking capabilities and overall system performance.
- Example-
    - Single-core → One worker doing all the jobs sequentially.
    - Quad-core → Four workers doing separate jobs at the same time.


`Checked exceptions`
- Exceptions that must be either caught or declared in the method signature, ensuring that the programmer handles them appropriately.
- They are checked at compile time, meaning the compiler verifies that these exceptions are handled in the code.
- Examples: `IOException`, `SQLException`, `InterruptedException`(Thread Interruption).

`Unchecked exceptions`
- Exceptions that do not need to be explicitly handled or declared.
- They are checked at runtime, meaning the compiler does not enforce handling them.
- Examples: `NullPointerException`, `ArrayIndexOutOfBoundsException`, `IllegalArgumentException`.

`Future`

- Represents the result of an asynchronous computation.
- It allows you to retrieve the result of a task once it completes, or check if it is done.
- Used with `Callable` to get results after task completion.
- Can also check if the task is still running or has completed.

`Executor Service`

- An `Executor` is a  java interface that represents an object capable of running *submitted* tasks.
- While `ExecutorService` is a  more advanced sub-interface of `Executor` that supports **thread pooling**, **task scheduling**, and **lifecycle management** (shutdown, etc.).
- Instead of creating threads manually, we submit tasks to an executor.
- The executor manages threads internally and reuses them (thread pool → better performance & scalability).