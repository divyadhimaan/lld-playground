# Factory: Creational Design Pattern

>provides an interface for creating objects in a superclass, but allows subclasses to alter the type of objects that will be created.
> 
>  It encapsulates object creation logic in a separate method or class, allowing the system to create objects based on given parameters or conditions.

## When to use Factory Method Pattern

- When you don't know beforehand the exact types of objects your code should work with
- When you want to provide a library of products and reveal only their interfaces
- When you need to centralize object creation logic
- When object creation involves complex logic that shouldn't be repeated
- When you want to decouple object creation from object usage

## Real world analogy

- Think of a pizza restaurant kitchen. 
- When you order a pizza, you don't go into the kitchen and make it yourself. 
- You simply tell the chef what type of pizza you want (Margherita, Pepperoni, Veggie), and the kitchen factory produces the specific pizza for you. 
- The kitchen knows all the recipes and ingredients needed - you just specify the type, and the factory handles the complex creation process.

## Problem Solved

- Eliminates the need for clients to know specific classes
- Centralizes object creation logic in one place
- Makes it easy to add new product types without changing existing code
- Reduces coupling between classes
- Provides a consistent interface for object creation
- Handles complex object initialization logic

The Factory Method pattern suggests that you replace direct object construction calls (using the `new` operator) with calls to a special factory method.

## Class Structure

![factory-class-structure.png](../../images/structure/factory.png)

## Violation code

[Pizza factory - Violation Code](../../code/designPatterns/factory/FactoryViolation.java)

```mermaid
classDiagram

%% Product classes (no shared interface)
    class MargheritaPizza2 {
        +prepare()
        +bake()
        +serve()
    }

    class PepperoniPizza2 {
        +prepare()
        +bake()
        +serve()
    }

    class VeggiePizza2 {
        +prepare()
        +bake()
        +serve()
    }

%% Client classes with duplicated creation logic
    class PizzaRestaurant {
        +orderPizza(pizzaType)
    }

    class PizzaDeliveryService {
        +processPizzaOrder(pizzaType)
    }

    PizzaRestaurant --> MargheritaPizza2
    PizzaRestaurant --> PepperoniPizza2
    PizzaRestaurant --> VeggiePizza2

    PizzaDeliveryService --> MargheritaPizza2
    PizzaDeliveryService --> PepperoniPizza2
    PizzaDeliveryService --> VeggiePizza2

```

### Issues with above code
1. Tight coupling - Client code depends directly on concrete pizza classes
2. Violates Open/Closed Principle - adding new pizza types requires modifying existing code
3. Code duplication - object creation logic repeated in multiple places
4. Violates Single Responsibility - client classes handle both business logic AND object creation
5. Hard to maintain - changes to object creation affect multiple classes
6. No centralized creation logic - creation scattered throughout application
7. Difficult to test - can't easily substitute mock objects
8. Violates DRY principle - same creation logic written multiple times

## Enhanced Code

[Pizza factory - Sample](../../code/designPatterns/factory/FactorySample.java)

```mermaid
classDiagram

class Pizza {
    <<interface>>
    +prepare(): void
    +bake(): void
    +serve(): void
}

class MargheritaPizza {
    +prepare(): void
    +bake(): void
    +serve(): void
}
Pizza <|.. MargheritaPizza

class PepperoniPizza {
    +prepare(): void
    +bake(): void
    +serve(): void
}
Pizza <|.. PepperoniPizza

class VeggiePizza {
    +prepare(): void
    +bake(): void
    +serve(): void
}
Pizza <|.. VeggiePizza

class PizzaFactory {
    +createPizza(pizzaType: String): Pizza
}

class PizzaFactoryExample {
    +main(String[]): void
}

PizzaFactoryExample --> PizzaFactory
PizzaFactory --> Pizza

```
## Common LLD Problems Using Factory Pattern:

### 1. Notification Sender System
- **Factory:** `NotificationFactory.create("email" | "sms" | "push")`
- **Products:** `EmailNotification`, `SMSNotification`, `PushNotification`
- **Context:** Create the right notification handler without exposing instantiation logic.

---

### 2. Food Ordering / Pizza System
- **Factory:** `PizzaFactory.create("margherita" | "veggie" | "pepperoni")`
- **Products:** `MargheritaPizza`, `VeggiePizza`, `PepperoniPizza`
- **Context:** Decouple pizza creation from ordering logic.

---

### 3. Shape Drawing Tool / Graphics Editor
- **Factory:** `ShapeFactory.create("circle" | "rectangle" | "triangle")`
- **Products:** `Circle`, `Rectangle`, `Triangle`
- **Context:** Used to create different shape objects in drawing or diagramming tools.

---

### 4. Payment Gateway Integration
- **Factory:** `PaymentFactory.getPayment("upi" | "credit_card" | "paypal")`
- **Products:** `UPIPayment`, `CreditCardPayment`, `PayPalPayment`
- **Context:** Abstract away the initialization of payment processors.

---

### 5. Document Reader / File Parser
- **Factory:** `ParserFactory.getParser("pdf" | "docx" | "csv")`
- **Products:** `PDFParser`, `DocxParser`, `CSVParser`
- **Context:** Read files of various formats without hardcoding parser logic.

---

### 6. Vehicle Manufacturing System
- **Factory:** `VehicleFactory.getVehicle("car" | "bike" | "truck")`
- **Products:** `Car`, `Bike`, `Truck`
- **Context:** Manage object creation for different vehicle types.

---

### 7. Cloud Provider SDK Generator
- **Factory:** `CloudFactory.getProvider("aws" | "gcp" | "azure")`
- **Products:** `AWSClient`, `GCPClient`, `AzureClient`
- **Context:** Simplify instantiation and configuration of cloud-specific clients.

---

### 8. Database Connection Provider
- **Factory:** `ConnectionFactory.getConnection("mysql" | "postgres" | "mongodb")`
- **Products:** `MySQLConnection`, `PostgresConnection`, `MongoDBConnection`
- **Context:** Unified access to different database types without changing business logic.

---


| References | Links                                                                       |
|------------|-----------------------------------------------------------------------------|
| Article Reference | [Refactoring Guru](https://refactoring.guru/design-patterns/factory-method) |
| Boiler Plate Code | [Factory Example](../../code/designPatterns/factory/FactoryExample.java)    |


