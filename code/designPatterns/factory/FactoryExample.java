package factory;

// Product Interface
interface Shape{
    void draw();
}

// Concrete Products - Defines the behavior and properties of the object.
class Rectangle implements Shape{
    @Override
    public void draw() {
        System.out.println("Draw factory.Rectangle");
    }
}

// Concrete Products - Defines the behavior and properties of the object.
class Square implements Shape{
    @Override
    public void draw() {
        System.out.println("Draw factory.Square");
    }
}
// Concrete Products - Defines the behavior and properties of the object.
class Circle implements Shape{
    @Override
    public void draw() {
        System.out.println("Draw factory.Circle");
    }
}

//The creator is responsible for creating product instances, typically by defining an abstract method that is implemented by subclasses.
class ShapeFactory{

    // creator
    Shape getShape(String input)
    {
        return switch (input) {
            case "CIRCLE" -> new Circle();
            case "RECTANGLE" -> new Rectangle();
            case "SQUARE" -> new Square();
            default -> null;
        };
    }
}


public class FactoryExample {
    public static void main(String[] args)
    {
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape shape1 = shapeFactory.getShape("CIRCLE");
        shape1.draw();

        Shape shape2 = shapeFactory.getShape("SQUARE");
        shape2.draw();


    }
}
