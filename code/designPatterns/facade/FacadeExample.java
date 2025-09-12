package facade;

// Subsystems remain unchanged
class BillingDept2 {
    public void handleBillingIssue() {
        System.out.println("Billing issue resolved");
    }
}

class TechSupportDept2 {
    public void handleTechIssue() {
        System.out.println("Technical issue resolved");
    }
}

class ShippingDept2 {
    public void handleShippingIssue() {
        System.out.println("Shipping issue resolved");
    }
}

// Facade
class CustomerCare {
    private BillingDept2 billing;
    private TechSupportDept2 tech;
    private ShippingDept2 shipping;

    public CustomerCare(BillingDept2 billing, TechSupportDept2 tech, ShippingDept2 shipping) {
        this.billing = billing;
        this.tech = tech;
        this.shipping = shipping;
    }

    public void resolveIssue(String type) {
        System.out.println("Customer Care received issue: " + type);

        switch (type.toLowerCase()) {
            case "billing":
                billing.handleBillingIssue();
                break;
            case "technical":
                tech.handleTechIssue();
                break;
            case "shipping":
                shipping.handleShippingIssue();
                break;
            default:
                System.out.println("Unknown issue, please hold...");
        }
    }
}

// Client only interacts with Facade
public class FacadeExample {
    public static void main(String[] args) {
        CustomerCare cc = new CustomerCare(new BillingDept2(), new TechSupportDept2(), new ShippingDept2());

        cc.resolveIssue("billing");
        cc.resolveIssue("technical");
        cc.resolveIssue("shipping");
    }
}

