# Abstract Factory: Design Pattern

> A creational design pattern that lets you produce families of related objects without specifying their concrete classes.
> 
> It's a factory of factories that creates objects that are designed to work together.

## When to use Abstract Factory Pattern

- When you need to create families of related products
- When you want to ensure that products from the same family are used together
- When you need to support multiple product lines or platforms
- When you want to hide the creation logic of complex object families
- When you need to switch between different families of products at runtime


## Real world analogy

- Think of different pizza restaurant chains like Domino's, Pizza Hut, and Brik Oven. 
- Each chain is like an abstract factory that creates a complete family of pizza products. 
- When you choose Domino's, you get their specific style of pizza base, sauce, cheese, and toppings that all work together. 
- You can't mix Pizza Hut's base with Domino's sauce - each factory creates products designed to complement each other within that brand family.


## Problem Solved

- Ensures that related products are created together and are compatible
- Provides consistency across product families
- Makes it easy to switch between different families of products
- Encapsulates the creation of complex object families
- Promotes loose coupling between client code and concrete product classes
- Makes adding new product families easier without changing existing code

## Class Structure

![abstract-factory-class-structure.png](../../images/structure/abstract-factory.png)

## Violation Code

[Pizza Factory - Violation Code](../../code/designPatterns/abstractFactory/AbstractFactoryViolation.java)

```mermaid
classDiagram
    %% --- Dominos Product Family ---
    class DominosMargheritaPizza2 {
        +prepare()
    }
    class DominosTomatoSauce2 {
        +addSauce()
    }
    class DominosMozzarella2 {
        +addCheese()
    }

    %% --- PizzaHut Product Family ---
    class PizzaHutMargheritaPizza2 {
        +prepare()
    }
    class PizzaHutTomatoSauce2 {
        +addSauce()
    }
    class PizzaHutMozzarella2 {
        +addCheese()
    }

    %% --- Client Classes ---
    class PizzaStore2 {
        -storeType : String
        +makePizza()
    }

    class BadPizzaStore {
        +makeMixedPizza()
        +makeAnotherPizza(pizzaType, sauceType, cheeseType)
    }

    class UncoordinatedPizzaFactory {
        +createPizza(brand, type)
        +createSauce(brand, type)
        +createCheese(brand, type)
    }

    %% --- Violations (Direct Dependencies) ---
    PizzaStore2 --> DominosMargheritaPizza2
    PizzaStore2 --> DominosTomatoSauce2
    PizzaStore2 --> DominosMozzarella2
    PizzaStore2 --> PizzaHutMargheritaPizza2
    PizzaStore2 --> PizzaHutTomatoSauce2
    PizzaStore2 --> PizzaHutMozzarella2

    BadPizzaStore --> DominosMargheritaPizza2
    BadPizzaStore --> PizzaHutTomatoSauce2
    BadPizzaStore --> DominosMozzarella2
    BadPizzaStore --> PizzaHutMozzarella2

    UncoordinatedPizzaFactory --> DominosMargheritaPizza2
    UncoordinatedPizzaFactory --> DominosTomatoSauce2
    UncoordinatedPizzaFactory --> DominosMozzarella2
    UncoordinatedPizzaFactory --> PizzaHutMargheritaPizza2
    UncoordinatedPizzaFactory --> PizzaHutTomatoSauce2
    UncoordinatedPizzaFactory --> PizzaHutMozzarella2

```

### Issue with the Violation Code
1. No family consistency guarantee - can mix Dominos pizza with Pizza Hut sauce
2. Tight coupling - client code depends on concrete product classes
3. Violates Open/Closed Principle - adding new families requires modifying existing code
4. Code duplication - creation logic repeated in multiple places
5. Error-prone - easy to accidentally mix product families
6. Hard to maintain - changes to product families affect multiple classes
7. No polymorphism - can't treat different families uniformly
8. Violates Single Responsibility - client handles both business logic and product creation
9. Complex conditional logic - becomes unwieldy with more families
10. Type safety issues - casting required with uncoordinated factory methods

## Enhanced Code

[Pizza Factory - Sample](../../code/designPatterns/abstractFactory/AbstractFactorySample.java)

