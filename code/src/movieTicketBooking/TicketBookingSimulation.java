import controller.TicketBookingController;

public class TicketBookingSimulation {
    public static void main(String[] args){
        TicketBookingController controller = new TicketBookingController();

        String userId = controller.registerUser("Admin", "Alice", "abcADMINabc");

        String theatreId = controller.addTheatre(userId, "PVR");

        controller.registerUser("Customer", "Bob", "mypassword");



    }
}
