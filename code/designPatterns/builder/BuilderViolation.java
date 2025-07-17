package builder;
// Violation: Telescoping Constructor Anti-Pattern
class Burger {
    private String bun;
    private boolean cheese;
    private boolean lettuce;
    private boolean tomato;
    private boolean mayo;
    private boolean mustard;

    // Telescoping constructors
    public Burger(String bun) {
        this(bun, false, false, false, false, false);
    }

    public Burger(String bun, boolean cheese) {
        this(bun, cheese, false, false, false, false);
    }

    public Burger(String bun, boolean cheese, boolean lettuce, boolean tomato,
                  boolean mayo, boolean mustard) {
        this.bun = bun;
        this.cheese = cheese;
        this.lettuce = lettuce;
        this.tomato = tomato;
        this.mayo = mayo;
        this.mustard = mustard;
    }

    public void display() {
        System.out.println("Burger with bun: " + bun +
                ", cheese: " + cheese +
                ", lettuce: " + lettuce +
                ", tomato: " + tomato +
                ", mayo: " + mayo +
                ", mustard: " + mustard);
    }
}

// Usage
public class BuilderViolation {
    public static void main(String[] args) {
        Burger burger = new Burger("Sesame", true, true, false, true, false);
        burger.display();
    }
}
