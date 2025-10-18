package repository;

import lombok.Getter;
import model.*;

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
        return movieMap.getOrDefault(movieName.toLowerCase(), null);
    }

    public Theatre getTheatreByName(String theatreName) {
        return registeredTheatresMap.values().stream()
                .filter(t -> t.getTheatreName().equalsIgnoreCase(theatreName))
                .findFirst()
                .orElse(null);
    }
    public Show getShowById(Theatre theatre, String showId) {
        if (theatre == null || theatre.getShowsAvailable() == null) return null;

        for (List<Show> shows : theatre.getShowsAvailable().values()) {
            for (Show show : shows) {
                if (show.getShowId().equals(showId)) {
                    return show;
                }
            }
        }
        return null;
    }
    public void addShowToTheatre(Theatre theatre, Movie movie, Show show){
        movieMap.put(movie.getMovieName().toLowerCase(), movie);
        theatre.addShow(movie, show);
    }

    public void removeMovieFromTheatre(Theatre theatre, String movieName) {
        if (theatre == null || movieName == null) return;

        Movie movie = theatre.getShowsAvailable().keySet().stream()
                .filter(m -> m.getMovieName().equalsIgnoreCase(movieName))
                .findFirst().orElse(null);

        if (movie != null) {
            theatre.getShowsAvailable().remove(movie);
            System.out.println("[INFO]: Movie '" + movieName + "' removed from theatre '" + theatre.getTheatreName() + "'");
        } else {
            System.out.println("[ERROR]: Movie not found in theatre.");
        }
    }


    public boolean removeShowFromTheatre(Theatre theatre, String movieName, String showId) {
        if (theatre == null || movieName == null || showId == null) return false;

        Movie movie = theatre.getShowsAvailable().keySet().stream()
                .filter(m -> m.getMovieName().equalsIgnoreCase(movieName))
                .findFirst().orElse(null);
        Boolean removedShow = false;
        if (movie != null && theatre.getShowsAvailable().containsKey(movie)) {
            List<Show> shows = theatre.getShowsAvailable().get(movie);
            shows.removeIf(show -> show.getShowId().equals(showId));
            removedShow = true;
            System.out.println("[INFO]: Show " + showId + " removed from movie '" + movieName + "'");
        } else {
            System.out.println("[ERROR]: Movie or show not found.");
        }
        return removedShow;
    }

    public void updateSeatingForShow(Theatre theatre, String movieName, String showId, List<Seat> newSeats) {
        Show show = getShowById(theatre, showId);
        if (show != null) {
            show.getSeats().clear();
            show.getSeats().addAll(newSeats);
            System.out.println("[INFO]: Seating updated for Show " + showId);
        } else {
            System.out.println("[ERROR]: Show not found.");
        }
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

    public void displayAllUpcomingMovies() {
        System.out.println("===== Upcoming Movies =====");

        LocalDateTime now = LocalDateTime.now();
        Set<Movie> upcomingMovies = new HashSet<>();

        for (Theatre theatre : registeredTheatresMap.values()) {
            for (Map.Entry<Movie, List<Show>> entry : theatre.getShowsAvailable().entrySet()) {
                Movie movie = entry.getKey();
                for (Show show : entry.getValue()) {
                    if (show.getStartTime().isAfter(now)) {
                        upcomingMovies.add(movie);
                    }
                }
            }
        }

        if (upcomingMovies.isEmpty()) {
            System.out.println("No upcoming movies found.");
        } else {
            for (Movie movie : upcomingMovies) {
                System.out.println("- " + movie.getMovieName());
            }
        }

        System.out.println("===========================");
    }

    public void displayTheatresAndShowsForMovie(Selection selection) {
        Movie movie = selection.getSelectedMovie();
        System.out.println("===== Theatres and Upcoming Shows for Movie: " + movie.getMovieName() + " =====");

        LocalDateTime now = LocalDateTime.now();
        boolean foundAny = false;

        // Map to store theatre → upcoming shows for this movie
        Map<Theatre, List<Show>> theatreShowsMap = new HashMap<>();

        for (Theatre theatre : registeredTheatresMap.values()) {
            List<Show> showsForMovie = theatre.getShowsAvailable().entrySet().stream()
                    .filter(e -> e.getKey().getMovieName().equalsIgnoreCase(movie.getMovieName()))
                    .flatMap(e -> e.getValue().stream())
                    .filter(show -> show.getStartTime().isAfter(now))
                    .toList();

            if (!showsForMovie.isEmpty()) {
                foundAny = true;
                theatreShowsMap.put(theatre, showsForMovie);

                System.out.println("Theatre: " + theatre.getTheatreName());
                for (Show show : showsForMovie) {
                    System.out.println("  Show ID: " + show.getShowId() +
                            " | Date: " + show.getShowDate() +
                            " | Start: " + show.getStartTime() +
                            " | End: " + show.getEndTime());
                }
            }
        }

        if (!foundAny) {
            System.out.println("No upcoming shows found for this movie.");
        }

        System.out.println("======================================================");

        // Update selection object with filtered theatres and shows
        selection.setTheatreShowsMap(theatreShowsMap);
    }



}
