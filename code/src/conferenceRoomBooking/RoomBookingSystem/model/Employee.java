package RoomBookingSystem.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Employee {
    private final UUID employeeId;
    private final String employeeName;
    private final String departmentName;
    private final List<Booking> bookings;

    public Employee(String name, String department){
        this.employeeId = UUID.randomUUID();
        this.employeeName = name;
        this.departmentName = department;
        this.bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public void displayBookings(){
        if(bookings.isEmpty()){
            System.out.println("No bookings found for " + employeeName);
            return;
        }
        for(Booking booking: bookings){
            System.out.println("Booking ID: " + booking.getBookingId() + ", Room: " + booking.getRoom().getRoomName() +
                    ", Slots: " + booking.getBookedSlots() + ", Day: " + booking.getDay() );
        }
    }

    public void removeBooking(Booking booking) {
        bookings.removeIf(b -> b.getBookingId().equals(booking.getBookingId()));
    }
}
