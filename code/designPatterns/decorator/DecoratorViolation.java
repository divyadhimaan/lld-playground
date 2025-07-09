package decorator;

// VIOLATES Decorator Pattern - Uses inheritance explosion instead of composition
class SimpleCoffee2 {
    public String getDescription() {
        return "Simple Coffee";
    }

    public double getCost() {
        return 2.0;
    }
}

// VIOLATION: Creating separate classes for every combination
class CoffeeWithMilk extends SimpleCoffee2 {
    public String getDescription() {
        return "Simple Coffee, Milk";
    }

    public double getCost() {
        return 2.5;
    }
}

class CoffeeWithSugar extends SimpleCoffee2 {
    public String getDescription() {
        return "Simple Coffee, Sugar";
    }

    public double getCost() {
        return 2.2;
    }
}

class CoffeeWithWhip extends SimpleCoffee2 {
    public String getDescription() {
        return "Simple Coffee, Whip";
    }

    public double getCost() {
        return 2.7;
    }
}

// VIOLATION: Exponential class explosion for combinations
class CoffeeWithMilkAndSugar extends SimpleCoffee2 {
    public String getDescription() {
        return "Simple Coffee, Milk, Sugar";
    }

    public double getCost() {
        return 2.7;
    }
}

class CoffeeWithMilkAndWhip extends SimpleCoffee2 {
    public String getDescription() {
        return "Simple Coffee, Milk, Whip";
    }

    public double getCost() {
        return 3.2;
    }
}

class CoffeeWithSugarAndWhip extends SimpleCoffee2 {
    public String getDescription() {
        return "Simple Coffee, Sugar, Whip";
    }

    public double getCost() {
        return 2.9;
    }
}

class CoffeeWithMilkSugarAndWhip extends SimpleCoffee2 {
    public String getDescription() {
        return "Simple Coffee, Milk, Sugar, Whip";
    }

    public double getCost() {
        return 3.4;
    }
}

// Alternative violation using boolean flags
class CoffeeWithFlags {
    private boolean hasMilk;
    private boolean hasSugar;
    private boolean hasWhip;

    public CoffeeWithFlags(boolean hasMilk, boolean hasSugar, boolean hasWhip) {
        this.hasMilk = hasMilk;
        this.hasSugar = hasSugar;
        this.hasWhip = hasWhip;
    }

    public String getDescription() {
        String desc = "Simple Coffee";
        if (hasMilk) desc += ", Milk";
        if (hasSugar) desc += ", Sugar";
        if (hasWhip) desc += ", Whip";
        return desc;
    }

    public double getCost() {
        double cost = 2.0;
        if (hasMilk) cost += 0.5;
        if (hasSugar) cost += 0.2;
        if (hasWhip) cost += 0.7;
        return cost;
    }
}

public class DecoratorViolation {
    public static void main(String[] args) {
        // VIOLATION: Must create separate objects for each combination
        SimpleCoffee2 coffee1 = new SimpleCoffee2();
        System.out.println(coffee1.getDescription() + " $" + coffee1.getCost());

        CoffeeWithMilk coffee2 = new CoffeeWithMilk();
        System.out.println(coffee2.getDescription() + " $" + coffee2.getCost());

        CoffeeWithMilkSugarAndWhip coffee3 = new CoffeeWithMilkSugarAndWhip();
        System.out.println(coffee3.getDescription() + " $" + coffee3.getCost());

        // Alternative with flags - also problematic
        CoffeeWithFlags coffee4 = new CoffeeWithFlags(true, true, false);
        System.out.println(coffee4.getDescription() + " $" + coffee4.getCost());

        // PROBLEM: What if we want to add vanilla? We'd need 16 more classes!
        // PROBLEM: No runtime composition flexibility
    }
}