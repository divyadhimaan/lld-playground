package bridge;


// Implementation hierarchy (Devices)
interface Device {
    void on();
    void off();
}

class TV implements Device {
    public void on() {
        System.out.println("TV turned ON");
    }
    public void off() {
        System.out.println("TV turned OFF");
    }
}

class Radio implements Device {
    public void on() {
        System.out.println("Radio turned ON");
    }
    public void off() {
        System.out.println("Radio turned OFF");
    }
}

// Abstraction hierarchy (Remote)
abstract class Remote {
    protected Device device;

    public Remote(Device device) {
        this.device = device;
    }

    public abstract void on();
    public abstract void off();
}

class BasicRemote extends Remote {
    public BasicRemote(Device device) {
        super(device);
    }

    public void on() {
        device.on();
    }

    public void off() {
        device.off();
    }
}

class AdvancedRemote extends Remote {
    public AdvancedRemote(Device device) {
        super(device);
    }

    public void on() {
        System.out.println("Using advanced remote...");
        device.on();
    }

    public void off() {
        System.out.println("Using advanced remote...");
        device.off();
    }
}

// Client
public class BridgeExample {
    public static void main(String[] args) {
        Remote tvRemote = new BasicRemote(new TV());
        tvRemote.on();
        tvRemote.off();

        Remote radioRemote = new AdvancedRemote(new Radio());
        radioRemote.on();
        radioRemote.off();
    }
}
