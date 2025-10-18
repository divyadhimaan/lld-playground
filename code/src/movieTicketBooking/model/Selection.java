package model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Selection {
    private final Movie selectedMovie;
    @Setter
    private Theatre selectedTheatre;
    @Setter
    private Show selectedShow;
    @Setter
    private Map<Theatre, List<Show>> theatreShowsMap = new HashMap<>();


    // Constructor
    public Selection(Movie movie) {
        this.selectedMovie = movie;
        this.selectedTheatre = null;
        this.selectedShow = null;
    }


    public void displaySelection() {
        System.out.println("===== Current Selection =====");

        System.out.println("Selected Movie: " + (selectedMovie != null ? selectedMovie.getMovieName() : "None"));
        System.out.println("Selected Theatre: " + (selectedTheatre != null ? selectedTheatre.getTheatreName() : "None"));

        if(selectedShow==null)
            displayTheatreShowMap();
        else
            System.out.println("Selected Show: " +
                    "  Show ID: " + selectedShow.getShowId() +
                            " | Date: " + selectedShow.getShowDate() +
                            " | Start: " + selectedShow.getStartTime() +
                            " | End: " + selectedShow.getEndTime()
            );


        System.out.println("=================================");
    }

    public void displayTheatreShowMap() {
        System.out.println("===== Theatres and Upcoming Shows for Selected Movie =====");

        if (theatreShowsMap == null || theatreShowsMap.isEmpty()) {
            System.out.println("No theatre-show mapping available yet.");
        } else {
            theatreShowsMap.forEach((theatre, shows) -> {
                System.out.println("Theatre: " + theatre.getTheatreName());
                if (shows.isEmpty()) {
                    System.out.println("  No upcoming shows.");
                } else {
                    shows.forEach(show -> System.out.println(
                            "  Show ID: " + show.getShowId() +
                                    " | Date: " + show.getShowDate() +
                                    " | Start: " + show.getStartTime() +
                                    " | End: " + show.getEndTime()
                    ));
                }
            });
        }

        System.out.println("======================================================");
    }

    public void displayShowsForSelectedTheatre() {
        if (theatreShowsMap == null || theatreShowsMap.isEmpty()) {
            System.out.println("No theatre-show mapping available yet.");
            return;
        }

        if(selectedTheatre==null){
            System.out.println("No selected theatre.");
            return;
        }

        List<Show> shows = theatreShowsMap.get(selectedTheatre);
        if (shows == null || shows.isEmpty()) {
            System.out.println("No upcoming shows for theatre: " + selectedTheatre.getTheatreName());
            return;
        }

        System.out.println("===== Upcoming Shows for Theatre: " + selectedTheatre.getTheatreName() + " =====");
        shows.forEach(show -> System.out.println(
                "Show ID: " + show.getShowId() +
                        " | Date: " + show.getShowDate() +
                        " | Start: " + show.getStartTime() +
                        " | End: " + show.getEndTime()
        ));
        System.out.println("======================================================");
    }



}

