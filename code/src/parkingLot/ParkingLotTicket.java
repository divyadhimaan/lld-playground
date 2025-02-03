import Vehicle.Vehicle;

import java.util.UUID;

public class ParkingLotTicket {
    private UUID ticketId;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private long entryTime;


    public ParkingLotTicket(UUID ticketId, Vehicle vehicle, ParkingSpot spot) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.parkingSpot = spot;
        this.entryTime = System.currentTimeMillis();
    }

    public UUID getTicketId()
    {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getParkingSpot()
    {
        return parkingSpot;
    }

    public long getEntryTime() {
        return entryTime;
    }
}
