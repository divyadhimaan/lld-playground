package factory;

// VIOLATES Factory Pattern - Direct instantiation with tight coupling

// Product classes (same as before)
class MargheritaPizza2 {
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

class PepperoniPizza2 {
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

class VeggiePizza2 {
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

// VIOLATION: Client code handles object creation directly
class PizzaRestaurant {
    public void orderPizza(String pizzaType) {
        // VIOLATION: Client code contains object creation logic
        // This creates tight coupling between client and concrete classes

        if (pizzaType == null) {
            System.out.println("Invalid pizza type");
            return;
        }

        // VIOLATION: Direct instantiation with conditional logic
        if (pizzaType.toLowerCase().equals("margherita")) {
            MargheritaPizza2 pizza = new MargheritaPizza2();
            pizza.prepare();
            pizza.bake();
            pizza.serve();
        } else if (pizzaType.toLowerCase().equals("pepperoni")) {
            PepperoniPizza2 pizza = new PepperoniPizza2();
            pizza.prepare();
            pizza.bake();
            pizza.serve();
        } else if (pizzaType.toLowerCase().equals("veggie")) {
            VeggiePizza2 pizza = new VeggiePizza2();
            pizza.prepare();
            pizza.bake();
            pizza.serve();
        } else {
            System.out.println("Unknown pizza type: " + pizzaType);
        }

        // PROBLEM: Adding new pizza types requires modifying this method
        // PROBLEM: Object creation logic is scattered throughout the application
    }
}

// Another violation - Multiple clients with duplicated creation logic
class PizzaDeliveryService {
    public void processPizzaOrder(String pizzaType) {
        // VIOLATION: Same object creation logic duplicated here
        MargheritaPizza2 pizza = null;
        PepperoniPizza2 pepperoni = null;
        VeggiePizza2 veggie = null;

        if (pizzaType.toLowerCase().equals("margherita")) {
            pizza = new MargheritaPizza2();
            pizza.prepare();
            pizza.bake();
            pizza.serve();
        } else if (pizzaType.toLowerCase().equals("pepperoni")) {
            pepperoni = new PepperoniPizza2();
            pepperoni.prepare();
            pepperoni.bake();
            pepperoni.serve();
        } else if (pizzaType.toLowerCase().equals("veggie")) {
            veggie = new VeggiePizza2();
            veggie.prepare();
            veggie.bake();
            veggie.serve();
        }

        // PROBLEM: Same creation logic repeated in multiple places
        // PROBLEM: Changes to pizza creation affect multiple classes
    }
}

public class FactoryViolation {
    public static void main(String[] args) {
        PizzaRestaurant restaurant = new PizzaRestaurant();
        PizzaDeliveryService delivery = new PizzaDeliveryService();

        // VIOLATION: Client code is tightly coupled to concrete classes
        restaurant.orderPizza("margherita");
        restaurant.orderPizza("pepperoni");

        delivery.processPizzaOrder("veggie");

        // PROBLEMS:
        // 1. What if we want to add Hawaiian pizza?
        //    - Must modify orderPizza() method
        //    - Must modify processPizzaOrder() method
        //    - Must modify any other method that creates pizzas
        // 2. Creation logic is scattered across multiple classes
        // 3. Violates DRY (Don't Repeat Yourself) principle
        // 4. Hard to test - can't easily mock object creation
        // 5. Tight coupling makes code inflexible
    }
}