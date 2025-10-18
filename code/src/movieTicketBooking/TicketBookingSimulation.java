import controller.TicketBookingController;

import java.text.ParseException;

public class TicketBookingSimulation {
    public static void main(String[] args) {
        TicketBookingController controller = new TicketBookingController();

        String adminUserId = controller.registerUser("Admin", "Alice", "abcADMINabc");

        String theatreIdPVR = controller.addTheatre(adminUserId, "PVR");
        String theatreIdINOX = controller.addTheatre(adminUserId, "INOX");
        String theatreIdMiniPlex = controller.addTheatre(adminUserId, "MiniPlex");


        try{
            controller.addShow(adminUserId, theatreIdPVR, "Top Gun", "2025-10-20", "20-10-2025 18:30", "20-10-2025 21:30");
            controller.addShow(adminUserId, theatreIdINOX, "Top Gun", "2025-10-21", "20-10-2025 10:30", "20-10-2025 13:30");
            controller.addShow(adminUserId, theatreIdMiniPlex, "Barbie", "2025-10-21", "20-10-2025 16:30", "20-10-2025 18:30");
        }catch (ParseException e){
            System.out.println("[ERROR]: Parse Exception for Date/Time");
        }


        controller.displayUpcomingShowsForTheatre(theatreIdINOX);

        controller.registerUser("Customer", "Bob", "mypassword");

        controller.displayAllUpcomingShows();


    }
}
