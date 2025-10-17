package model;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Theatre {
    private final AtomicInteger id= new AtomicInteger();
    @Getter
    private final String theatreId;
    @Getter
    private final String theatreName;
    @Getter
    private final int numberOfScreens;
    @Getter
    private final Map<Movie, List<Show>> showsAvailable;
    @Getter
    private final User registeredBy;

    public Theatre(String name, User registeredByUser){
        this.theatreId = String.valueOf(id.incrementAndGet());
        this.theatreName = name;
        this.numberOfScreens = 1;
        this.showsAvailable = new HashMap<Movie, List<Show>>();
        this.registeredBy = registeredByUser;
    }

    public void addShow(Movie movie, Show show){
        if(show==null) {
            System.out.println("[ERROR]: Invalid Show");
        }
        if(movie==null){
            System.out.println("[ERROR]: Invalid Movie");
        }
        if(showsAvailable.containsKey(movie)){
            showsAvailable.get(movie).add(show);
        }
        showsAvailable.put(movie,show);
    }

    public void displayInfo() {
        System.out.println("===== Theatre Info =====");
        System.out.println("Theatre ID   : " + getTheatreId());
        System.out.println("Theatre Name : " + getTheatreName());
        System.out.println("Number of Screens: " + getNumberOfScreens());
        System.out.println("Registered By: " + (registeredBy != null ? registeredBy.getUserName() : "N/A"));

        if (showsAvailable.isEmpty()) {
            System.out.println("No shows available.");
        } else {
            System.out.println("Shows Available:");
            for (Map.Entry<Movie, List<Show>> entry : showsAvailable.entrySet()) {
                Movie movie = entry.getKey();
                List<Show> shows = entry.getValue();
                System.out.println("  Movie: " + movie.getMovieName());
                for (Show show : shows) {
                    System.out.println("    Show ID: " + show.getShowId() + ", Time: " + show.getShowTime());
                }
            }
        }
        System.out.println("=======================");
    }


}
