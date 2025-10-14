# ```O``` - Open Closed Principle (OCP)

> Class should be Open for Extension but closed for Modification


- Software entities should be open for extension, but closed for modifications
- Make minimal changes to existing code when introducing new functionality, should not modify already tested code.

![open-close.png](../../images/open-closed.png)
  

## Violation of OCP

```java
class Rectangle {
    public double length, width;
    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
}

class AreaCalculator {
    public double calculateArea(Object shape) {
        if (shape instanceof Rectangle) {
            Rectangle r = (Rectangle) shape;
            return r.length * r.width;
        }
        // Later, adding more shapes will require modifying this method
        return 0;
    }
}

```

### Problem:
- AreaCalculator is not closed for modification.
- Every time we add a new shape (e.g., Circle), we must modify the calculateArea method.


## Following OCP

```java
interface Shape {
    double area();
}

class Rectangle implements Shape {
    public double length, width;
    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
    public double area() {
        return length * width;
    }
}

class Circle implements Shape {
    public double radius;
    public Circle(double radius) {
        this.radius = radius;
    }
    public double area() {
        return Math.PI * radius * radius;
    }
}

class AreaCalculator {
    public double calculateArea(Shape shape) {
        return shape.area();  // Works for any new Shape without modification
    }
}

```

## Benefits:
- AreaCalculator is open for extension (new shapes can be added) but closed for modification.
- Adding a Triangle just requires creating a new class implementing Shape — no need to touch AreaCalculator.