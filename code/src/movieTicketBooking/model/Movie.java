package model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Movie {
    private static final AtomicInteger id = new AtomicInteger(0);
    @Getter
    private final String movieId;
    @Getter
    private final String movieName;
    @Getter
    private final List<Theatre> theatres;

    public Movie(String name){
        this.movieId =  String.valueOf(id.incrementAndGet());
        this.movieName = name;
        this.theatres = new ArrayList<>();
    }

}
