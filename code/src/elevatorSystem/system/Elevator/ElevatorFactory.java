package system.Elevator;

import system.Building;

public interface ElevatorFactory {
    Elevator createElevator(int id, int capacity, Building building);
}

