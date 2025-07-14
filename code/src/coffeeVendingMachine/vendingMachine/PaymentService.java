package vendingMachine;

class PaymentService {
    private static PaymentService payment;

    public static synchronized PaymentService getInstance() {
        if (payment == null) {
            payment = new PaymentService();
        }
        return payment;
    }

    public void processPayment(int paidAmount, int productPrice) {
        if (paidAmount > productPrice) {
            System.out.println("Payment successful. Change returned: $" + (paidAmount - productPrice));
        } else if (paidAmount == productPrice) {
            System.out.println("Payment successful. No change to return.");
        } else {
            System.out.println("Insufficient payment. Please pay at least $" + productPrice);
        }
    }

}
