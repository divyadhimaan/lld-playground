package controller;

import model.*;
import repository.TheatreInventory;
import service.PaymentProcessor;

import java.util.Scanner;

public class BookingManager {
    private final TheatreInventory theatreInventory;

    public BookingManager(TheatreInventory inventory) {
        this.theatreInventory = inventory;
    }

    public void bookMovie(User user) {
        if (user == null) {
            System.out.println("[ERROR]: Invalid user.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Initiating Movie Booking....");

        Selection selection = new Selection();

        if (!selection.chooseMovie(scanner, theatreInventory)) return;
        if (!selection.chooseTheatre(scanner, theatreInventory)) return;
        if (!selection.chooseShow(scanner, theatreInventory)) return;
        if (!selection.chooseSeats(scanner)) return;

        double amount = selection.getSelectedShow().calculateTotalPrice(selection.getSelectedSeats());
        System.out.println("Total amount: ₹" + amount);

        Payment payment = PaymentProcessor.initiatePayment(scanner, amount);
        if (payment == null || payment.getStatus() != PaymentStatus.SUCCESS) {
            System.out.println("[ERROR]: Payment failed. Booking cancelled.");
            return;
        }

        Booking booking = new Booking.BookingBuilder()
                .bookingBy(user)
                .bookedMovie(selection.getSelectedMovie())
                .bookedTheatre(selection.getSelectedTheatre())
                .bookedShow(selection.getSelectedShow())
                .bookedSeats(selection.getSelectedSeats())
                .payment(payment)
                .build();
        selection.getSelectedShow().changeStatus(selection.getSelectedSeats(), SeatStatus.BOOKED);
        user.addBooking(booking);

        System.out.println("[INFO]: Booking completed successfully!");
        booking.displayBooking();
    }
}
