package state;

// State Interface
interface TrafficLightState {
    void change(TrafficLight context);
}

// Concrete States
class RedState implements TrafficLightState {
    public void change(TrafficLight context) {
        System.out.println("STOP the cars");
        context.setState(new GreenState());
    }
}

class GreenState implements TrafficLightState {
    public void change(TrafficLight context) {
        System.out.println("ALLOW cars to move");
        context.setState(new YellowState());
    }
}

class YellowState implements TrafficLightState {
    public void change(TrafficLight context) {
        System.out.println("SLOW DOWN the cars");
        context.setState(new RedState());
    }
}

// Context Class
class TrafficLight {
    private TrafficLightState currentState;

    public TrafficLight() {
        this.currentState = new RedState(); // Initial state
    }

    public void setState(TrafficLightState state) {
        this.currentState = state;
    }

    public void change() {
        currentState.change(this);
    }
}

// Usage
public class StateSample {
    public static void main(String[] args) {
        TrafficLight signal = new TrafficLight();
        signal.change(); // RED → GREEN
        signal.change(); // GREEN → YELLOW
        signal.change(); // YELLOW → RED
    }
}

