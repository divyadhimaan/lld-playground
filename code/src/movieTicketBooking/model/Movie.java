package model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Movie {
    private final AtomicInteger id= new AtomicInteger();
    private final String movieId;
    @Getter
    private final String movieName;
    @Getter
    private final List<Theatre> theatres;

    public Movie(String name){
        this.movieId = name + String.valueOf(id.incrementAndGet());
        this.movieName = name;
        this.theatres = new ArrayList<>();
    }

}
