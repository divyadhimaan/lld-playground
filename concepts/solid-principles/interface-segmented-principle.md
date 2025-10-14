# ```I``` - Interface Segmented Principle (ISP)

> Interfaces should be such, that Clients should not be forced to depend on methods that they do not use.

**Goal** - This principle aims at splitting a set of actions into smaller sets so that a Class executes ONLY the set of actions it requires.

![interface segregation principle.png](../../images/interface-segmented.png)
  

## Violation of ISP

```java

interface Worker {
    void work();
    void eat();
}

class HumanWorker implements Worker {
    public void work() { System.out.println("Working"); }
    public void eat() { System.out.println("Eating"); }
}

class RobotWorker implements Worker {
    public void work() { System.out.println("Working"); }
    public void eat() {
        // Robots don't eat, but still forced to implement this
        throw new UnsupportedOperationException("Robots don't eat!");
    }
}
```

### Problem:
- RobotWorker is forced to implement eat().
- Violates ISP: clients should not be forced to depend on methods they don’t use.

## Following ISP

```java
interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

class HumanWorker implements Workable, Eatable {
    public void work() { System.out.println("Working"); }
    public void eat() { System.out.println("Eating"); }
}

class RobotWorker implements Workable {
    public void work() { System.out.println("Working"); }
}

```

### Benefits:
- Interfaces are segmented based on responsibility. 
- Classes implement only what they need. 
- No unnecessary methods or exceptions.