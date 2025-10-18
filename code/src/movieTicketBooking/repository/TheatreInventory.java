package repository;

import lombok.Getter;
import model.Movie;
import model.Show;
import model.Theatre;

import java.time.LocalDateTime;
import java.util.*;

public class TheatreInventory {
    private static TheatreInventory instance;
    @Getter
    private final Map<String, Theatre> registeredTheatresMap;
    private final Map<String, Movie> movieMap;

    private TheatreInventory(){
        this.registeredTheatresMap = new HashMap<>();
        this.movieMap = new HashMap<>();
    }
    public static synchronized TheatreInventory getInstance(){
        if(instance==null)
            instance=new TheatreInventory();
        return instance;
    }

    public void addTheatre(Theatre theatre){
        if(theatre==null){
            System.out.println("[ERROR]: Failed to add theatre to inventory");
            return;
        }
        registeredTheatresMap.put(theatre.getTheatreId(), theatre);
    }

    public Theatre getTheatreById(String theatreId){
        Theatre theatre = registeredTheatresMap.get(theatreId);
        if(theatre==null){
            System.out.println("[ERROR]: Accessing Invalid Theatre");
        }
        return theatre;
    }

    public Movie getMovieByName(String movieName){
        return movieMap.getOrDefault(movieName, null);
    }

    public void addShowToTheatre(Theatre theatre, Movie movie, Show show){
        movieMap.put(movie.getMovieId(), movie);
        theatre.addShow(movie, show);
    }

    public void displayUpcomingShowsForTheatre(Theatre theatre){
        theatre.displayUpcomingShows();
    }

    public void displayAllUpcomingShows() {
        System.out.println("===== Upcoming Shows Across All Theatres =====");

        LocalDateTime now = LocalDateTime.now();
        boolean found = false;

        for (Theatre theatre : registeredTheatresMap.values()) {
            for (Map.Entry<Movie, List<Show>> entry : theatre.getShowsAvailable().entrySet()) {
                Movie movie = entry.getKey();
                List<Show> shows = entry.getValue();

                for (Show show : shows) {
                    if (show.getStartTime().isAfter(now)) {
                        found = true;
                        System.out.println("Theatre: " + theatre.getTheatreName() +
                                " | Movie: " + movie.getMovieName() +
                                " | Date: " + show.getShowDate() +
                                " | Start: " + show.getStartTime() +
                                " | End: " + show.getEndTime());
                    }
                }
            }
        }

        if (!found) {
            System.out.println("No upcoming shows found in any theatre.");
        }

        System.out.println("==============================================");
    }



}
