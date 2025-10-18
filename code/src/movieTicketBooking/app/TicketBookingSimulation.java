package app;

import controller.TicketBookingController;
import model.Seat;
import model.SeatType;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicketBookingSimulation {
    public static void main(String[] args) {
        TicketBookingController controller = new TicketBookingController();
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== Welcome to Movie Ticket Booking System =====");

        // ===== Hardcoded Admin Setup =====
        String adminName = "AdminUser";
        String adminPassword = "ADMIN@123";
        String adminId = controller.registerUser("Admin", adminName, adminPassword);

        if (!adminId.isEmpty()) {
            System.out.println("[INFO]: Admin registered successfully.");

            // Hardcoded theatres
            controller.addTheatre(adminId, "PVR Cinemas");
            controller.addTheatre(adminId, "INOX");
            controller.addTheatre(adminId, "Miniplex");


            // Hardcoded shows and seats
            try {
                controller.addShow(adminId, "1", "Avengers: Endgame", "2025-10-20", "20-10-2025 10:00", "20-10-2025 13:00");
                controller.addShow(adminId, "2", "Avengers: Endgame", "2025-10-21", "20-10-2025 10:00", "20-10-2025 13:00");

                controller.addShow(adminId, "1", "Avatar 2", "2025-10-21", "21-10-2025 16:00", "21-10-2025 18:00");
                controller.addShow(adminId, "2", "Avatar 2", "2025-10-20", "21-10-2025 14:00", "21-10-2025 17:00");
                controller.addShow(adminId, "3", "Avatar 2", "2025-10-21", "21-10-2025 10:30", "21-10-2025 12:45");

                List<Seat> avengersSeats = new ArrayList<>();
                avengersSeats.add(new Seat("A1", SeatType.REGULAR));
                avengersSeats.add(new Seat("A2", SeatType.REGULAR));
                avengersSeats.add(new Seat("A3", SeatType.PREMIUM));
                controller.updateSeating(adminId, "1", "Avengers: Endgame", "s2", avengersSeats);

                List<Seat> avatarSeats = new ArrayList<>();
                avatarSeats.add(new Seat("B1", SeatType.REGULAR));
                avatarSeats.add(new Seat("B2", SeatType.PREMIUM));
                avatarSeats.add(new Seat("B3", SeatType.PREMIUM));
                controller.updateSeating(adminId, "1", "Avatar 2", "s4", avatarSeats);

            } catch (ParseException e) {
                System.out.println("[ERROR]: Failed to add hardcoded shows/seats.");
            }
        }

        // ===== Customer Interaction =====
        while (true) {
            System.out.println("\nSelect User Type:");
            System.out.println("1. Customer");
            System.out.println("2. Exit");
            System.out.print("Choice: ");
            String userTypeChoice = scanner.nextLine().trim();

            switch (userTypeChoice) {
                case "1" -> handleCustomer(controller, scanner);
                case "2" -> {
                    System.out.println("Exiting system. Goodbye!");
                    return;
                }
                default -> System.out.println("[ERROR]: Invalid choice. Try again.");
            }
        }
    }

    private static void handleCustomer(TicketBookingController controller, Scanner scanner) {
        System.out.print("Enter customer name: ");
        String custName = scanner.nextLine();
        System.out.print("Enter password: ");
        String custPass = scanner.nextLine();

        String customerId = controller.registerUser("Customer", custName, custPass);
        if (customerId.isEmpty()) return;

        while (true) {
            System.out.println("\n===== Customer Menu =====");
            System.out.println("1. Display All Upcoming Shows");
            System.out.println("2. Book a Show");
            System.out.println("3. View My Bookings");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> controller.displayAllUpcomingShows();
                case "2" -> controller.bookShow(customerId);
                case "3" -> {
                    System.out.println("===== Your Bookings =====");
                    controller.getBookingsForUser(customerId);
                }
                case "4" -> {
                    return; // back to main menu
                }
                default -> System.out.println("[ERROR]: Invalid choice. Try again.");
            }
        }
    }
}
