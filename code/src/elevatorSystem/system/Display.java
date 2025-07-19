package system;

import system.Elevator.Elevator;

public class Display {
    public void showStatus(Elevator elevator) {
        StringBuilder sb = new StringBuilder();
        sb.append("| ");
        sb.append("Elevator ID: ").append(elevator.getId()).append(" | ");
        sb.append("Status: ").append(elevator.getStatus()).append(" | ");
        sb.append("Current Floor: ").append(elevator.getCurrentFloor()).append(" | ");
//        sb.append("Capacity: ").append(elevator.getCapacity()).append(" | ");

        System.out.println(sb);
    }

    public void showFloor(int floor) {
        System.out.println("Current Floor: " + floor);
    }

    public void showCapacity(int capacity) {
        System.out.println("Elevator Capacity: " + capacity);
    }
}
