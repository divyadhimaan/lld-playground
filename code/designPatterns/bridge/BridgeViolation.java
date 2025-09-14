package bridge;

// Remote and Device are tightly coupled
class TVRemote {
    public void on() {
        System.out.println("TV turned ON with Remote");
    }
    public void off() {
        System.out.println("TV turned OFF with Remote");
    }
}

class RadioRemote {
    public void on() {
        System.out.println("Radio turned ON with Remote");
    }
    public void off() {
        System.out.println("Radio turned OFF with Remote");
    }
}

// Client must create separate class for each combination
public class BridgeViolation {
    public static void main(String[] args) {
        TVRemote tvRemote = new TVRemote();
        tvRemote.on();

        RadioRemote radioRemote = new RadioRemote();
        radioRemote.off();
    }
}
