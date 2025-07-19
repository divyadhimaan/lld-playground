package system.Strategy;

import system.Elevator.Elevator;
import system.Status;

import java.util.List;
import java.util.Objects;

public class NearestIdleElevatorStrategy implements ElevatorAssigningStrategy{

    @Override
    public Elevator assignElevator(int floorNumber, List<Elevator> elevatorList){
        Elevator nearestIdle = null;
        int minDistance = Integer.MAX_VALUE;

        // Finding nearest idle
        for(Elevator elevator: elevatorList){
            if(Objects.equals(elevator.getStatus(), Status.IDLE.name())){
                int distance = Math.abs(floorNumber-elevator.getCurrentFloor());
                if(distance < minDistance)
                {
                    minDistance = distance;
                    nearestIdle = elevator;
                }
            }
        }

        if (nearestIdle != null) return nearestIdle;

        //Nothing idle -> finding nearest with any status
        Elevator nearestAny = null;
        minDistance = Integer.MAX_VALUE;
        for(Elevator elevator: elevatorList){
            int distance = Math.abs(floorNumber-elevator.getCurrentFloor());
            if(distance < minDistance)
            {
                minDistance = distance;
                nearestAny = elevator;
            }
        }
        return nearestAny;

    }

}
