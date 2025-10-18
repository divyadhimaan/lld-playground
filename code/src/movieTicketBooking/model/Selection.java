package model;

import lombok.Getter;
import lombok.Setter;
import repository.TheatreInventory;

import java.util.*;

@Getter @Setter
public class Selection {
    private Movie selectedMovie;
    private Theatre selectedTheatre;
    private Show selectedShow;
    private Map<Theatre, List<Show>> theatreShowsMap = new HashMap<>();
    private List<Seat> selectedSeats;


    // Constructor
    public Selection() {
        this.selectedMovie = null;
        this.selectedTheatre = null;
        this.selectedShow = null;
        this.selectedSeats = new ArrayList<>();
    }

    public boolean chooseMovie(Scanner scanner, TheatreInventory inventory) {
        System.out.println("Pick a movie from following:");
        inventory.displayAllUpcomingMovies();
        String name = scanner.nextLine();
        selectedMovie = inventory.getMovieByName(name);
        if (selectedMovie == null) {
            System.out.println("Invalid movie selection.");
            return false;
        }
        return true;
    }

    public boolean chooseTheatre(Scanner scanner, TheatreInventory inventory) {
        System.out.println("Pick a theatre from following:");
        inventory.displayTheatresAndShowsForMovie(this);
        String theatreName = scanner.nextLine();
        selectedTheatre = inventory.getTheatreByName(theatreName);
        if (selectedTheatre == null) {
            System.out.println("Invalid theatre selection.");
            return false;
        }
        return true;
    }

    public boolean chooseShow(Scanner scanner, TheatreInventory inventory) {
        System.out.println("Pick a show from following (Enter show ID):");
        displayShowsForSelectedTheatre();
        String showId = scanner.nextLine();
        selectedShow = inventory.getShowById(selectedTheatre, showId);
        if (selectedShow == null) {
            System.out.println("Invalid show selection.");
            return false;
        }
        return true;
    }

    public boolean chooseSeats(Scanner scanner) {
        selectedShow.displaySeats();
        System.out.println("Pick seats (comma separated): ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("No seats entered.");
            return false;
        }

        String[] seatIds = input.split(",");
        List<Seat> seatsToHold = new ArrayList<>();

        for (String id : seatIds) {
            Seat seat = selectedShow.getSeatById(id.trim());
            if (seat == null || !seat.isAvailable()) {
                System.out.println("[ERROR]: Seat " + id + " is invalid or unavailable.");
                return false;
            }
            seatsToHold.add(seat);
        }

        for (Seat seat : seatsToHold) {
            seat.hold();
        }

        selectedSeats.addAll(seatsToHold);
        return true;
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

        System.out.print("Selected Seats: ");
        if(selectedSeats.isEmpty()){
            System.out.println("None");
        }else{
            for(Seat seat: selectedSeats){
                System.out.println(
                        "  Seat ID: " + seat.getSeatId() +
                        " | Seat Type: " + seat.getSeatType()
                );
            }
        }

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

