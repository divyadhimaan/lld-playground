# Threads

> Fundamental units of execution that allows programs to perform multiple tasks concurrently.
> 
> Can utilize [multi-core](glossary.md#multicore) processors efficiently and improve overall application performance.

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
   

## 3. 