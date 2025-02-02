// Base class
abstract class Bird {
    abstract void fly();
}

// Subclass following Liskov Substitution Principle
class Sparrow extends Bird {
    @Override
    void fly() {
        System.out.println("Sparrow is flying.");
    }
}

// Subclass violating Liskov Substitution Principle
class Penguin extends Bird {
    @Override
    void fly() {
        throw new UnsupportedOperationException("Penguins cannot fly!");
    }
}

// Client code demonstrating Liskov principle
public class LiskovSubstitutionViolation {
    public static void main(String[] args) {
        Bird bird = new Sparrow(); // Works fine
        bird.fly();

        bird = new Penguin(); // Violates Liskov as Penguin cannot fly
        bird.fly(); // Throws exception
    }
}
