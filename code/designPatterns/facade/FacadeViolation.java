package facade;

// Subsystems
class BillingDept {
    public void handleBillingIssue() {
        System.out.println("Billing issue resolved");
    }
}

class TechSupportDept {
    public void handleTechIssue() {
        System.out.println("Technical issue resolved");
    }
}

class ShippingDept {
    public void handleShippingIssue() {
        System.out.println("Shipping issue resolved");
    }
}

// Client directly interacts with subsystems
public class FacadeViolation {
    public static void main(String[] args) {
        BillingDept billing = new BillingDept();
        TechSupportDept tech = new TechSupportDept();
        ShippingDept shipping = new ShippingDept();

        // Customer needs to know which dept to call
        billing.handleBillingIssue();
        tech.handleTechIssue();
        shipping.handleShippingIssue();
    }
}
