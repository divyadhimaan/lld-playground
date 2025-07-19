package system;

import system.Elevator.ConcreteElevatorFactory;
import system.Elevator.Elevator;
import system.Elevator.ElevatorFactory;

import java.util.ArrayList;
import java.util.List;

public class Building {
    private static Building building;
    private List<Elevator> elevators;
    private List<Floor> floors = new ArrayList<>();
    private int totalFloors = 0;
    private final int MAX_CAPACITY_FOR_ELEVATOR = 10;

    private Building(int numberOfFloors, int numberOfElevators) {
        elevators = new ArrayList<>();
        ElevatorFactory factory = new ConcreteElevatorFactory();
        this.totalFloors = numberOfFloors;
        for (int i = 0; i < numberOfElevators; i++) {
            addElevator(factory.createElevator(i + 1, MAX_CAPACITY_FOR_ELEVATOR, this));
        }

        for (int i = 0; i < numberOfFloors; i++) {
            addFloor(new Floor(i, elevators));
        }

        System.out.println("Initialized Building with " + floors.size() + " floors and " + numberOfElevators + " elevators.");

    }

    public static synchronized Building getInstance(int numberOfFloors, int numberOfElevators) {
        if(building == null) {
            building = new Building(numberOfFloors, numberOfElevators);
        }
        return building;
    }
    
    public void addElevator(Elevator elevator) {
        elevators.add(elevator);
    }

    public void addFloor(Floor floor) {
        floors.add(floor);
    }

    public int getTotalFloors(){ return this.totalFloors; }

    public Elevator getElevator(int index) {
        if (index >= 0 && index < elevators.size()) {
            return elevators.get(index);
        } else {
            System.out.println("Invalid elevator index.");
            return null;
        }
    }
    
    public Floor getFloor(int floorNumber){
        return floors.get(floorNumber);
    }

}

