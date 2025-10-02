package RoomBookingSystem.model;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class Booking {
    private final UUID bookingId;
    private final Room room;
    private final List<Integer> bookedSlots;
    private final String employeeName;
    private final int day;

    public Booking(Room room, List<Integer> bookedSlots, String employeeName, int day) {
        this.bookingId = UUID.randomUUID();
        this.room = room;
        this.bookedSlots = bookedSlots;
        this.employeeName = employeeName;
        this.day = day;
    }
}
