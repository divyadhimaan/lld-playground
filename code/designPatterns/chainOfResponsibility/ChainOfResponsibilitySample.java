package chainOfResponsibility;

// Handler interface
abstract class SupportHandler {
    protected SupportHandler nextHandler;

    public void setNextHandler(SupportHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void handleRequest(String issue, int priority);
}

// Concrete Handlers
class Level1Support extends SupportHandler {
    public void handleRequest(String issue, int priority) {
        if (priority <= 1) {
            System.out.println("Level 1 Support: Handling basic issue - " + issue);
        } else if (nextHandler != null) {
            nextHandler.handleRequest(issue, priority);
        }
    }
}

class Level2Support extends SupportHandler {
    public void handleRequest(String issue, int priority) {
        if (priority <= 2) {
            System.out.println("Level 2 Support: Handling intermediate issue - " + issue);
        } else if (nextHandler != null) {
            nextHandler.handleRequest(issue, priority);
        }
    }
}

class Level3Support extends SupportHandler {
    public void handleRequest(String issue, int priority) {
        if (priority <= 3) {
            System.out.println("Level 3 Support: Handling complex issue - " + issue);
        } else if (nextHandler != null) {
            nextHandler.handleRequest(issue, priority);
        }
    }
}

class ManagerSupport extends SupportHandler {
    public void handleRequest(String issue, int priority) {
        System.out.println("Manager: Handling critical issue - " + issue);
    }
}

// Usage
public class ChainOfResponsibilitySample {
    public static void main(String[] args) {
        // Create chain
        SupportHandler level1 = new Level1Support();
        SupportHandler level2 = new Level2Support();
        SupportHandler level3 = new Level3Support();
        SupportHandler manager = new ManagerSupport();

        // Set up chain
        level1.setNextHandler(level2);
        level2.setNextHandler(level3);
        level3.setNextHandler(manager);

        // Handle requests
        level1.handleRequest("Password reset", 1);
        level1.handleRequest("Software installation", 2);
        level1.handleRequest("Network configuration", 3);
        level1.handleRequest("System crash", 4);

        // Output:
        // Level 1 Support: Handling basic issue - Password reset
        // Level 2 Support: Handling intermediate issue - Software installation
        // Level 3 Support: Handling complex issue - Network configuration
        // Manager: Handling critical issue - System crash
    }
}

