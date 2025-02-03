import java.util.UUID;

public class ExitGate {
    private ParkingLot parkingLot;

    public ExitGate(ParkingLot parkingLot){
        this.parkingLot = parkingLot;
    }

    public void exit(UUID ticketId)
    {
        ParkingLotTicket ticket = parkingLot.getTicketById(ticketId);
        if(ticket != null)
        {
            long exitTime = System.currentTimeMillis();
            long duration = exitTime - ticket.getEntryTime();
            double fee = parkingLot.paymentProcessor.calculateStrategy(duration);
            System.out.println("Vehicle " + ticket.getVehicle().getLicenseNumber() + " exited. Parking fee: $" + fee);
            ticket.getParkingSpot().vacateSpot();
            parkingLot.removeTicket(ticketId);
        } else {
            System.out.println("Invalid ticket ID: " + ticketId);
        }
    }


}
