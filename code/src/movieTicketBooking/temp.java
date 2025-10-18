//package controller;
//
//import model.*;
//import repository.TheatreInventory;
//
//import java.util.Scanner;
//
//public class BookingManager {
//    private final TheatreInventory theatreInventory;
//
//    public BookingManager(TheatreInventory inventory) {
//        this.theatreInventory = inventory;
//    }
//
//    public void bookMovie(User user) {
//        if (user == null) {
//            System.out.println("[ERROR]: Invalid user.");
//            return;
//        }
//
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Initiating Movie Booking....");
//
//        Selection selection = new Selection();
//
//        if (!selection.chooseMovie(scanner, theatreInventory)) return;
//        if (!selection.chooseTheatre(scanner, theatreInventory)) return;
//        if (!selection.chooseShow(scanner, theatreInventory)) return;
//        if (!selection.chooseSeats(scanner)) return;
//
//        double amount = selection.getSelectedShow().calculateTotalPrice(selection.getSelectedSeats());
//        System.out.println("Total amount: ₹" + amount);
//
//        Payment payment = PaymentProcessor.initiatePayment(scanner, amount);
//        if (payment == null || payment.getStatus() != PaymentStatus.SUCCESS) {
//            System.out.println("[ERROR]: Payment failed. Booking cancelled.");
//            return;
//        }
//
//        Booking booking = new Booking(user, selection, payment);
//        selection.getSelectedShow().changeStatus(selection.getSelectedSeats(), SeatStatus.BOOKED);
//        user.addBooking(booking);
//
//        System.out.println("[INFO]: Booking completed successfully!");
//        booking.displayBooking();
//    }
//}
//
//package controller;
//
//import model.Payment;
//import strategy.CreditCardPayment;
//import strategy.PaymentStrategy;
//import strategy.UpiPayment;
//import model.PaymentStatus;
//
//import java.util.Scanner;
//
//public class PaymentProcessor {
//    public static Payment initiatePayment(Scanner scanner, double amount) {
//        Payment payment = null;
//        boolean paymentSuccess = false;
//        while(!paymentSuccess){
//            System.out.println("Select Payment Method:");
//            System.out.println("1. UPI");
//            System.out.println("2. Credit Card");
//            System.out.print("Enter choice (1/2): ");
//            String choice = scanner.nextLine();
//
//            PaymentStrategy strategy;
//            switch (choice) {
//                case "1" -> {
//                    System.out.print("Enter UPI ID: ");
//                    strategy = new UpiPayment(scanner.nextLine());
//                }
//                case "2" -> {
//                    System.out.print("Enter Card Number: ");
//                    String cardNumber = scanner.nextLine();
//                    System.out.print("Enter Holder Name: ");
//                    String holder = scanner.nextLine();
//                    System.out.print("Enter Expiry (MM/YY): ");
//                    String expiry = scanner.nextLine();
//                    System.out.print("Enter CVV: ");
//                    String cvv = scanner.nextLine();
//                    strategy = new CreditCardPayment(cardNumber, holder, expiry, cvv);
//                }
//                default -> {
//                    System.out.println("Invalid payment option.");
//                    continue;
//                }
//            }
//
//            payment = new Payment(amount, strategy);
//            payment.processPayment();
//
//            if (payment.getStatus() == PaymentStatus.SUCCESS) {
//                paymentSuccess = true;
//                System.out.println("[INFO]: Payment successful!");
//            } else {
//                System.out.println("[ERROR]: Payment failed.");
//                System.out.print("Would you like to retry payment? (yes/no): ");
//                String retry = scanner.nextLine().trim().toLowerCase();
//
//                if (!retry.equals("yes")) {
//                    System.out.println("[INFO]: Payment cancelled by user.");
//                    return payment;
//                }
//            }
//        }
//        return payment;
//    }
//}
//
//package controller;
//
//
//import service.TicketBookingService;
//
//import java.text.ParseException;
//import java.util.Scanner;
//
////facade layer
//public class TicketBookingController {
//    private final TicketBookingService service;
//
//    public TicketBookingController(){
//        this.service = TicketBookingService.getInstance();
//    }
//    public String registerUser(String userType, String userName, String password){
//        return service.registerUser(userType, userName, password);
//    }
//
//    public String addTheatre(String userId, String theatreName){
//        return service.registerTheatre(userId, theatreName);
//    }
//
//    public void addShow(String userId, String theatreId, String movieName, String showDate, String startTime, String endTime) throws ParseException {
//        service.addShowToTheatre(userId, theatreId, movieName, showDate, startTime, endTime);
//    }
//
//    public void displayUpcomingShowsForTheatre(String theatreId){
//        service.displayShowForTheatre(theatreId);
//    }
//
//    public void displayAllUpcomingShows(){
//        service.displayAllUpcomingShows();
//    }
//
//    public void bookShow(String userId){
//        service.booking(userId);
//    }
//}
//
//package factory;
//
//import model.Theatre;
//import model.User;
//import model.UserType;
//
//public class TheatreFactory {
//
//    public Theatre createTheatre(User user, String theatreName){
//        if(!verifyUserAsAdmin(user)){
//            System.out.println("[ERROR]: User doesn't have create theatre access");
//        }
//
//        return new Theatre(theatreName, user);
//    }
//
//    private boolean verifyUserAsAdmin(User user){
//        return user.getUserType().equals(UserType.ADMIN);
//    }
//}
//
//
//package factory;
//
//import model.User;
//import model.UserType;
//
//public class UserFactory {
//    private final String verificationCode;
//
//    public UserFactory(String code){
//        this.verificationCode = code;
//    }
//    public User createUser(String userType, String userName, String password){
//        User user = null;
//        if (userType.equalsIgnoreCase("ADMIN")) {
//            if(verifyAdmin(password)){
//                user = new User(userName, UserType.ADMIN, "ADMIN", password);
//            }else {
//                System.out.println("[ERROR]: Verification for Admin failed");
//            }
//        }else if(userType.equalsIgnoreCase("CUSTOMER")){
//            user = new User(userName, UserType.CUSTOMER, "CUST", password);
//        }
//        return user;
//    }
//
//    // change verification logic later
//    private boolean verifyAdmin(String password){
//        return password.contains(verificationCode);
//    }
//}
//
//package model;
//
//import lombok.Getter;
//
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//@Getter
//public class Booking {
//    private final String bookingId;
//    private final User bookingBy;
//    private final Theatre bookedTheatre;
//    private final Movie bookedMovie;
//    private final Show bookedShow;
//    private final List<Seat> bookedSeats;
//    private final Payment payment;
//
//    public Booking(User user, Selection selection, Payment payment){
//        this.bookingId = String.valueOf(UUID.randomUUID());
//        this.bookingBy = user;
//        this.bookedTheatre = selection.getSelectedTheatre();
//        this.bookedMovie = selection.getSelectedMovie();
//        this.bookedShow = selection.getSelectedShow();
//        this.bookedSeats = selection.getSelectedSeats();
//        this.payment = payment;
//    }
//
//    public void displayBooking() {
//        System.out.println("===== Booking Confirmation =====");
//        System.out.println("Booking ID: " + bookingId);
//        System.out.println("Booked By: " + (bookingBy != null ? bookingBy.getUserName() : "Guest"));
//
//        if (bookedMovie != null)
//            System.out.println("Movie: " + bookedMovie.getMovieName());
//        else
//            System.out.println("Movie: Not Selected");
//
//        if (bookedTheatre != null)
//            System.out.println("Theatre: " + bookedTheatre.getTheatreName());
//        else
//            System.out.println("Theatre: Not Selected");
//
//        if (bookedShow != null) {
//            System.out.println("Show ID: " + bookedShow.getShowId());
//            System.out.println("Date: " + bookedShow.getShowDate());
//            System.out.println("Start Time: " + bookedShow.getStartTime());
//            System.out.println("End Time: " + bookedShow.getEndTime());
//        } else {
//            System.out.println("Show: Not Selected");
//        }
//        if (bookedSeats != null && !bookedSeats.isEmpty()) {
//            System.out.println("Booked Seats:");
//            for (Seat seat : bookedSeats) {
//                System.out.println("  Seat ID: " + seat.getSeatId() +
//                        " | Type: " + seat.getSeatType() +
//                        " | Status: " + seat.getSeatStatus());
//            }
//        } else {
//            System.out.println("No seats selected.");
//        }
//
//        payment.displayPaymentInfo();
//
//
//        System.out.println("================================");
//    }
//}
//
//package model;
//
//import lombok.Getter;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class Movie {
//    private static final AtomicInteger id = new AtomicInteger(0);
//    @Getter
//    private final String movieId;
//    @Getter
//    private final String movieName;
//    @Getter
//    private final List<Theatre> theatres;
//
//    public Movie(String name){
//        this.movieId = name + String.valueOf(id.incrementAndGet());
//        this.movieName = name;
//        this.theatres = new ArrayList<>();
//    }
//
//}
//package model;
//
//
//import lombok.Getter;
//import lombok.Setter;
//import strategy.PaymentStrategy;
//
//import java.util.UUID;
//
//@Getter
//public class Payment {
//    private final String paymentId;
//    private final PaymentStrategy strategy;
//    private final double amount;
//    @Setter
//    private PaymentStatus status;
//
//    public Payment(double amount, PaymentStrategy strategy){
//        this.paymentId = 'p' + String.valueOf(UUID.randomUUID());
//        this.amount = amount;
//        this.strategy = strategy;
//        this.status = PaymentStatus.PENDING;
//    }
//
//    public void processPayment(){
//        System.out.println("Initiating Payment");
//        status = strategy.pay(amount) ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;
//    }
//
//    public void displayPaymentInfo() {
//        System.out.println("===== Payment Details =====");
//        System.out.println("Payment ID: " + paymentId);
//        System.out.println("Amount: ₹" + amount);
//        System.out.println("Method: " + strategy.getClass().getSimpleName());
//        System.out.println("Status: " + status);
//        System.out.println("===========================");
//    }
//}
//
//package model;
//
//public enum PaymentStatus {
//    PENDING,
//    FAILED,
//    SUCCESS
//}package model;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.concurrent.atomic.AtomicInteger;
//
//@Getter
//public class Seat {
//    private static final AtomicInteger id = new AtomicInteger(0);
//    private final String seatId;
//    private final SeatType seatType;
//    @Setter
//    private SeatStatus seatStatus;
//
//    public Seat(SeatType type){
//        this.seatId = type.name().toLowerCase().substring(0,1) + id.incrementAndGet();
//        this.seatType = type;
//        this.seatStatus=SeatStatus.AVAILABLE;
//    }
//
//    public boolean isAvailable() {
//        return this.seatStatus == SeatStatus.AVAILABLE;
//    }
//
//    public void display() {
//        System.out.println("Seat: " + seatId + " | Type: " + seatType + " | Status: " + seatStatus);
//    }
//}
//
//package model;
//
//public enum SeatStatus {
//    AVAILABLE,
//    BOOKED,
//    HELD
//}
//
//package model;
//
//public enum SeatType {
//    REGULAR,
//    PREMIUM
//}
//package model;
//
//import lombok.Getter;
//import lombok.Setter;
//import repository.TheatreInventory;
//
//import java.util.*;
//
//@Getter @Setter
//public class Selection {
//    private Movie selectedMovie;
//    private Theatre selectedTheatre;
//    private Show selectedShow;
//    private Map<Theatre, List<Show>> theatreShowsMap = new HashMap<>();
//    private List<Seat> selectedSeats;
//
//
//    // Constructor
//    public Selection() {
//        this.selectedMovie = null;
//        this.selectedTheatre = null;
//        this.selectedShow = null;
//        this.selectedSeats = new ArrayList<>();
//    }
//
//    public boolean chooseMovie(Scanner scanner, TheatreInventory inventory) {
//        System.out.println("Pick a movie from following:");
//        inventory.displayAllUpcomingMovies();
//        String name = scanner.nextLine();
//        selectedMovie = inventory.getMovieByName(name);
//        if (selectedMovie == null) {
//            System.out.println("Invalid movie selection.");
//            return false;
//        }
//        return true;
//    }
//
//    public boolean chooseTheatre(Scanner scanner, TheatreInventory inventory) {
//        System.out.println("Pick a theatre from following:");
//        inventory.displayTheatresAndShowsForMovie(this);
//        String theatreName = scanner.nextLine();
//        selectedTheatre = inventory.getTheatreByName(theatreName);
//        if (selectedTheatre == null) {
//            System.out.println("Invalid theatre selection.");
//            return false;
//        }
//        return true;
//    }
//
//    public boolean chooseShow(Scanner scanner, TheatreInventory inventory) {
//        System.out.println("Pick a show from following (Enter show ID):");
//        displayShowsForSelectedTheatre();
//        String showId = scanner.nextLine();
//        selectedShow = inventory.getShowById(selectedTheatre, showId);
//        if (selectedShow == null) {
//            System.out.println("Invalid show selection.");
//            return false;
//        }
//        return true;
//    }
//
//    public boolean chooseSeats(Scanner scanner) {
//        selectedShow.displaySeats();
//        System.out.println("Pick seats (comma separated): ");
//        String input = scanner.nextLine().trim();
//        if (input.isEmpty()) {
//            System.out.println("No seats entered.");
//            return false;
//        }
//
//        String[] seatIds = input.split(",");
//        for (String id : seatIds) {
//            Seat seat = selectedShow.getSeatById(id.trim());
//            if (seat != null && seat.isAvailable()) {
//                selectedSeats.add(seat);
//            } else {
//                System.out.println("Seat " + id + " invalid or unavailable.");
//            }
//        }
//
//        selectedShow.changeStatus(selectedSeats, SeatStatus.HELD);
//        return !selectedSeats.isEmpty();
//    }
//
////    public void displayShowsForSelectedTheatre() {
////        selectedTheatre.displayShowsForMovie(selectedMovie);
////    }
//
//
//
//    public void displaySelection() {
//        System.out.println("===== Current Selection =====");
//
//        System.out.println("Selected Movie: " + (selectedMovie != null ? selectedMovie.getMovieName() : "None"));
//        System.out.println("Selected Theatre: " + (selectedTheatre != null ? selectedTheatre.getTheatreName() : "None"));
//
//        if(selectedShow==null)
//            displayTheatreShowMap();
//        else
//            System.out.println("Selected Show: " +
//                    "  Show ID: " + selectedShow.getShowId() +
//                    " | Date: " + selectedShow.getShowDate() +
//                    " | Start: " + selectedShow.getStartTime() +
//                    " | End: " + selectedShow.getEndTime()
//            );
//
//        System.out.print("Selected Seats: ");
//        if(selectedSeats.isEmpty()){
//            System.out.println("None");
//        }else{
//            for(Seat seat: selectedSeats){
//                System.out.println(
//                        "  Seat ID: " + seat.getSeatId() +
//                                " | Seat Type: " + seat.getSeatType()
//                );
//            }
//        }
//
//        System.out.println("=================================");
//    }
//
//    public void displayTheatreShowMap() {
//        System.out.println("===== Theatres and Upcoming Shows for Selected Movie =====");
//
//        if (theatreShowsMap == null || theatreShowsMap.isEmpty()) {
//            System.out.println("No theatre-show mapping available yet.");
//        } else {
//            theatreShowsMap.forEach((theatre, shows) -> {
//                System.out.println("Theatre: " + theatre.getTheatreName());
//                if (shows.isEmpty()) {
//                    System.out.println("  No upcoming shows.");
//                } else {
//                    shows.forEach(show -> System.out.println(
//                            "  Show ID: " + show.getShowId() +
//                                    " | Date: " + show.getShowDate() +
//                                    " | Start: " + show.getStartTime() +
//                                    " | End: " + show.getEndTime()
//                    ));
//                }
//            });
//        }
//
//        System.out.println("======================================================");
//    }
//
//    public void displayShowsForSelectedTheatre() {
//        if (theatreShowsMap == null || theatreShowsMap.isEmpty()) {
//            System.out.println("No theatre-show mapping available yet.");
//            return;
//        }
//
//        if(selectedTheatre==null){
//            System.out.println("No selected theatre.");
//            return;
//        }
//
//        List<Show> shows = theatreShowsMap.get(selectedTheatre);
//        if (shows == null || shows.isEmpty()) {
//            System.out.println("No upcoming shows for theatre: " + selectedTheatre.getTheatreName());
//            return;
//        }
//
//        System.out.println("===== Upcoming Shows for Theatre: " + selectedTheatre.getTheatreName() + " =====");
//        shows.forEach(show -> System.out.println(
//                "Show ID: " + show.getShowId() +
//                        " | Date: " + show.getShowDate() +
//                        " | Start: " + show.getStartTime() +
//                        " | End: " + show.getEndTime()
//        ));
//        System.out.println("======================================================");
//    }
//
//    public boolean validate(){
//        return getSelectedMovie() != null && selectedShow != null && selectedTheatre!=null && selectedSeats!=null && !selectedSeats.isEmpty();
//    }
//
//}
//
//
//package model;
//
//import lombok.Getter;
//
//import java.time.LocalDateTime;
//import java.util.*;
//        import java.util.concurrent.atomic.AtomicInteger;
//
//public class Show {
//    private static final AtomicInteger id = new AtomicInteger(0);
//    @Getter
//    private final String showId;
//    @Getter
//    private final Date showDate;
//    @Getter
//    private final LocalDateTime startTime;
//    @Getter
//    private final LocalDateTime endTime;
//    private final List<Seat> seats;
//    private final Map<SeatType, Double> seatPricing;
//
//
//    public Show(Date showDate, LocalDateTime startTime, LocalDateTime endTime){
//        this.showId = 's' + String.valueOf(id.incrementAndGet());
//        this.showDate =showDate;
//        this.startTime=startTime;
//        this.endTime=endTime;
//        this.seats = initializeSeats();
//        this.seatPricing = initializeSeatPricing();
//    }
//
//    private Map<SeatType, Double> initializeSeatPricing() {
//        Map<SeatType, Double> pricing = new HashMap<>();
//        pricing.put(SeatType.REGULAR, 150.0);
//        pricing.put(SeatType.PREMIUM, 250.0);
//        return pricing;
//    }
//
//    public double getPriceForSeat(Seat seat) {
//        return seatPricing.getOrDefault(seat.getSeatType(), 0.0);
//    }
//
//    public double calculateTotalPrice(List<Seat> selectedSeats) {
//        double total = 0;
//        for (Seat seat : selectedSeats) {
//            total += getPriceForSeat(seat);
//        }
//        return total;
//    }
//
//    private List<Seat> initializeSeats() {
//        List<Seat> seatList = new ArrayList<>();
//        // Example: 10 Regular, 5 Premium
//        for (int i = 0; i < 10; i++) seatList.add(new Seat(SeatType.REGULAR));
//        for (int i = 0; i < 5; i++) seatList.add(new Seat(SeatType.PREMIUM));
//        return seatList;
//    }
//
//    public void changeStatus(List<Seat> selectedSeats, SeatStatus status){
//        for(Seat seat: selectedSeats){
//            seat.setSeatStatus(status);
//        }
//    }
//
//    public Seat getSeatById(String seatId) {
//        for (Seat seat : seats) {
//            if (seat.getSeatId().equalsIgnoreCase(seatId)) {
//                return seat;
//            }
//        }
//        return null;
//    }
//    public void displaySeats() {
//        System.out.println("===== Seat Layout for Show " + showId + " =====");
//        for (Seat seat : seats) {
//            seat.display();
//        }
//        System.out.println("===============================================");
//    }
//
//    public String getShowTime(){
//        return this.startTime.toString();
//    }
//}
//
//package model;
//
//import lombok.Getter;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicInteger;
//@Getter
//public class Theatre {
//    private static final AtomicInteger id = new AtomicInteger(0);
//    private final String theatreId;
//    private final String theatreName;
//    private final int numberOfScreens;
//    private final Map<Movie, List<Show>> showsAvailable;
//    private final User registeredBy;
//
//    public Theatre(String name, User registeredByUser){
//        this.theatreId = String.valueOf(id.incrementAndGet());
//        this.theatreName = name;
//        this.numberOfScreens = 1;
//        this.showsAvailable = new HashMap<Movie, List<Show>>();
//        this.registeredBy = registeredByUser;
//    }
//
//    public void addShow(Movie movie, Show show){
//        if(show==null) {
//            System.out.println("[ERROR]: Invalid Show");
//        }
//        if(movie==null){
//            System.out.println("[ERROR]: Invalid Movie");
//        }
//        if(showsAvailable.containsKey(movie)){
//            showsAvailable.get(movie).add(show);
//        }
//        showsAvailable.put(movie,List.of(show));
//    }
//
//    public void displayInfo() {
//        System.out.println("===== Theatre Info =====");
//        System.out.println("Theatre ID   : " + getTheatreId());
//        System.out.println("Theatre Name : " + getTheatreName());
//        System.out.println("Number of Screens: " + getNumberOfScreens());
//        System.out.println("Registered By: " + (registeredBy != null ? registeredBy.getUserName() : "N/A"));
//
//        if (showsAvailable.isEmpty()) {
//            System.out.println("No shows available.");
//        } else {
//            System.out.println("Shows Available:");
//            for (Map.Entry<Movie, List<Show>> entry : showsAvailable.entrySet()) {
//                Movie movie = entry.getKey();
//                List<Show> shows = entry.getValue();
//                System.out.println("  Movie: " + movie.getMovieName());
//                for (Show show : shows) {
//                    System.out.println("    Show ID: " + show.getShowId() + ", Time: " + show.getShowTime());
//                }
//            }
//        }
//        System.out.println("=======================");
//    }
//
//    public void displayUpcomingShows() {
//        System.out.println("===== Upcoming Shows in " + theatreName + " =====");
//
//        LocalDateTime now = LocalDateTime.now();
//        boolean found = false;
//
//        for (Map.Entry<Movie, List<Show>> entry : showsAvailable.entrySet()) {
//            Movie movie = entry.getKey();
//            List<Show> shows = entry.getValue();
//
//            for (Show show : shows) {
//                if (show.getStartTime().isAfter(now)) {
//                    if (!found) found = true;
//                    System.out.println("Movie: " + movie.getMovieName() +
//                            " | Show ID: " + show.getShowId() +
//                            " | Start: " + show.getStartTime() +
//                            " | End: " + show.getEndTime());
//                }
//            }
//        }
//
//        if (!found) {
//            System.out.println("No upcoming shows found.");
//        }
//
//        System.out.println("======================================");
//    }
//
//
//
//}
//
//package model;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class User {
//
//    private static final AtomicInteger id = new AtomicInteger(0);
//    @Getter
//    private final String userId;
//    @Getter
//    private final UserType userType;
//    @Getter
//    private final String userName;
//    private final String password;
//    @Getter @Setter
//    private List<Booking> bookings;
//
//
//    public User(String userName, UserType userType, String idGen, String password){
//        this.userId = idGen + id.incrementAndGet();
//        this.userName = userName;
//        this.userType = userType;
//        this.password = password;
//        this.bookings = new ArrayList<>();
//    }
//
//    public void displayInfo(){
//        System.out.println("===== User Info =====");
//        System.out.println("User ID   : " + getUserId());
//        System.out.println("User Name : " + getUserName());
//        System.out.println("User Type : " + getUserType());
//        System.out.println("=====================");
//    }
//
//    public void addBooking(Booking booking){
//        bookings.add(booking);
//    }
//
//}
//
//package model;
//
//public enum UserType {
//    ADMIN,
//    CUSTOMER
//}
//
//package repository;
//
//import lombok.Getter;
//import model.Movie;
//import model.Selection;
//import model.Show;
//import model.Theatre;
//
//import java.time.LocalDateTime;
//import java.util.*;
//
//public class TheatreInventory {
//    private static TheatreInventory instance;
//    @Getter
//    private final Map<String, Theatre> registeredTheatresMap;
//    private final Map<String, Movie> movieMap;
//
//    private TheatreInventory(){
//        this.registeredTheatresMap = new HashMap<>();
//        this.movieMap = new HashMap<>();
//    }
//    public static synchronized TheatreInventory getInstance(){
//        if(instance==null)
//            instance=new TheatreInventory();
//        return instance;
//    }
//
//    public void addTheatre(Theatre theatre){
//        if(theatre==null){
//            System.out.println("[ERROR]: Failed to add theatre to inventory");
//            return;
//        }
//        registeredTheatresMap.put(theatre.getTheatreId(), theatre);
//    }
//
//    public Theatre getTheatreById(String theatreId){
//        Theatre theatre = registeredTheatresMap.get(theatreId);
//        if(theatre==null){
//            System.out.println("[ERROR]: Accessing Invalid Theatre");
//        }
//        return theatre;
//    }
//
//    public Movie getMovieByName(String movieName){
//        return movieMap.getOrDefault(movieName.toLowerCase(), null);
//    }
//
//    public Theatre getTheatreByName(String theatreName) {
//        return registeredTheatresMap.values().stream()
//                .filter(t -> t.getTheatreName().equalsIgnoreCase(theatreName))
//                .findFirst()
//                .orElse(null);
//    }
//    public Show getShowById(Theatre theatre, String showId) {
//        if (theatre == null || theatre.getShowsAvailable() == null) return null;
//
//        for (List<Show> shows : theatre.getShowsAvailable().values()) {
//            for (Show show : shows) {
//                if (show.getShowId().equals(showId)) {
//                    return show;
//                }
//            }
//        }
//        return null;
//    }
//    public void addShowToTheatre(Theatre theatre, Movie movie, Show show){
//        movieMap.put(movie.getMovieName().toLowerCase(), movie);
//        theatre.addShow(movie, show);
//    }
//
//    public void displayUpcomingShowsForTheatre(Theatre theatre){
//        theatre.displayUpcomingShows();
//    }
//
//    public void displayAllUpcomingShows() {
//        System.out.println("===== Upcoming Shows Across All Theatres =====");
//
//        LocalDateTime now = LocalDateTime.now();
//        boolean found = false;
//
//        for (Theatre theatre : registeredTheatresMap.values()) {
//            for (Map.Entry<Movie, List<Show>> entry : theatre.getShowsAvailable().entrySet()) {
//                Movie movie = entry.getKey();
//                List<Show> shows = entry.getValue();
//
//                for (Show show : shows) {
//                    if (show.getStartTime().isAfter(now)) {
//                        found = true;
//                        System.out.println("Theatre: " + theatre.getTheatreName() +
//                                " | Movie: " + movie.getMovieName() +
//                                " | Date: " + show.getShowDate() +
//                                " | Start: " + show.getStartTime() +
//                                " | End: " + show.getEndTime());
//                    }
//                }
//            }
//        }
//
//        if (!found) {
//            System.out.println("No upcoming shows found in any theatre.");
//        }
//
//        System.out.println("==============================================");
//    }
//
//    public void displayAllUpcomingMovies() {
//        System.out.println("===== Upcoming Movies =====");
//
//        LocalDateTime now = LocalDateTime.now();
//        Set<Movie> upcomingMovies = new HashSet<>();
//
//        for (Theatre theatre : registeredTheatresMap.values()) {
//            for (Map.Entry<Movie, List<Show>> entry : theatre.getShowsAvailable().entrySet()) {
//                Movie movie = entry.getKey();
//                for (Show show : entry.getValue()) {
//                    if (show.getStartTime().isAfter(now)) {
//                        upcomingMovies.add(movie);
//                    }
//                }
//            }
//        }
//
//        if (upcomingMovies.isEmpty()) {
//            System.out.println("No upcoming movies found.");
//        } else {
//            for (Movie movie : upcomingMovies) {
//                System.out.println("- " + movie.getMovieName());
//            }
//        }
//
//        System.out.println("===========================");
//    }
//
//    public void displayTheatresAndShowsForMovie(Selection selection) {
//        Movie movie = selection.getSelectedMovie();
//        System.out.println("===== Theatres and Upcoming Shows for Movie: " + movie.getMovieName() + " =====");
//
//        LocalDateTime now = LocalDateTime.now();
//        boolean foundAny = false;
//
//        // Map to store theatre → upcoming shows for this movie
//        Map<Theatre, List<Show>> theatreShowsMap = new HashMap<>();
//
//        for (Theatre theatre : registeredTheatresMap.values()) {
//            List<Show> showsForMovie = theatre.getShowsAvailable().entrySet().stream()
//                    .filter(e -> e.getKey().getMovieName().equalsIgnoreCase(movie.getMovieName()))
//                    .flatMap(e -> e.getValue().stream())
//                    .filter(show -> show.getStartTime().isAfter(now))
//                    .toList();
//
//            if (!showsForMovie.isEmpty()) {
//                foundAny = true;
//                theatreShowsMap.put(theatre, showsForMovie);
//
//                System.out.println("Theatre: " + theatre.getTheatreName());
//                for (Show show : showsForMovie) {
//                    System.out.println("  Show ID: " + show.getShowId() +
//                            " | Date: " + show.getShowDate() +
//                            " | Start: " + show.getStartTime() +
//                            " | End: " + show.getEndTime());
//                }
//            }
//        }
//
//        if (!foundAny) {
//            System.out.println("No upcoming shows found for this movie.");
//        }
//
//        System.out.println("======================================================");
//
//        // Update selection object with filtered theatres and shows
//        selection.setTheatreShowsMap(theatreShowsMap);
//    }
//
//
//
//}
//
//package repository;
//
//import model.Seat;
//import model.User;
//import model.UserType;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//public class UserInventory {
//    private static UserInventory instance;
//    private final Map<String, User> userMap;
//
//    private UserInventory(){
//        this.userMap = new HashMap<>();
//    }
//    public static synchronized UserInventory getInstance(){
//        if(instance==null)
//            instance=new UserInventory();
//        return instance;
//    }
//
//    public void addUser(User user){
//        if(user==null){
//            System.out.println("[ERROR]: Failed to add user to inventory");
//            return;
//        }
//        userMap.put(user.getUserId(), user);
//    }
//
//    public User getUserById(String userId){
//        User user = userMap.get(userId);
//        if(user==null){
//            System.out.println("[ERROR]: Accessing Invalid User");
//        }
//        return user;
//    }
//
//    public boolean verifyUserAsAdmin(User user){
//        return user.getUserType().equals(UserType.ADMIN);
//    }
//}
//
//
//package service;
//
//import controller.BookingManager;
//import factory.TheatreFactory;
//import factory.UserFactory;
//import model.*;
//        import repository.TheatreInventory;
//import repository.UserInventory;
//import strategy.CreditCardPayment;
//import strategy.PaymentStrategy;
//import strategy.UpiPayment;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Scanner;
//
//public class TicketBookingService {
//    private static TicketBookingService instance;
//    private final UserFactory userFactory;
//    private final UserInventory userInventory;
//    private final TheatreFactory theatreFactory;
//    private final TheatreInventory theatreInventory;
//    private final BookingManager bookingManager;
//
//
//    TicketBookingService(){
//        this.userFactory= new UserFactory("ADMIN");
//        this.userInventory = UserInventory.getInstance();
//        this.theatreFactory = new TheatreFactory();
//        this.theatreInventory = TheatreInventory.getInstance();
//        this.bookingManager = new BookingManager(theatreInventory);
//    }
//
//    public static synchronized TicketBookingService getInstance(){
//        if(instance==null){
//            instance = new TicketBookingService();
//        }
//        return instance;
//    }
//
//    public String registerUser(String userType, String userName, String password){
//        User user = userFactory.createUser(userType, userName, password);
//        if(nullCheckUser(user)){
//            System.out.println("[ERROR]: Failed to register User");
//            return "";
//        }
//
//        userInventory.addUser(user);
//        System.out.println("[INFO]: User " + user.getUserName() + " Added");
//        user.displayInfo();
//        return user.getUserId();
//    }
//
//    public String registerTheatre(String userId, String theatreName){
//        User user = getUser(userId);
//        if(nullCheckUser(user)){
//            return "";
//        }
//
//        Theatre theatre = theatreFactory.createTheatre(user, theatreName);
//        if(nullCheckTheatre(theatre)){
//            System.out.println("[ERROR]: Failed to register theatre");
//            return "";
//        }
//
//        theatreInventory.addTheatre(theatre);
//        System.out.println("[INFO]: Theatre " + theatre.getTheatreName()+ " Added");
//        theatre.displayInfo();
//        return theatre.getTheatreId();
//    }
//
//    public void addShowToTheatre(String userId, String theatreId, String movieName, String showDate, String startTime, String endTime) throws ParseException {
//        User user = getUser(userId);
//        if(nullCheckUser(user)){
//            return;
//        }
//        userInventory.verifyUserAsAdmin(user);
//
//        Theatre theatre = getTheatre(theatreId);
//        if(nullCheckTheatre(theatre)){
//            return;
//        }
//
//        Movie movie = theatreInventory.getMovieByName(movieName) != null ? theatreInventory.getMovieByName(movieName) : new Movie(movieName);
//
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date showDateParsed = sdf.parse(showDate);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
//
//        Show show = new Show(showDateParsed, LocalDateTime.parse(startTime, formatter), LocalDateTime.parse(endTime, formatter));
//
//        theatreInventory.addShowToTheatre(theatre, movie, show);
//        System.out.println("[INFO]: Show added to theatre "+ theatre.getTheatreName());
//
//    }
//
//    public void displayShowForTheatre(String theatreId){
//        Theatre theatre = getTheatre(theatreId);
//        if(nullCheckTheatre(theatre)){
//            return;
//        }
//
//        theatreInventory.displayUpcomingShowsForTheatre(theatre);
//    }
//
//    public void displayAllUpcomingShows(){
//        theatreInventory.displayAllUpcomingShows();
//    }
//
//    public void booking(String userId){
//        User user = getUser(userId);
//        if(nullCheckUser(user)){
//            System.out.println("[ERROR]: invalid user.");
//            return;
//        }
//        bookingManager.bookMovie(user);
//    }
//
//
//    public void getBookingsForUser(User user){
//        for(Booking booking: user.getBookings()){
//            booking.displayBooking();
//        }
//    }
//
//    private User getUser(String userId){
//        return userInventory.getUserById(userId);
//    }
//    private Theatre getTheatre(String theatreId){
//        return theatreInventory.getTheatreById(theatreId);
//    }
//
//    private boolean nullCheckUser(User user){
//        if(user==null){
//            System.out.println("[ERROR]: Invalid user.");
//            return true;
//        }
//        return false;
//    }
//    private boolean nullCheckTheatre(Theatre theatre){
//        if(theatre==null){
//            System.out.println("[ERROR]: Invalid theatre.");
//            return true;
//        }
//        return false;
//    }
//}
//
//package strategy;
//
//public class CreditCardPayment implements PaymentStrategy{
//
//    private final String cardNumber;
//    private final String cardHolderName;
//    private final String expiryDate;
//    private final String cvv;
//
//    public CreditCardPayment(String cardNumber, String cardHolderName, String expiryDate, String cvv) {
//        this.cardNumber = cardNumber;
//        this.cardHolderName = cardHolderName;
//        this.expiryDate = expiryDate;
//        this.cvv = cvv;
//    }
//
//    @Override
//    public boolean pay(double amount) {
//        System.out.println("Processing Credit Card payment of ₹" + amount + "...");
//        return true;
//    }
//}
//
//package strategy;
//
//public interface PaymentStrategy {
//    boolean pay(double amount);
//}
//
//package strategy;
//
//public class UpiPayment implements PaymentStrategy{
//    private final String upiId;
//
//    public UpiPayment(String upiId) {
//        this.upiId = upiId;
//    }
//
//    @Override
//    public boolean pay(double amount) {
//        System.out.println("Processing UPI payment of ₹" + amount + " via " + upiId + "...");
//        return true;
//    }
//}
//
//import controller.TicketBookingController;
//import lombok.CustomLog;
//
//import java.text.ParseException;
//import java.util.Scanner;
//
//public class TicketBookingSimulation {
//    public static void main(String[] args) {
//        TicketBookingController controller = new TicketBookingController();
//
//        String adminUserId = controller.registerUser("Admin", "Alice", "abcADMINabc");
//
//        String theatreIdPVR = controller.addTheatre(adminUserId, "PVR");
//        String theatreIdINOX = controller.addTheatre(adminUserId, "INOX");
//        String theatreIdMiniPlex = controller.addTheatre(adminUserId, "MiniPlex");
//
//
//        try{
//            controller.addShow(adminUserId, theatreIdPVR, "Top Gun", "2025-10-20", "20-10-2025 18:30", "20-10-2025 21:30");
//            controller.addShow(adminUserId, theatreIdINOX, "Top Gun", "2025-10-21", "20-10-2025 10:30", "20-10-2025 13:30");
//            controller.addShow(adminUserId, theatreIdMiniPlex, "Barbie", "2025-10-21", "20-10-2025 16:30", "20-10-2025 18:30");
//        }catch (ParseException e){
//            System.out.println("[ERROR]: Parse Exception for Date/Time");
//        }
//
//
//        controller.displayUpcomingShowsForTheatre(theatreIdINOX);
//
//        String CustomerUserId1 = controller.registerUser("Customer", "Bob", "mypassword1");
//        String CustomerUserId2 = controller.registerUser("Customer", "Charlie", "mypassword2");
//        String CustomerUserId3 = controller.registerUser("Customer", "David", "mypassword3");
//
//        controller.displayAllUpcomingShows();
//
//        controller.bookShow(CustomerUserId1);
//        controller.bookShow(CustomerUserId2);
//
//
//    }
//}