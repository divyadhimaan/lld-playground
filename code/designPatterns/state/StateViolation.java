package state;

class TrafficSignal {
    private String state;

    public TrafficSignal() {
        this.state = "RED";
    }

    public void change() {
        if (state.equals("RED")) {
            System.out.println("STOP the cars");
            state = "GREEN";
        } else if (state.equals("GREEN")) {
            System.out.println("ALLOW cars to move");
            state = "YELLOW";
        } else if (state.equals("YELLOW")) {
            System.out.println("SLOW DOWN the cars");
            state = "RED";
        }
    }
}

public class StateViolation {
    public static void main(String[] args) {
        TrafficSignal signal = new TrafficSignal();
        signal.change(); // RED → GREEN
        signal.change(); // GREEN → YELLOW
        signal.change(); // YELLOW → RED
    }
}
