package system.Button;

import com.sun.jdi.request.InvalidRequestStateException;
import system.Elevator.Elevator;
import system.Elevator.ElevatorController;

import java.util.HashMap;
import java.util.Map;

public class InternalButtonPanel{
   private Map<Integer, InternalButton> buttons;
    private Elevator elevator;


    public InternalButtonPanel(Elevator elevator, int totalFloors) {
        this.elevator = elevator;
        buttons = new HashMap<>();

        for(int floor = 0; floor < totalFloors; floor++) {
            addButton(floor, new InternalButton(floor, elevator));
        }
    }

    public void addButton(int floor, InternalButton button) {
        buttons.put(floor, button);
    }

    public void press(ElevatorController controller, int floor) {
        if (buttons.containsKey(floor)) {
            buttons.get(floor).press();
        }
        System.out.println("Button pressed in Elevator " + elevator.getId());
    }

    public void getButtonPanel() {
        System.out.println("Internal Button Panel for Elevator " + elevator.getId() + ":");
        for (Map.Entry<Integer, InternalButton> entry : buttons.entrySet()) {
            System.out.println("Button for floor " + entry.getKey());
        }
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }

    public void display(){
        if(elevator == null)
            throw new InvalidRequestStateException("Elevator not set");

        StringBuilder sb = new StringBuilder("Internal Button Panel for Elevator " + elevator.getId() + ":\n");
        int count = 0;
        for (Map.Entry<Integer, InternalButton> button : buttons.entrySet()) {
            sb.append(button.getKey()).append(" ");
            count++;
            if (count % 3 == 0) {
                sb.append("\n");
            }
        }
        System.out.println(sb);
    }
}
