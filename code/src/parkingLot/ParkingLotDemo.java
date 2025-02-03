import Vehicle.Vehicle;
import Vehicle.Car;

public class ParkingLotDemo {
    public static void main(String[] args)
    {
        ParkingLot parkingLot = ParkingLot.getInstance(new HourlyPaymentStrategy());

        EntryGate entryGate = new EntryGate(parkingLot);
        ExitGate exitGate = new ExitGate(parkingLot);

        Vehicle car = new Car("KA-01-AB-1234");
        ParkingLotTicket ticket = entryGate.entry(car);

        if (ticket != null) {
            try {
                Thread.sleep(5000); // Simulate some parking time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            exitGate.exit(ticket.getTicketId());
        }
    }
}
