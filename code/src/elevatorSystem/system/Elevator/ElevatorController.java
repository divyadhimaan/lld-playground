package system.Elevator;

import system.Building;
import system.Display;
import system.Status;


public class ElevatorController {

    private static ElevatorController controller;
    private Display display;
    private Building building;


    ElevatorController(Building building){
        this.display = new Display();
        setBuilding(building);
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    private Building getBuilding() {
        return building;
    }

    public static synchronized ElevatorController getInstance(Building building){
        if(controller == null)
            controller =  new ElevatorController(building);
        return controller;
    }

    public void moveElevatorForUser(Elevator elevator, int fromFloor, int toFloor) throws InterruptedException {

        if(elevator.getCurrentFloor() != fromFloor) {
            goToFloor(elevator, fromFloor);
        }else {
            System.out.println("Elevator already at floor: " + fromFloor);
        }

        System.out.println("Elevator " + elevator.getId() + " is moving from floor " + fromFloor + " to floor " + toFloor);

        goToFloor(elevator, toFloor);
        elevator.setStatus(Status.IDLE);

    }

    private void goToFloor(Elevator elevator, int floorNumber) throws InterruptedException {
        if (floorNumber < 0 || floorNumber >= building.getTotalFloors()) {
            throw new IllegalArgumentException("Invalid floor number: " + floorNumber);
        }
        elevator.setStatus(Status.MOVING);
        displayStatus(elevator);

        elevator.move(floorNumber);
        handleDoor(elevator);

        elevator.setStatus(Status.IDLE);
        displayStatus(elevator);
    }

    private void handleDoor(Elevator elevator) throws InterruptedException {
        elevator.openDoor();
        Thread.sleep(2000);
        elevator.closeDoor();
    }

    public void displayStatus(Elevator elevator) {
        display.showStatus(elevator);
    }
}
