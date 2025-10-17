package service;

import factory.TheatreFactory;
import factory.UserFactory;
import model.Theatre;
import model.User;
import repository.TheatreInventory;
import repository.UserInventory;

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

    public void AddShowToTheatre(String userId, String theatreId, String MovieId, String showDate, String startTime, String endTime){
        User user = getUser(userId);
        if(nullCheckUser(user)){
            return;
        }
        userInventory.verifyUserAsAdmin(user);

        Theatre theatre = getTheatre(theatreId);
        if(nullCheckTheatre(theatre)){
            return;
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
