package system.Elevator;

import system.Building;

public class ConcreteElevatorFactory implements ElevatorFactory{
    @Override
    public Elevator createElevator(int id, int capacity, Building building) {
        return new Elevator(id, capacity, building);
    }

}
