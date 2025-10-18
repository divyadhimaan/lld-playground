package model;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class Booking {

    private final String bookingId;
    private final User bookingBy;
    private final Theatre bookedTheatre;
    private final Movie bookedMovie;
    private final Show bookedShow;
    private final List<Seat> bookedSeats;
    private final Payment payment;

    // Private constructor to enforce Builder usage
    private Booking(BookingBuilder builder) {
        this.bookingId = builder.bookingId;
        this.bookingBy = builder.bookingBy;
        this.bookedTheatre = builder.bookedTheatre;
        this.bookedMovie = builder.bookedMovie;
        this.bookedShow = builder.bookedShow;
        this.bookedSeats = builder.bookedSeats;
        this.payment = builder.payment;
    }

    public void displayBooking() {
        System.out.println("===== Booking Confirmation =====");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Booked By: " + (bookingBy != null ? bookingBy.getUserName() : "Guest"));
        System.out.println("Movie: " + (bookedMovie != null ? bookedMovie.getMovieName() : "Not Selected"));
        System.out.println("Theatre: " + (bookedTheatre != null ? bookedTheatre.getTheatreName() : "Not Selected"));
        if (bookedShow != null) {
            System.out.println("Show ID: " + bookedShow.getShowId());
            System.out.println("Date: " + bookedShow.getShowDate());
            System.out.println("Start Time: " + bookedShow.getStartTime());
            System.out.println("End Time: " + bookedShow.getEndTime());
        }
        if (bookedSeats != null && !bookedSeats.isEmpty()) {
            System.out.println("Booked Seats:");
            for (Seat seat : bookedSeats) {
                System.out.println("  Seat ID: " + seat.getSeatId() +
                        " | Type: " + seat.getSeatType() +
                        " | Status: " + seat.getSeatStatus());
            }
        }
        if (payment != null) payment.displayPaymentInfo();
        System.out.println("================================");
    }

    // ===================== Builder =====================
    public static class BookingBuilder {
        private final String bookingId = UUID.randomUUID().toString();
        private User bookingBy;
        private Theatre bookedTheatre;
        private Movie bookedMovie;
        private Show bookedShow;
        private List<Seat> bookedSeats;
        private Payment payment;

        public BookingBuilder bookingBy(User user) {
            this.bookingBy = user;
            return this;
        }

        public BookingBuilder bookedTheatre(Theatre theatre) {
            this.bookedTheatre = theatre;
            return this;
        }

        public BookingBuilder bookedMovie(Movie movie) {
            this.bookedMovie = movie;
            return this;
        }

        public BookingBuilder bookedShow(Show show) {
            this.bookedShow = show;
            return this;
        }

        public BookingBuilder bookedSeats(List<Seat> seats) {
            this.bookedSeats = seats;
            return this;
        }

        public BookingBuilder payment(Payment payment) {
            this.payment = payment;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }
}
