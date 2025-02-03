//Abstract Products: Button and Checkbox are the abstract product interfaces.
//Concrete Products: LightButton, DarkButton, LightCheckbox, and DarkCheckbox are the concrete implementations of the Button and Checkbox interfaces for different themes.
//Abstract Factory: GUIFactory is the abstract factory that defines methods for creating buttons and checkboxes.
//Concrete Factories: LightThemeFactory and DarkThemeFactory are the concrete factories that implement the GUIFactory interface. Each factory creates products that are part of a specific theme (either light or dark).
//Client Code:The client code (in AbstractFactoryExample) uses the abstract factory interface (GUIFactory) to create the UI components (buttons and checkboxes). Depending on which concrete factory is used, the client receives either a light theme or a dark theme UI.


//-------------------------------------------------------------------------------------------------------

// Abstract Product 1: Button
interface Button {
    void render();
}

// Abstract Product 2: Checkbox
interface Checkbox {
    void render();
}

// Concrete Product 1A: Light Button
class LightButton implements Button {
    @Override
    public void render() {
        System.out.println("Rendering Light Button");
    }
}

// Concrete Product 1B: Dark Button
class DarkButton implements Button {
    @Override
    public void render() {
        System.out.println("Rendering Dark Button");
    }
}

// Concrete Product 2A: Light Checkbox
class LightCheckbox implements Checkbox {
    @Override
    public void render() {
        System.out.println("Rendering Light Checkbox");
    }
}

// Concrete Product 2B: Dark Checkbox
class DarkCheckbox implements Checkbox {
    @Override
    public void render() {
        System.out.println("Rendering Dark Checkbox");
    }
}

// Abstract Factory: GUIFactory
interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

// Concrete Factory 1: LightThemeFactory
class LightThemeFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new LightButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new LightCheckbox();
    }
}

// Concrete Factory 2: DarkThemeFactory
class DarkThemeFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new DarkButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new DarkCheckbox();
    }
}

// Client Code: Application
public class AbstractFactoryExample {
    public static void main(String[] args) {
        // Create a factory for the light theme
        GUIFactory lightFactory = new LightThemeFactory();
        Button lightButton = lightFactory.createButton();
        Checkbox lightCheckbox = lightFactory.createCheckbox();

        lightButton.render();  // Rendering Light Button
        lightCheckbox.render();  // Rendering Light Checkbox

        // Create a factory for the dark theme
        GUIFactory darkFactory = new DarkThemeFactory();
        Button darkButton = darkFactory.createButton();
        Checkbox darkCheckbox = darkFactory.createCheckbox();

        darkButton.render();  // Rendering Dark Button
        darkCheckbox.render();  // Rendering Dark Checkbox
    }
}