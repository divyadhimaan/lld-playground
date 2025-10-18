package controller;


import service.TicketBookingService;

import java.text.ParseException;
import java.util.Scanner;

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

    public void addShow(String userId, String theatreId, String movieName, String showDate, String startTime, String endTime) throws ParseException {
        service.addShowToTheatre(userId, theatreId, movieName, showDate, startTime, endTime);
    }

    public void displayUpcomingShowsForTheatre(String theatreId){
        service.displayShowForTheatre(theatreId);
    }

    public void displayAllUpcomingShows(){
        service.displayAllUpcomingShows();
    }

    public void selectShow(){
        service.booking();
    }
}
