package builder;

// Builder Pattern applied
class Burger2 {
    private String bun;
    private boolean cheese;
    private boolean lettuce;
    private boolean tomato;
    private boolean mayo;
    private boolean mustard;

    // Private constructor
    private Burger2(Burger2Builder builder) {
        this.bun = builder.bun;
        this.cheese = builder.cheese;
        this.lettuce = builder.lettuce;
        this.tomato = builder.tomato;
        this.mayo = builder.mayo;
        this.mustard = builder.mustard;
    }

    public void display() {
        System.out.println("Burger2 with bun: " + bun +
                ", cheese: " + cheese +
                ", lettuce: " + lettuce +
                ", tomato: " + tomato +
                ", mayo: " + mayo +
                ", mustard: " + mustard);
    }

    // Static nested Builder class
    public static class Burger2Builder {
        private String bun;
        private boolean cheese;
        private boolean lettuce;
        private boolean tomato;
        private boolean mayo;
        private boolean mustard;

        public Burger2Builder(String bun) {
            this.bun = bun;
        }

        public Burger2Builder addCheese() {
            this.cheese = true;
            return this;
        }

        public Burger2Builder addLettuce() {
            this.lettuce = true;
            return this;
        }

        public Burger2Builder addTomato() {
            this.tomato = true;
            return this;
        }

        public Burger2Builder addMayo() {
            this.mayo = true;
            return this;
        }

        public Burger2Builder addMustard() {
            this.mustard = true;
            return this;
        }

        public Burger2 build() {
            return new Burger2(this);
        }
    }
}

// Usage
public class BuilderSample {
    public static void main(String[] args) {
        Burger2 burger = new Burger2.Burger2Builder("Sesame")
                .addCheese()
                .addLettuce()
                .addMayo()
                .build();

        burger.display();
    }
}
