// Concrete classes for Mouse and Keyboard
class WiredMouse {
    public void click() {
        System.out.println("Wired mouse clicked.");
    }
}

class WirelessMouse {
    public void click() {
        System.out.println("Wireless mouse clicked.");
    }
}

class WiredKeyboard {
    public void type() {
        System.out.println("Wired keyboard typing.");
    }
}

class WirelessKeyboard {
    public void type() {
        System.out.println("Wireless keyboard typing.");
    }
}

// Macbook class directly depends on concrete classes (violation of DIP)
class Macbook {
    private WiredMouse2 wiredMouse2;
    private WiredKeyboard2 wiredKeyboard2;

    // Constructor where the concrete classes are directly initialized
    public Macbook() {
        this.wiredMouse2 = new WiredMouse2();
        this.wiredKeyboard2 = new WiredKeyboard2();
    }

    public void useDevice() {
        wiredMouse2.click();
        wiredKeyboard2.type();
    }
}


public class DependencyInversionViolation {
    public static void main(String[] args) {
        // Macbook directly uses concrete classes (violation)
        Macbook macbook = new Macbook();
        macbook.useDevice();
    }
}
