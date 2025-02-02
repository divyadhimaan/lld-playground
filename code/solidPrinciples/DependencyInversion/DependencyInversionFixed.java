// Abstraction for Mouse
interface Mouse {
    void click();
}

// Abstraction for Keyboard
interface Keyboard {
    void type();
}

// Concrete implementation of Wired Mouse
class WiredMouse2 implements Mouse {
    @Override
    public void click() {
        System.out.println("Wired mouse clicked.");
    }
}

// Concrete implementation of Wireless Mouse
class WirelessMouse2 implements Mouse {
    @Override
    public void click() {
        System.out.println("Wireless mouse clicked.");
    }
}

// Concrete implementation of Wired Keyboard
class WiredKeyboard2 implements Keyboard {
    @Override
    public void type() {
        System.out.println("Wired keyboard typing.");
    }
}

// Concrete implementation of Wireless Keyboard
class WirelessKeyboard2 implements Keyboard {
    @Override
    public void type() {
        System.out.println("Wireless keyboard typing.");
    }
}

// High-level class (Macbook) depends on abstractions, not concrete classes
class Macbook2 {
    private Mouse mouse;
    private Keyboard keyboard;

    // Constructor injection (Dependency Injection)
    public Macbook2(Mouse mouse, Keyboard keyboard) {
        this.mouse = mouse;
        this.keyboard = keyboard;
    }

    public void useDevice() {
        mouse.click();
        keyboard.type();
    }
}

// Client Code
public class DependencyInversionFixed {
    public static void main(String[] args) {
        // Using Wired Mouse and Wired Keyboard
        Mouse wiredMouse = new WiredMouse2();
        Keyboard wiredKeyboard = new WiredKeyboard2();
        Macbook2 macbook = new Macbook2(wiredMouse, wiredKeyboard);
        macbook.useDevice();

        // Using Wireless Mouse and Wireless Keyboard
        Mouse wirelessMouse = new WirelessMouse2();
        Keyboard wirelessKeyboard = new WirelessKeyboard2();
        Macbook2 macbook2 = new Macbook2(wirelessMouse, wirelessKeyboard);
        macbook2.useDevice();
    }
}
