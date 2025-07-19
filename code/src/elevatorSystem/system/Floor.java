package system;

import system.Button.ExternalButton;
import system.Elevator.Elevator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Floor {
    private final int floorNumber;
    private final Map<Direction, ExternalButton> directionButtons = new HashMap<>();
    private final List<Elevator> elevators;

    public Floor(int floorNumber, List<Elevator> elevators) {
        this.floorNumber = floorNumber;
        this.elevators = elevators;

        directionButtons.put(Direction.UP, new ExternalButton(floorNumber, Direction.UP));
        directionButtons.put(Direction.DOWN, new ExternalButton(floorNumber, Direction.DOWN));
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void pressButton(Direction direction) {
        if (directionButtons.containsKey(direction)) {
            directionButtons.get(direction).press();
        } else {
            System.out.println("Invalid direction for floor " + floorNumber);
        }
    }


    public ExternalButton getExternalButton(Direction direction) {
        return directionButtons.get(direction);
    }

    public List<Elevator> getElevators(){
        return elevators;
    }
}
