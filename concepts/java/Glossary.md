# Java Glossary

## Volatile Keyword

- volatile is a keyword in Java used with variables.
- It tells the JVM and compiler that a variable’s value will be modified by multiple threads.
- Ensures that reads/writes to the variable are always done from main memory (not thread-local caches).
- Use Case 
  - Without volatile, threads may cache variable values locally and not see updates by other threads.
  - With volatile, updates by one thread are visible to all other threads instantly.
```java
// when one thread sets running = false, all threads will immediately see it.
class SharedData {
    volatile boolean running = true;

    void worker() {
        while (running) {
            // do some work
        }
    }
}

```

- volatile ensures visibility of changes across threads but not atomicity. For counters or complex shared data updates, use synchronized or Atomic classes instead.