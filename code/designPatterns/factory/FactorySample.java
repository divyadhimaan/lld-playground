package factory;

// Product interface
interface Pizza {
    void prepare();
    void bake();
    void serve();
}

// Concrete Products
class MargheritaPizza implements Pizza {
    public void prepare() {
        System.out.println("Preparing Margherita Pizza with tomato sauce, mozzarella, and basil");
    }

    public void bake() {
        System.out.println("Baking Margherita Pizza at 450°F for 12 minutes");
    }

    public void serve() {
        System.out.println("Serving hot Margherita Pizza");
    }
}

class PepperoniPizza implements Pizza {
    public void prepare() {
        System.out.println("Preparing Pepperoni Pizza with tomato sauce, mozzarella, and pepperoni");
    }

    public void bake() {
        System.out.println("Baking Pepperoni Pizza at 475°F for 15 minutes");
    }

    public void serve() {
        System.out.println("Serving hot Pepperoni Pizza");
    }
}

class VeggiePizza implements Pizza {
    public void prepare() {
        System.out.println("Preparing Veggie Pizza with tomato sauce, mozzarella, bell peppers, and mushrooms");
    }

    public void bake() {
        System.out.println("Baking Veggie Pizza at 425°F for 14 minutes");
    }

    public void serve() {
        System.out.println("Serving hot Veggie Pizza");
    }
}

// Factory class
class PizzaFactory {
    public static Pizza createPizza(String pizzaType) {
        if (pizzaType == null) {
            return null;
        }

        switch (pizzaType.toLowerCase()) {
            case "margherita":
                return new MargheritaPizza();
            case "pepperoni":
                return new PepperoniPizza();
            case "veggie":
                return new VeggiePizza();
            default:
                throw new IllegalArgumentException("Unknown pizza type: " + pizzaType);
        }
    }
}

// Usage
public class FactorySample {
    public static void main(String[] args) {
        // Customer orders without knowing pizza creation details
        Pizza margherita = PizzaFactory.createPizza("margherita");
        Pizza pepperoni = PizzaFactory.createPizza("pepperoni");
        Pizza veggie = PizzaFactory.createPizza("veggie");

        margherita.prepare();
        margherita.bake();
        margherita.serve();

        pepperoni.prepare();
        pepperoni.bake();
        pepperoni.serve();

        veggie.prepare();
        veggie.bake();
        veggie.serve();
    }
}