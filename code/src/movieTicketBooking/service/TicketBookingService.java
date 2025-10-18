package service;

import factory.TheatreFactory;
import factory.UserFactory;
import model.*;
import repository.TheatreInventory;
import repository.UserInventory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class TicketBookingService {
    private static TicketBookingService instance;
    private final UserFactory userFactory;
    private final UserInventory userInventory;
    private final TheatreFactory theatreFactory;
    private final TheatreInventory theatreInventory;


    TicketBookingService(){
        this.userFactory= new UserFactory("ADMIN");
        this.userInventory = UserInventory.getInstance();
        this.theatreFactory = new TheatreFactory();
        this.theatreInventory = TheatreInventory.getInstance();
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
        userInventory.verifyUserAsAdmin(user);

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

    }

    public void displayShowForTheatre(String theatreId){
        Theatre theatre = getTheatre(theatreId);
        if(nullCheckTheatre(theatre)){
            return;
        }

        theatreInventory.displayUpcomingShowsForTheatre(theatre);
    }

    public void displayAllUpcomingShows(){
        theatreInventory.displayAllUpcomingShows();
    }

    public void booking(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Initiating Movie Booking....");

        //Pick movie
        System.out.println("Pick a movie from following: ");
        theatreInventory.displayAllUpcomingMovies();
        String selectedMovieName = scanner.nextLine();

        Movie selectedMovie = theatreInventory.getMovieByName(selectedMovieName);
        if(selectedMovie==null){
            System.out.println("Invalid Selection");
            return;
        }
        System.out.println("Selected Movie: "+selectedMovie.getMovieName());
        Selection selection = new Selection(selectedMovie);



        //Pick theatre
        System.out.println("Pick a theatre from following: ");
        theatreInventory.displayTheatresAndShowsForMovie(selection);
        String selectedTheatreName = scanner.nextLine();

        Theatre selectedTheatre = theatreInventory.getTheatreByName(selectedTheatreName);
        if (selectedTheatre == null) {
            System.out.println("Invalid theatre selection.");
            return;
        }

        selection.setSelectedTheatre(selectedTheatre);


        //Pick Show
        System.out.println("Pick a show from following (Enter show id): ");
        selection.displayShowsForSelectedTheatre();
        String selectedShowId = scanner.nextLine();

        Show selectedShow = theatreInventory.getShowById(selectedTheatre, selectedShowId);
        if (selectedShow == null) {
            System.out.println("Invalid Show selection.");
            return;
        }

        selection.setSelectedShow(selectedShow);

        selection.displaySelection();



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
