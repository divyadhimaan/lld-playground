package model;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Seat {
    private final String seatId;
    private final SeatType seatType;
    @Setter
    private SeatStatus seatStatus;

    public Seat(String id, SeatType type){
        this.seatId = id;
        this.seatType = type;
        this.seatStatus=SeatStatus.AVAILABLE;
    }

    // Thread-safe check and hold
    public synchronized boolean hold() {
        if (seatStatus == SeatStatus.AVAILABLE) {
            seatStatus = SeatStatus.HELD;
            return true;
        }
        return false;
    }

    // Thread-safe booking
    public synchronized boolean book() {
        if (seatStatus == SeatStatus.HELD) {
            seatStatus = SeatStatus.BOOKED;
            return true;
        }
        return false;
    }

    public synchronized boolean isAvailable() {
        return this.seatStatus == SeatStatus.AVAILABLE;
    }

    public void display() {
        System.out.println("Seat: " + seatId + " | Type: " + seatType + " | Status: " + seatStatus);
    }
}
