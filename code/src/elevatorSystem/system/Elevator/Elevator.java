package system.Elevator;

import system.Building;
import system.Button.InternalButtonPanel;
import system.Direction;
import system.Status;

public class Elevator{
    
    private final int id;
    private final int capacity;

    private Status status = Status.IDLE;
    private Direction currentDirection = Direction.UP;

    private int currentFloor;
    InternalButtonPanel buttonPanel;
    ElevatorController elevatorController;

    public Elevator(int id, int capacity, Building building) {
        this.id = id;
        this.capacity = capacity;

        this.elevatorController = ElevatorController.getInstance(building);
        this.buttonPanel = new InternalButtonPanel(this, building.getTotalFloors());
    }

    public void move(int floorNumber){
        this.currentFloor = floorNumber;
        System.out.println("Elevator " + id + " has arrived at floor " + currentFloor);
    }

    public void pressButton(int startFloor, int destinationFloor) throws InterruptedException {
        buttonPanel.press(elevatorController, destinationFloor);
        elevatorController.moveElevatorForUser(this, startFloor, destinationFloor);
    }

    public void displayInternalButtons(){
        buttonPanel.display();
    }

    public void openDoor() {
        System.out.println("Opening Door...");
    }

    public void closeDoor() {
        System.out.println("Closing Door...");
    }

    public int getId() {
        return id;
    }
    public int getCapacity() {
        return capacity;
    }
    public int getCurrentFloor(){
        return currentFloor;
    }
    public String getStatus() {
        return status.name();
    }
    public void setStatus(Status status){
        this.status = status;
    }


}

