package model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
@Getter
public class Theatre {
    private static final AtomicInteger id = new AtomicInteger(0);
    private final String theatreId;
    private final String theatreName;
    private final int numberOfScreens;
    private final Map<Movie, List<Show>> showsAvailable;
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
        showsAvailable.computeIfAbsent(movie, k -> new ArrayList<>()).add(show);
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

    public void displayUpcomingShows() {
        System.out.println("===== Upcoming Shows in " + theatreName + " =====");

        LocalDateTime now = LocalDateTime.now();
        boolean found = false;

        for (Map.Entry<Movie, List<Show>> entry : showsAvailable.entrySet()) {
            Movie movie = entry.getKey();
            List<Show> shows = entry.getValue();

            for (Show show : shows) {
                if (show.getStartTime().isAfter(now)) {
                    if (!found) found = true;
                    System.out.println("Movie: " + movie.getMovieName() +
                            " | Show ID: " + show.getShowId() +
                            " | Start: " + show.getStartTime() +
                            " | End: " + show.getEndTime());
                }
            }
        }

        if (!found) {
            System.out.println("No upcoming shows found.");
        }

        System.out.println("======================================");
    }



}