```mermaid
classDiagram

%% Abstract Product Interfaces
    class Pizza {
        <<interface>>
        +prepare(): void
    }

    class Sauce {
        <<interface>>
        +addSauce(): void
    }

    class Cheese {
        <<interface>>
        +addCheese(): void
    }

%% Concrete Products for Dominos
    class DominosMargheritaPizza {
        +prepare(): void
    }
    Pizza <|.. DominosMargheritaPizza

    class DominosTomatoSauce {
        +addSauce(): void
    }
    Sauce <|.. DominosTomatoSauce

    class DominosMozzarella {
        +addCheese(): void
    }
    Cheese <|.. DominosMozzarella

%% Concrete Products for PizzaHut
    class PizzaHutMargheritaPizza {
        +prepare(): void
    }
    Pizza <|.. PizzaHutMargheritaPizza

    class PizzaHutTomatoSauce {
        +addSauce(): void
    }
    Sauce <|.. PizzaHutTomatoSauce

    class PizzaHutMozzarella {
        +addCheese(): void
    }
    Cheese <|.. PizzaHutMozzarella

%% Abstract Factory
    class PizzaFactory {
        <<interface>>
        +createPizza(): Pizza
        +createSauce(): Sauce
        +createCheese(): Cheese
    }

%% Concrete Factories
    class DominosPizzaFactory {
        +createPizza(): Pizza
        +createSauce(): Sauce
        +createCheese(): Cheese
    }
    PizzaFactory <|.. DominosPizzaFactory

    class PizzaHutPizzaFactory {
        +createPizza(): Pizza
        +createSauce(): Sauce
        +createCheese(): Cheese
    }
    PizzaFactory <|.. PizzaHutPizzaFactory

%% Client
    class PizzaStore {
        -factory: PizzaFactory
        +PizzaStore(factory: PizzaFactory)
        +makePizza(): void
    }

%% Usage
    class AbstractFactorySample {
        +main(String[]): void
    }

    PizzaStore --> PizzaFactory
    PizzaStore --> Pizza
    PizzaStore --> Sauce
    PizzaStore --> Cheese
    AbstractFactorySample --> PizzaStore


```


## Common LLD Problems Using Abstract Factory Pattern:

### 1. Cross-Platform UI Toolkit
- **Abstract Factory:** `WidgetFactory`
- **Products:** `Button`, `Checkbox`, `TextField`
- **Variants:** `MacWidgetFactory`, `WindowsWidgetFactory`, `LinuxWidgetFactory`
- **Context:** UI components differ by OS but belong to the same "family".

---

### 2. Theme-Based UI System (Dark/Light Mode)
- **Abstract Factory:** `UIThemeFactory`
- **Products:** `ThemedButton`, `ThemedNavbar`, `ThemedCard`
- **Variants:** `DarkThemeFactory`, `LightThemeFactory`
- **Context:** Dynamically render full UI component sets based on selected theme.

---

### 3. Cloud Infrastructure Provisioning
- **Abstract Factory:** `CloudResourceFactory`
- **Products:** `Compute`, `Storage`, `Database`
- **Variants:** `AWSFactory`, `AzureFactory`, `GCPFactory`
- **Context:** Provision services in a cloud-agnostic way.

---

### 4. Operating System Driver Loader
- **Abstract Factory:** `DriverFactory`
- **Products:** `KeyboardDriver`, `MouseDriver`, `PrinterDriver`
- **Variants:** `WindowsDriverFactory`, `LinuxDriverFactory`, `MacDriverFactory`
- **Context:** Load platform-specific drivers transparently.

---

### 5. Game Environment Generator
- **Abstract Factory:** `GameEnvironmentFactory`
- **Products:** `Enemy`, `Terrain`, `NPC`
- **Variants:** `DesertWorldFactory`, `SnowWorldFactory`, `ForestWorldFactory`
- **Context:** Generate game entities tailored to level/environment.

---

### 6. Car Manufacturing System
- **Abstract Factory:** `CarPartsFactory`
- **Products:** `Engine`, `Tyre`, `Suspension`
- **Variants:** `LuxuryCarPartsFactory`, `SportsCarPartsFactory`
- **Context:** Build cars from consistent component sets.

---

### 7. OS-Specific File System or IO API
- **Abstract Factory:** `FileSystemFactory`
- **Products:** `File`, `Directory`, `PathResolver`
- **Variants:** `UnixFileSystemFactory`, `WindowsFileSystemFactory`
- **Context:** Provide a platform-independent file handling system.

---

### 8. Cross-Device Media Renderer
- **Abstract Factory:** `MediaRendererFactory`
- **Products:** `VideoPlayer`, `AudioPlayer`, `SubtitleRenderer`
- **Variants:** `MobileRendererFactory`, `DesktopRendererFactory`, `SmartTVRendererFactory`
- **Context:** Render media differently based on the device type.

---


| References | Links                                                                                     |
|------------|-------------------------------------------------------------------------------------------|
| Article Reference | [Refactoring Guru](https://refactoring.guru/design-patterns/abstract-factory)             |
| Boiler Plate Code | [Observer Example](../../code/designPatterns/abstractFactory/AbstractFactoryExample.java) |
