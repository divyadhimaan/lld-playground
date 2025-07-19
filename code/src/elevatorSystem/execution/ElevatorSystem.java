package execution;

import system.Building;

import system.Direction;
import system.Elevator.Elevator;
import system.Floor;
import system.Strategy.ElevatorAssigningStrategy;
import system.Strategy.NearestIdleElevatorStrategy;

import java.util.List;
import java.util.Scanner;

public class ElevatorSystem {
    public static void main(String[] args) throws InterruptedException {
        int totalFloors = 10;
        int numberOfElevators = 2;
        Building building = Building.getInstance(totalFloors, numberOfElevators);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Add current floor. (0-" + (totalFloors-1) + ")");
        int currentFloor = scanner.nextInt();

        Floor floor = building.getFloor(currentFloor);
        List<Elevator> elevators = floor.getElevators();

        System.out.println("Press the option (1 for UP and 2 for Down)");
        int directionInput = scanner.nextInt();
        switch (directionInput){
            case 1 -> building.getFloor(currentFloor).pressButton(Direction.UP);
            case 2 -> building.getFloor(currentFloor).pressButton(Direction.DOWN);
            default -> System.out.println("Invalid Direction");
        }

        ElevatorAssigningStrategy strategy = new NearestIdleElevatorStrategy();
        Elevator assignedElevator = strategy.assignElevator(currentFloor, elevators);
        System.out.println("Elevator "+assignedElevator.getId() + " assigned!");

        System.out.println("Pick a destination floor: ");
        assignedElevator.displayInternalButtons();
        int destinationFloor = scanner.nextInt();

        building.getElevator(assignedElevator.getId()).pressButton(currentFloor, destinationFloor);

    }
}
