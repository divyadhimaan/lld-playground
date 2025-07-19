package system.Button;

import system.Elevator.Elevator;

public class InternalButton{
    private int destinationFloor;
    private Elevator elevator;


    public InternalButton(int destinationFloor, Elevator elevator) {
        this.destinationFloor = destinationFloor;
        this.elevator = elevator;
    }

    public void press() {
        System.out.println("Internal button pressed for floor " + destinationFloor + " in Elevator " + elevator.getId());
//        elevator.displayStatus();
    }
    public int getFloor(){
        return destinationFloor;
    }
}
