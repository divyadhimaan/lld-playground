package strategy;

// The strategy interface declares operations common to all
// supported versions. The context uses this
// interface to call the algorithm defined by the concrete
// strategies.
interface Strategy {
    int execute(int a, int b);
}

// Concrete strategies implement the algorithm while following
// the base strategy interface. The interface makes them
// interchangeable in the context.
class ConcreteStrategyAdd implements Strategy {
    @Override
    public int execute(int a, int b) {
        return a + b;
    }
}

class ConcreteStrategySubtract implements Strategy {
    @Override
    public int execute(int a, int b) {
        return a - b;
    }
}

class ConcreteStrategyMultiply implements Strategy {
    @Override
    public int execute(int a, int b) {
        return a * b;
    }
}

// The context defines the interface of interest to clients.
class Context {
    // The context maintains a reference to one of the strategy
    // objects. The context doesn't know the concrete class of a
    // strategy. It should work with all strategies via the
    // strategy interface.
    private Strategy strategy;

    // Usually the context accepts a strategy through the
    // constructor, and also provides a setter so that the
    // strategy can be switched at runtime.
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    // The context delegates some work to the strategy object
    // instead of implementing multiple versions of the
    // algorithm on its own.
    public int executeStrategy(int a, int b) {
        return strategy.execute(a, b);
    }
}

// The client code picks a concrete strategy and passes it to
// the context. The client should be aware of the differences
// between strategies in order to make the right choice.
public class StrategyExample {
    public static void main(String[] args) {
        Context context = new Context();
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        System.out.print("Enter first number: ");
        int num1 = scanner.nextInt();

        System.out.print("Enter second number: ");
        int num2 = scanner.nextInt();

        System.out.print("Enter operation (addition -> '+', subtraction -> '-', multiplication -> '*'): ");
        String action = scanner.next();

        // Set the strategy based on user input
        if (action.equals("+")) {
            context.setStrategy(new ConcreteStrategyAdd());
        } else if (action.equals("-")) {
            context.setStrategy(new ConcreteStrategySubtract());
        } else if (action.equals("*")) {
            context.setStrategy(new ConcreteStrategyMultiply());
        }

        // Execute the strategy and display the result
        int result = context.executeStrategy(num1, num2);
        System.out.println("Result: " + result);
    }
}
