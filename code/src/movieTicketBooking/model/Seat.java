package model;

import java.util.concurrent.atomic.AtomicInteger;

public class Seat {
    private static final AtomicInteger id = new AtomicInteger(0);
    private String seatId;
    private SeatType seatType;

    public Seat(SeatType type){
        this.seatId = type.name() + id.incrementAndGet();
        this.seatType = type;
    }
}
