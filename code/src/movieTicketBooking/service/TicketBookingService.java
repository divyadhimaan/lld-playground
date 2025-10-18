package service;

import controller.BookingManager;
import factory.TheatreFactory;
import factory.UserFactory;
import model.*;
import repository.TheatreInventory;
import repository.UserInventory;
import strategy.CreditCardPayment;
import strategy.PaymentStrategy;
import strategy.UpiPayment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TicketBookingService {
    private static TicketBookingService instance;
    private final UserFactory userFactory;
    private final UserInventory userInventory;
    private final TheatreFactory theatreFactory;
    private final TheatreInventory theatreInventory;
    private final BookingManager bookingManager;


    TicketBookingService(){
        this.userFactory= new UserFactory("ADMIN");
        this.userInventory = UserInventory.getInstance();
        this.theatreFactory = new TheatreFactory();
        this.theatreInventory = TheatreInventory.getInstance();
        this.bookingManager = new BookingManager(theatreInventory);
    }

    public static synchronized TicketBookingService getInstance(){
        if(instance==null){
            instance = new TicketBookingService();
        }
        return instance;
    }

    public String registerUser(String userType, String userName, String password){
        User user = userFactory.createUser(userType, userName, password);
        if(nullCheckUser(user)){
            System.out.println("[ERROR]: Failed to register User");
            return "";
        }

        userInventory.addUser(user);
        System.out.println("[INFO]: User " + user.getUserName() + " Added");
        user.displayInfo();
        return user.getUserId();
    }

    public String registerTheatre(String userId, String theatreName){
        User user = getUser(userId);
        if(nullCheckUser(user)){
            return "";
        }

        Theatre theatre = theatreFactory.createTheatre(user, theatreName);
        if(nullCheckTheatre(theatre)){
            System.out.println("[ERROR]: Failed to register theatre");
            return "";
        }

        theatreInventory.addTheatre(theatre);
        System.out.println("[INFO]: Theatre " + theatre.getTheatreName()+ " Added");
        theatre.displayInfo();
        return theatre.getTheatreId();
    }

    public void addShowToTheatre(String userId, String theatreId, String movieName, String showDate, String startTime, String endTime) throws ParseException {
        User user = getUser(userId);
        if(nullCheckUser(user)){
            return;
        }
        if(!userInventory.verifyUserAsAdmin(user)){
            System.out.println("[ERROR]: User doesn't have access to add Show");
        }

        Theatre theatre = getTheatre(theatreId);
        if(nullCheckTheatre(theatre)){
            return;
        }

        Movie movie = theatreInventory.getMovieByName(movieName) != null ? theatreInventory.getMovieByName(movieName) : new Movie(movieName);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date showDateParsed = sdf.parse(showDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        Show show = new Show(showDateParsed, LocalDateTime.parse(startTime, formatter), LocalDateTime.parse(endTime, formatter));

        theatreInventory.addShowToTheatre(theatre, movie, show);
        System.out.println("[INFO]: Show added to theatre "+ theatre.getTheatreName());
        displayAddedShowDetails(theatre, movie, show);
//        show.displayShowDetails();
    }

    private void displayAddedShowDetails(Theatre theatre, Movie movie, Show show) {
        System.out.println("===== Show Added Successfully =====");
        System.out.println("Theatre: " + theatre.getTheatreName() + " (ID: " + theatre.getTheatreId() + ")");
        System.out.println("Movie  : " + movie.getMovieName());
        System.out.println("Show ID: " + show.getShowId() +
                " | Date: " + show.getShowDate() +
                " | Start: " + show.getStartTime() +
                " | End: " + show.getEndTime());
        System.out.println("Seats Available:");
        show.displaySeats();
        System.out.println("===================================");
    }

    public void displayShowForTheatre(String theatreId){
        Theatre theatre = getTheatre(theatreId);
        if(nullCheckTheatre(theatre)){
            return;
        }

        theatreInventory.displayUpcomingShowsForTheatre(theatre);
    }

    public void removeMovie(String userId, String theatreId, String movieName) {
        User user = getUser(userId);
        if (nullCheckUser(user) || !userInventory.verifyUserAsAdmin(user)) return;

        Theatre theatre = getTheatre(theatreId);
        if (nullCheckTheatre(theatre)) return;

        theatreInventory.removeMovieFromTheatre(theatre, movieName);
    }

    public boolean removeShow(String userId, String theatreId, String movieName, String showId) {
        User user = getUser(userId);
        if (nullCheckUser(user) || !userInventory.verifyUserAsAdmin(user)) return false;

        Theatre theatre = getTheatre(theatreId);
        if (nullCheckTheatre(theatre)) return false;

        return theatreInventory.removeShowFromTheatre(theatre, movieName, showId);
    }

    public void updateSeating(String userId, String theatreId, String movieName, String showId, List<Seat> newSeats) {
        User user = getUser(userId);
        if (nullCheckUser(user) || !userInventory.verifyUserAsAdmin(user)) return;

        Theatre theatre = getTheatre(theatreId);
        if (nullCheckTheatre(theatre)) return;

        theatreInventory.updateSeatingForShow(theatre, movieName, showId, newSeats);
    }

    public void displayAllUpcomingShows(){
        theatreInventory.displayAllUpcomingShows();
    }

    public void booking(String userId){
        User user = getUser(userId);
        if(nullCheckUser(user)){
            System.out.println("[ERROR]: invalid user.");
            return;
        }
        bookingManager.bookMovie(user);
    }


    public void getBookingsForUser(String userId){
        User user = getUser(userId);
        if (nullCheckUser(user)) return;
        if(user.getBookings().isEmpty())
            System.out.println("No bookings.");
        for(Booking booking: user.getBookings()){
            booking.displayBooking();
        }
    }

    private User getUser(String userId){
        return userInventory.getUserById(userId);
    }
    private Theatre getTheatre(String theatreId){
        return theatreInventory.getTheatreById(theatreId);
    }

    private boolean nullCheckUser(User user){
        if(user==null){
            System.out.println("[ERROR]: Invalid user.");
            return true;
        }
        return false;
    }
    private boolean nullCheckTheatre(Theatre theatre){
        if(theatre==null){
            System.out.println("[ERROR]: Invalid theatre.");
            return true;
        }
        return false;
    }
}
