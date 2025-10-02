
import RoomBookingSystem.service.RoomBookingSystem;

import java.util.List;

public class RoomBookingSystemSimulation {
    public static void main(String[] args) {

        RoomBookingSystem bookingRoomSystem = RoomBookingSystem.getInstance();

        List<Integer> slots1 = List.of(1,2,3,4,5,6,7,8,9,10);
        bookingRoomSystem.registerRoom("Room A", "SMALL", slots1);
        bookingRoomSystem.registerRoom("Room B", "LARGE", slots1);

        //check validation for duplicate room name
        bookingRoomSystem.registerRoom("Room A", "SMALL", slots1);

        bookingRoomSystem.showAllRegisteredRooms();

        bookingRoomSystem.registerEmployee("Alice", "Marketing");
        bookingRoomSystem.registerEmployee("Bob", "Sales");
        bookingRoomSystem.registerEmployee("David", "IT");

        //check validation for duplicate employee name
        bookingRoomSystem.registerEmployee("Bob", "Sales");

        bookingRoomSystem.showAllRegisteredEmployees();

        bookingRoomSystem.bookRoom("Alice", 7, 2, 30);
        bookingRoomSystem.bookRoom("Bob", 12, 1, 90);
        bookingRoomSystem.bookRoom("Charlie", 3, 1, 90);
        bookingRoomSystem.bookRoom("Alice", 7, 2, 180);

        bookingRoomSystem.bookRoomRecurring("David", 5, 3, 60, 3, 3, "WEEKLY");
        bookingRoomSystem.bookRoomRecurring("Bob", 13, 7, 30, 2, 1,"DAILY");

        bookingRoomSystem.viewSchedule();

    }
}
