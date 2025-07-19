package system.Strategy;

import system.Elevator.Elevator;

import java.util.List;

public interface ElevatorAssigningStrategy {
    public Elevator assignElevator(int floors, List<Elevator> elevatorList);
}
