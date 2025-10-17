package repository;

import lombok.Getter;
import model.Movie;
import model.Show;
import model.Theatre;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TheatreInventory {
    private static TheatreInventory instance;
    @Getter
    private final Map<String, Theatre> registeredTheatresMap;

    private TheatreInventory(){
        this.registeredTheatresMap = new HashMap<>();
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

    public void addShowToTheatre(Theatre theatre, Movie movie, Show show){
        theatre.addShow(movie, show);
    }


}
