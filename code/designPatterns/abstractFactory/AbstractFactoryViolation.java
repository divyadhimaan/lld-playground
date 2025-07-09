package abstractFactory;

// VIOLATES Abstract Factory Pattern - Direct instantiation with mixed product families

// Product classes (same as before but without interfaces)
class DominosMargheritaPizza2 {
    public void prepare() {
        System.out.println("Preparing authentic Dominos Margherita with thin crust");
    }
}

class DominosTomatoSauce2 {
    public void addSauce() {
        System.out.println("Adding traditional Dominos tomato sauce");
    }
}

class DominosMozzarella2 {
    public void addCheese() {
        System.out.println("Adding fresh Dominos mozzarella");
    }
}

class PizzaHutMargheritaPizza2 {
    public void prepare() {
        System.out.println("Preparing Pizza Hut Margherita with thick crust");
    }
}

class PizzaHutTomatoSauce2 {
    public void addSauce() {
        System.out.println("Adding sweet Pizza Hut tomato sauce");
    }
}

class PizzaHutMozzarella2 {
    public void addCheese() {
        System.out.println("Adding processed Pizza Hut mozzarella");
    }
}

// VIOLATION: Client directly handles product creation and mixing
class PizzaStore2 {
    private String storeType;

    public PizzaStore2(String storeType) {
        this.storeType = storeType;
    }

    public void makePizza() {
        // VIOLATION: Direct instantiation with conditional logic
        // VIOLATION: No guarantee of product family consistency

        if (storeType.equals("dominos")) {
            DominosMargheritaPizza2 pizza = new DominosMargheritaPizza2();
            DominosTomatoSauce2 sauce = new DominosTomatoSauce2();
            DominosMozzarella2 cheese = new DominosMozzarella2();

            pizza.prepare();
            sauce.addSauce();
            cheese.addCheese();

        } else if (storeType.equals("pizzahut")) {
            PizzaHutMargheritaPizza2 pizza = new PizzaHutMargheritaPizza2();
            PizzaHutTomatoSauce2 sauce = new PizzaHutTomatoSauce2();
            PizzaHutMozzarella2 cheese = new PizzaHutMozzarella2();

            pizza.prepare();
            sauce.addSauce();
            cheese.addCheese();
        }

        System.out.println("Pizza ready!\n");
    }
}

// Even worse violation - mixing product families
class BadPizzaStore {
    public void makeMixedPizza() {
        // VIOLATION: Mixing products from different families
        // This creates inconsistent products!

        DominosMargheritaPizza2 pizza = new DominosMargheritaPizza2();  // Dominos pizza
        PizzaHutTomatoSauce2 sauce = new PizzaHutTomatoSauce2();        // Pizza Hut sauce
        DominosMozzarella2 cheese = new DominosMozzarella2();           // Dominos cheese

        pizza.prepare();
        sauce.addSauce();
        cheese.addCheese();

        System.out.println("Inconsistent pizza ready!\n");
        // This creates a pizza with mismatched components!
    }

    public void makeAnotherPizza(String pizzaType, String sauceType, String cheeseType) {
        // VIOLATION: Parameters allow any combination - no family consistency

        if (pizzaType.equals("dominos")) {
            new DominosMargheritaPizza2().prepare();
        } else {
            new PizzaHutMargheritaPizza2().prepare();
        }

        if (sauceType.equals("dominos")) {
            new DominosTomatoSauce2().addSauce();
        } else {
            new PizzaHutTomatoSauce2().addSauce();
        }

        if (cheeseType.equals("dominos")) {
            new DominosMozzarella2().addCheese();
        } else {
            new PizzaHutMozzarella2().addCheese();
        }

        System.out.println("Potentially inconsistent pizza ready!\n");
    }
}

// Another violation - Separate creation methods without coordination
class UncoordinatedPizzaFactory {
    public static Object createPizza(String brand, String type) {
        if (brand.equals("dominos") && type.equals("pizza")) {
            return new DominosMargheritaPizza2();
        } else if (brand.equals("pizzahut") && type.equals("pizza")) {
            return new PizzaHutMargheritaPizza2();
        }
        return null;
    }

    public static Object createSauce(String brand, String type) {
        if (brand.equals("dominos") && type.equals("sauce")) {
            return new DominosTomatoSauce2();
        } else if (brand.equals("pizzahut") && type.equals("sauce")) {
            return new PizzaHutTomatoSauce2();
        }
        return null;
    }

    public static Object createCheese(String brand, String type) {
        if (brand.equals("dominos") && type.equals("cheese")) {
            return new DominosMozzarella2();
        } else if (brand.equals("pizzahut") && type.equals("cheese")) {
            return new PizzaHutMozzarella2();
        }
        return null;
    }

    // VIOLATION: No coordination between methods
    // Client could call: createPizza("dominos", "pizza"), createSauce("pizzahut", "sauce")
    // This would mix families!
}

public class AbstractFactoryViolation {
    public static void main(String[] args) {
        System.out.println("=== Violation Example 1: Basic conditional logic ===");
        PizzaStore2 dominosStore = new PizzaStore2("dominos");
        dominosStore.makePizza();

        PizzaStore2 pizzahutStore = new PizzaStore2("pizzahut");
        pizzahutStore.makePizza();

        System.out.println("=== Violation Example 2: Mixed families ===");
        BadPizzaStore badStore = new BadPizzaStore();
        badStore.makeMixedPizza();  // Creates inconsistent product!

        System.out.println("=== Violation Example 3: Uncoordinated parameters ===");
        badStore.makeAnotherPizza("dominos", "pizzahut", "dominos");  // Mixed family!

        System.out.println("=== Violation Example 4: Separate factory methods ===");
        // Using uncoordinated factory - can easily mix families
        DominosMargheritaPizza2 pizza = (DominosMargheritaPizza2) UncoordinatedPizzaFactory.createPizza("dominos", "pizza");
        PizzaHutTomatoSauce2 sauce = (PizzaHutTomatoSauce2) UncoordinatedPizzaFactory.createSauce("pizzahut", "sauce");  // Wrong family!
        DominosMozzarella2 cheese = (DominosMozzarella2) UncoordinatedPizzaFactory.createCheese("dominos", "cheese");

        pizza.prepare();
        sauce.addSauce();
        cheese.addCheese();
        System.out.println("Mixed family pizza ready!\n");
    }
}