package system.Strategy;

import system.Elevator.Elevator;

import java.util.List;

public class OddEvenAssigningStrategy implements ElevatorAssigningStrategy {


    @Override
    public Elevator assignElevator(int floorNumber, List<Elevator> elevatorList)
    {
        boolean isEvenFloor = floorNumber % 2 == 0;

        for (Elevator elevator : elevatorList) {
            int elevatorId = elevator.getId();

            if (isEvenFloor && elevatorId % 2 == 0) {
                return elevator;
            } else if (!isEvenFloor && elevatorId % 2 != 0) {
                return elevator;
            }
        }
        return elevatorList.get(0);

    }
}
