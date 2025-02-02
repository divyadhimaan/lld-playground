// Base class
abstract class Bird2 {}

// Birds that can fly
abstract class FlyingBird2 extends Bird2 {
    abstract void fly();
}

// Birds that cannot fly
abstract class NonFlyingBird2 extends Bird2 {}

// Sparrow is a FlyingBird
class Sparrow2 extends FlyingBird2{
    @Override
    void fly() {
        System.out.println("Sparrow is flying.");
    }
}

// Penguin is a NonFlyingBird
class Penguin2 extends NonFlyingBird2 {}

public class LiskovSubstitutionFixed {
    public static void main(String[] args) {
        FlyingBird2 bird = new Sparrow2(); // Works fine
        bird.fly();

        // Penguin is not assigned to FlyingBird, thus respecting LSP
        NonFlyingBird2 penguin = new Penguin2();
    }
}
