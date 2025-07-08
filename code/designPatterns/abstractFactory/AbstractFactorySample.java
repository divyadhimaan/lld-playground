package abstractFactory;

// Abstract Products
interface Pizza {
    void prepare();
}

interface Sauce {
    void addSauce();
}

interface Cheese {
    void addCheese();
}

// Concrete Products for Italian Style
class DominosMargheritaPizza implements Pizza {
    public void prepare() {
        System.out.println("Preparing authentic Dominos Margherita with thin crust");
    }
}

class DominosTomatoSauce implements Sauce {
    public void addSauce() {
        System.out.println("Adding traditional Dominos tomato sauce");
    }
}

class DominosMozzarella implements Cheese {
    public void addCheese() {
        System.out.println("Adding fresh Dominos mozzarella");
    }
}

// Concrete Products for American Style
class PizzaHutMargheritaPizza implements Pizza {
    public void prepare() {
        System.out.println("Preparing Pizza Hut Margherita with thick crust");
    }
}

class PizzaHutTomatoSauce implements Sauce {
    public void addSauce() {
        System.out.println("Adding sweet Pizza Hut tomato sauce");
    }
}

class PizzaHutMozzarella implements Cheese {
    public void addCheese() {
        System.out.println("Adding processed Pizza Hut mozzarella");
    }
}

// Abstract Factory
interface PizzaFactory {
    Pizza createPizza();
    Sauce createSauce();
    Cheese createCheese();
}

// Concrete Factories
class DominosPizzaFactory implements PizzaFactory {
    public Pizza createPizza() {
        return new DominosMargheritaPizza();
    }

    public Sauce createSauce() {
        return new DominosTomatoSauce();
    }

    public Cheese createCheese() {
        return new DominosMozzarella();
    }
}

class PizzaHutPizzaFactory implements PizzaFactory {
    public Pizza createPizza() {
        return new PizzaHutMargheritaPizza();
    }

    public Sauce createSauce() {
        return new PizzaHutTomatoSauce();
    }

    public Cheese createCheese() {
        return new PizzaHutMozzarella();
    }
}

// Client
class PizzaStore {
    private PizzaFactory factory;

    public PizzaStore(PizzaFactory factory) {
        this.factory = factory;
    }

    public void makePizza() {
        Pizza pizza = factory.createPizza();
        Sauce sauce = factory.createSauce();
        Cheese cheese = factory.createCheese();

        pizza.prepare();
        sauce.addSauce();
        cheese.addCheese();
        System.out.println("Pizza ready!\n");
    }
}

// Usage
public class AbstractFactorySample {
    public static void main(String[] args) {
        // Dominos style pizza store
        PizzaStore dominosStore = new PizzaStore(new DominosPizzaFactory());
        System.out.println("Dominos Style:");
        dominosStore.makePizza();

        // PizzaHut style pizza store
        PizzaStore PizzaHutStore = new PizzaStore(new PizzaHutPizzaFactory());
        System.out.println("PizzaHut Style:");
        PizzaHutStore.makePizza();

        // Output:
        // Dominos Style:
        // Preparing authentic Dominos Margherita with thin crust
        // Adding traditional Dominos tomato sauce
        // Adding fresh Dominos mozzarella
        // Pizza ready!
        //
        // PizzaHut Style:
        // Preparing PizzaHut Margherita with thick crust
        // Adding sweet PizzaHut tomato sauce
        // Adding processed PizzaHut mozzarella
        // Pizza ready!
    }
}