import Vehicle.VehicleType;
import Vehicle.Vehicle;


import java.util.*;

public class ParkingLot {
    private static ParkingLot instance;
    private final List<ParkingLevel> parkingLevels;
    PaymentProcessor paymentProcessor;
    private Map<UUID, ParkingLotTicket> tickets = new HashMap<>();

    private ParkingLot(PaymentStrategy strategy)
    {
        parkingLevels = new ArrayList<>();
        paymentProcessor = new PaymentProcessor();
        paymentProcessor.setStrategy(strategy);
        tickets = new HashMap<>();
    }

    public static synchronized ParkingLot getInstance(PaymentStrategy strategy)
    {
        if(instance == null)
            instance = new ParkingLot(strategy);
        return instance;
    }

    public void addLevel(ParkingLevel level)
    {
        parkingLevels.add(level);
    }
    public ParkingLotTicket getTicketById(UUID ticketId)
    {
        return tickets.get(ticketId);
    }

    public ParkingSpot findAvailableSpot(VehicleType vehicleType)
    {
        for (ParkingLevel level : parkingLevels) {
            ParkingSpot spot = level.findParkingSpot(vehicleType);
            if (spot != null) {
                return spot;
            }
        }
        return null;
    }

    public void issueTicket(UUID ticketId, ParkingLotTicket ticket) {
        tickets.put(ticketId, ticket);
    }

    public void removeTicket(UUID ticketId) {
        tickets.remove(ticketId);
    }

    public PaymentProcessor getPaymentProcessor() {
        return paymentProcessor;
    }

}
