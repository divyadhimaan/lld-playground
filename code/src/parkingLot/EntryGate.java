import Vehicle.Vehicle;

import java.util.UUID;

public class EntryGate {
    private ParkingLot parkingLot;

    public EntryGate(ParkingLot parkingLot){
        this.parkingLot = parkingLot;
    }

    public ParkingLotTicket entry(Vehicle vehicle)
    {
        return issueTicket(vehicle);
    }
    public ParkingLotTicket issueTicket(Vehicle vehicle)
    {
        ParkingSpot availableSpot = parkingLot.findAvailableSpot(vehicle.getVehicleType());
        if(availableSpot != null)
        {
            UUID ticketId = UUID.randomUUID();
            ParkingLotTicket ticket = new ParkingLotTicket(ticketId, vehicle, availableSpot);
            parkingLot.issueTicket(ticketId, ticket);
            availableSpot.occupySpot(vehicle);
            System.out.println("Ticket issued for vehicle: " + vehicle.getLicenseNumber());
            return ticket;
        }else{
            System.out.println("No available parking spot for vehicle: " + vehicle.getLicenseNumber());
            return null;
        }

    }
}
