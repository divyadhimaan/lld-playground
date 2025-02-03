// Base Pizza class
abstract class Pizza {
    public abstract String getDescription();
    public abstract double cost();
}

// Concrete Pizza class (Margherita)
class MargheritaPizza extends Pizza {
    @Override
    public String getDescription() {
        return "Margherita Pizza";
    }

    @Override
    public double cost() {
        return 5.00;
    }
}

// Decorator base class
abstract class ToppingDecorator extends Pizza {
    protected Pizza pizza;

    public ToppingDecorator(Pizza pizza) {
        this.pizza = pizza;
    }

    public abstract String getDescription();
}

// Extra Cheese Decorator
class ExtraCheese extends ToppingDecorator {
    public ExtraCheese(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + ", Extra Cheese";
    }

    @Override
    public double cost() {
        return pizza.cost() + 1.50; // Cost of extra cheese
    }
}

// Extra Veggies Decorator
class ExtraVeggies extends ToppingDecorator {
    public ExtraVeggies(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + ", Extra Veggies";
    }

    @Override
    public double cost() {
        return pizza.cost() + 2.00; // Cost of extra veggies
    }
}

// Main class to test the decorators
public class DecoratorExample {
    public static void main(String[] args) {
        Pizza pizza = new MargheritaPizza();
        System.out.println(pizza.getDescription() + " | Cost: $" + pizza.cost());

        // Adding extra cheese
        pizza = new ExtraCheese(pizza);
        System.out.println(pizza.getDescription() + " | Cost: $" + pizza.cost());

        // Adding extra veggies
        pizza = new ExtraVeggies(pizza);
        System.out.println(pizza.getDescription() + " | Cost: $" + pizza.cost());
    }
}
