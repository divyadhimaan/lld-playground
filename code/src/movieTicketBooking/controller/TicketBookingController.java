package controller;


import service.TicketBookingService;

//facade layer
public class TicketBookingController {
    private final TicketBookingService service;

    public TicketBookingController(){
        this.service = TicketBookingService.getInstance();
    }
    public String registerUser(String userType, String userName, String password){
        return service.registerUser(userType, userName, password);
    }

    public String addTheatre(String userId, String theatreName){
        return service.registerTheatre(userId, theatreName);
    }
}
