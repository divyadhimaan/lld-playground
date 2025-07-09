package chainOfResponsibility;

public class ChainOfResponsibilityViolation {

    public void handleRequest(String issue, int priority) {
        if (priority <= 1) {
            System.out.println("Level 1 Support: Handling basic issue - " + issue);
        } else if (priority == 2) {
            System.out.println("Level 2 Support: Handling intermediate issue - " + issue);
        } else if (priority == 3) {
            System.out.println("Level 3 Support: Handling complex issue - " + issue);
        } else {
            System.out.println("Manager: Handling critical issue - " + issue);
        }
    }

    public static void main(String[] args) {
        ChainOfResponsibilityViolation service = new ChainOfResponsibilityViolation();

        service.handleRequest("Password reset", 1);
        service.handleRequest("Software installation", 2);
        service.handleRequest("Network configuration", 3);
        service.handleRequest("System crash", 4);
    }
}
