package system.Button;

import system.Direction;

import java.util.Map;

public class ExternalButton{
    private final int floor;
    private final Direction direction;

    private Map<Integer, Direction> directionMap = Map.of(
            0, Direction.UP,
            1, Direction.DOWN
    );


    public ExternalButton(int floor, Direction direction) {
        this.floor = floor;
        this.direction = direction;
    }

    public void press() {
        System.out.println("External button pressed for floor " + floor + " for direction " + direction);
    }

    public int getFloor() {
        return floor;
    }

    public Direction getDirection() {
        return direction;
    }
}
