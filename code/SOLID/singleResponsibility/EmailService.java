class EmailService {
    public void sendConfirmationEmail(OrderAfterSRP order) {
        System.out.println("Connecting to email server...");
        System.out.println("Sending confirmation to " + order.getCustomerEmail());
        System.out.println("Email sent successfully!");
    }
}
