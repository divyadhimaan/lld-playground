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