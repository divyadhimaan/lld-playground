package model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Show {
    private static AtomicInteger showIdCounter = new AtomicInteger(1);
    private AtomicInteger seatCounter = new AtomicInteger(1); // per-show seat numbering

    @Getter
    private final String showId;
    @Getter
    private final Date showDate;
    @Getter
    private final LocalDateTime startTime;
    @Getter
    private final LocalDateTime endTime;
    @Getter
    private final List<Seat> seats;
    private final Map<SeatType, Double> seatPricing;


    public Show(Date showDate, LocalDateTime startTime, LocalDateTime endTime){
        this.showId = 's' + String.valueOf(showIdCounter.incrementAndGet());
        this.showDate =showDate;
        this.startTime=startTime;
        this.endTime=endTime;
        this.seats = initializeSeats();
        this.seatPricing = initializeSeatPricing();
    }

    private Map<SeatType, Double> initializeSeatPricing() {
        Map<SeatType, Double> pricing = new HashMap<>();
        pricing.put(SeatType.REGULAR, 150.0);
        pricing.put(SeatType.PREMIUM, 250.0);
        return pricing;
    }

    public double getPriceForSeat(Seat seat) {
        return seatPricing.getOrDefault(seat.getSeatType(), 0.0);
    }

    public double calculateTotalPrice(List<Seat> selectedSeats) {
        double total = 0;
        for (Seat seat : selectedSeats) {
            total += getPriceForSeat(seat);
        }
        return total;
    }

    private List<Seat> initializeSeats() {
        List<Seat> seatList = new ArrayList<>();
        // Example: 10 Regular, 5 Premium seats per show
        for (int i = 0; i < 10; i++) seatList.add(new Seat("R" + seatCounter.getAndIncrement(), SeatType.REGULAR));
        for (int i = 0; i < 5; i++) seatList.add(new Seat("P" + seatCounter.getAndIncrement(), SeatType.PREMIUM));
        return seatList;
    }

    public void changeStatus(List<Seat> selectedSeats, SeatStatus status){
        for (Seat seat : selectedSeats) {
            if (status == SeatStatus.BOOKED) seat.book();
            else if (status == SeatStatus.HELD) seat.hold();
        }
    }

    public Seat getSeatById(String seatId) {
        for (Seat seat : seats) {
            if (seat.getSeatId().equalsIgnoreCase(seatId.trim())) {
                return seat;
            }
        }
        return null;
    }
    public void displaySeats() {
        System.out.println("===== Seat Layout for Show " + showId + " =====");
        for (Seat seat : seats) {
            seat.display();
        }
        System.out.println("===============================================");
    }

    public String getShowTime(){
        return this.startTime.toString();
    }

}
