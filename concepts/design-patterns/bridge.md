# Bridge: Structural Design Pattern

> The Bridge Pattern decouples an abstraction from its implementation so that the two can evolve independently. 
> 
> It separates what is done from how it is done.

![representation](../../images/bridge-dp.png)

## When to use Bridge Pattern
- When a class has multiple dimensions of variation (e.g., shapes × colors, devices × remotes).
- When you want to avoid class explosion caused by combining multiple features in subclasses.
- When you need both abstraction and implementation to evolve independently.

## Real World Analogy

- A Remote Control is the abstraction. 
- Different Devices (TV, Radio, Projector) are the implementations. 
- You can use the same remote to control different devices, and devices can work with different remotes. 
- Without Bridge, you’d need TVRemote, RadioRemote, ProjectorRemote separately (class explosion).

## What Problem it solves

- Eliminates the need for creating a new class for each abstraction–implementation combination. 
- Promotes flexibility: You can change abstraction and implementation independently. 
- Reduces tight coupling between abstraction and implementation.

## Class Structure
![bridge-class-structure](../../images/structure/bridge.png)

## Violation Code

[Remote Control - Violation Code](./../../code/designPatterns/bridge/BridgeViolation.java)

```mermaid
classDiagram
    class TVRemote {
        +on() void
        +off() void
    }

    class RadioRemote {
        +on() void
        +off() void
    }

    class BridgeViolation {
        +main(args: String[]) void
    }

    BridgeViolation --> TVRemote
    BridgeViolation --> RadioRemote


```

### Issues with above code

1. Class explosion: Adding a new device requires a new remote class. 
2. No flexibility: Remote and device are tightly bound. 
3. Poor maintainability: Changes in one require changes in the other.

## Enhanced Code

[Remote Control - Example Code](./../../code/designPatterns/bridge/BridgeExample.java)

```mermaid
classDiagram
    %% Implementation Hierarchy
    class Device {
        <<interface>>
        +on() void
        +off() void
    }

    class TV {
        +on() void
        +off() void
    }

    class Radio {
        +on() void
        +off() void
    }

    Device <|.. TV
    Device <|.. Radio

    %% Abstraction Hierarchy
    class Remote {
        <<abstract>>
        #Device device
        +Remote(device: Device)
        +on() void
        +off() void
    }

    class BasicRemote {
        +BasicRemote(device: Device)
        +on() void
        +off() void
    }

    class AdvancedRemote {
        +AdvancedRemote(device: Device)
        +on() void
        +off() void
    }

    Remote <|-- BasicRemote
    Remote <|-- AdvancedRemote
    Remote --> Device

    %% Client
    class BridgeExample {
        +main(args: String[]) void
    }

    BridgeExample --> Remote

```

## Common LLD problems using Bridge Pattern

1. **Remote Control System**
    - Decouple `Remote` abstraction from different `Device` implementations (TV, Radio).

2. **Payment System**
    - Separate `PaymentMethod` abstraction (CreditCard, UPI) from `PaymentGateway` implementation (PayPal, Stripe).

3. **Drawing Application**
    - Split `Shape` abstraction (Circle, Rectangle) from `RenderingAPI` (OpenGL, DirectX).

4. **Notification System**
    - Decouple `Notification` type (Email, SMS, Push) from `MessageSender` implementation.

5. **Document Management System**
    - Separate `Document` abstraction (PDF, Word) from `Storage` implementation (Local, Cloud).

6. **Audio Player**
    - Bridge between `MediaPlayer` abstraction (BasicPlayer, AdvancedPlayer) and `AudioFormat` implementation (MP3, WAV, FLAC).

7. **Report Generation System**
    - Abstract `Report` (Summary, Detailed) from `ExportFormat` (PDF, Excel, HTML).

8. **E-commerce Discount System**
    - Separate `Discount` abstraction (Seasonal, Festival, Loyalty) from `CalculationStrategy` implementation.