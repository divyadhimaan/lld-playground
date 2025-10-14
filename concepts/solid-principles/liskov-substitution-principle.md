# ```L``` - Liskov Substitution Principle (LSP)

> If S is a subtype of T, then objects of type T in a program may be replaced with objects of type S without altering any of the desirable properties of that program.

**Goal** - This principle aims to enforce consistency so that the parent Class or its child Class can be used in the same way without any errors.

- Subclass should extend the properties of parent class, not narrow it down.

![Liskov Substitution Principle.png](../../images/liskov-substitution.png)
  

## Violation of LSP

```java
class Bird {
    public void fly() {
        System.out.println("I can fly");
    }
}

class Ostrich extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Ostriches can't fly!");
    }
}

```

### Problem:
- Ostrich is a Bird but cannot fly.
- Code that expects any Bird to fly will break.
- Violates LSP: subclasses should be replaceable without altering program correctness.

## Following LSP

```java
abstract class Bird {}

interface Flyable {
    void fly();
}

class Sparrow extends Bird implements Flyable {
    public void fly() {
        System.out.println("Sparrow is flying");
    }
}

class Ostrich extends Bird {
    // No fly method, behaves correctly as Bird
}

class BirdSimulator {
    public void makeItFly(Flyable bird) {
        bird.fly();
    }
}

```

### Benefits:
- Only birds that can actually fly implement Flyable.
- No unexpected exceptions.
- Subtypes can safely replace their base types.