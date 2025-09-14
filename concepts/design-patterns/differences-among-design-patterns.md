# Builder v/s Decorator


#### Analogy where both might confuse:

You walk into a coffee shop. You want your ideal cup of coffee, possibly with different ingredients and extra add-ons.

- `Builder Pattern Thinking`: I want to construct a specific coffee step-by-step, with full control over each part. 
  - You are focused on constructing a product from the ground up, one configuration at a time.
  - You assemble ingredients using a `CoffeeBuilder`.
- `Decorator Pattern Thinking`: I already have a base coffee, now I want to add features dynamically.
  - You’re wrapping and layering behaviors on top of a ready-made coffee.
  - You start with a `BasicCoffee` object. 
  - You wrap it with `ExtraShot`, then `WhippedCreamDecorator`, and so on.




| Aspect               | **Builder Pattern**                                             | **Decorator Pattern**                                               |
|----------------------|-----------------------------------------------------------------|---------------------------------------------------------------------|
| **Intent**           | Construct complex objects step-by-step                          | Add responsibilities to an object dynamically                       |
| **Focus**            | Object **creation**                                             | Object **extension/behavior modification**                          |
| **Used When**        | You want to build a complex object with multiple configurations | You want to **wrap** an object with additional features             |
| **Resulting Object** | Final product (usually immutable)                               | Same object, but enhanced                                           |
| **Pattern Type**     | Creational                                                      | Structural                                                          |
| **Structure**        | Director → Builder → Product                                    | Component → ConcreteComponent → Decorator                           |
| **Flexibility**      | Flexibility in object construction                              | Flexibility in extending behavior without subclassing               |
| **Example**          | Constructing a `Burger` with bun, patty, cheese, toppings       | Wrapping a `TextBox` with a `ScrollDecorator` and `BorderDecorator` |

---
# Facade vs Proxy

#### Analogy where both might confuse:

Imagine you want to watch a movie:

- `Facade Thinking`: 
  - Instead of buying tickets, adjusting the sound system, dimming the lights, and starting the movie yourself, you just press “Play Movie” on a home theater remote. 
  - The facade hides the complexity of all subsystems and gives you a simplified interface.

- `Proxy Thinking`: 
  - You could watch the movie directly, but instead, you go through a movie rental service agent. 
  - The agent controls access (e.g., checking subscription, caching, restricting bandwidth). 
  - You still get the same movie, but with an additional layer of control/optimization.

| Aspect               | **Facade Pattern**                                                          | **Proxy Pattern**                                                                               |
|----------------------|-----------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------|
| **Intent**           | Provide a **simplified interface** to a complex subsystem                   | Provide a **surrogate/placeholder** to control access to a real object. Follows same interface. |
| **Focus**            | Hiding complexity of multiple subsystems                                    | Controlling access, adding extra behavior (security, caching, lazy loading)                     |
| **Used When**        | Clients need easy access to a complex system without learning its internals | Direct access is expensive, sensitive, or needs additional control                              |
| **Resulting Object** | A high-level interface that delegates work to multiple classes              | A stand-in object that forwards requests to the real object                                     |
| **Pattern Type**     | Structural                                                                  | Structural                                                                                      |
| **Structure**        | Facade → Subsystems                                                         | Client → Proxy → RealSubject                                                                    |
| **Flexibility**      | Simplifies client usage, but doesn’t usually extend functionality           | Adds flexibility by intercepting calls and adding logic before/after delegation                 |
| **Example**          | `HomeTheaterFacade` to start movie (lights, projector, sound)               | `ImageProxy` that loads an image lazily only when needed                                        |

---

# Facade vs Adapter

#### Analogy where both might confuse

You walk into a coffee shop:

- `Facade Thinking`: 
  - You just say “Give me a Latte”. 
  - Behind the counter, the barista coordinates the grinder, espresso machine, milk steamer, and cashier. 
  - You don’t see the complexity — you just use a simple unified interface.

- `Adapter Thinking`: 
  - You brought your own coffee mug from home, but the coffee shop machines only accept their own cups. 
  - Instead of changing the machine, the shop provides a cupholder adapter so your mug fits. 
  - The adapter translates between incompatible interfaces.

| Aspect               | **Facade Pattern**                                                            | **Adapter Pattern**                                                               |
|----------------------|-------------------------------------------------------------------------------|-----------------------------------------------------------------------------------|
| **Intent**           | Provide a **simplified interface** to a complex subsystem                     | Convert the **interface of one class into another** expected by clients           |
| **Focus**            | Hiding internal complexity                                                    | Making incompatible interfaces work together                                      |
| **Used When**        | Clients shouldn’t need to know subsystem details                              | You want to reuse existing classes but their interface doesn’t match requirements |
| **Resulting Object** | A high-level interface that internally coordinates many subsystems            | A wrapper object that **translates method calls**                                 |
| **Pattern Type**     | Structural                                                                    | Structural                                                                        |
| **Structure**        | Facade → Subsystems                                                           | Client → Adapter → Adaptee                                                        |
| **Flexibility**      | Simplifies usage, but doesn’t change subsystem behavior                       | Adds flexibility by **bridging mismatched interfaces**                            |
| **Example**          | `HomeTheaterFacade` that starts movie (lights, sound, projector all together) | `SquarePegAdapter` that lets a `SquarePeg` fit into a `RoundHole`                 |


# Strategy vs Bridge

#### Analogy where both might confuse

Imagine you’re listening to music 🎶

- `Strategy Thinking`: “How do I play this song?”
  - You can switch the algorithm of playback: play normally, shuffle, repeat, or apply an equalizer.
  - You swap strategies at runtime depending on your need.

- `Bridge Thinking`: “On which device do I play this song?”
  - You separate the abstraction (MusicPlayer) from the implementation (Speakers, Bluetooth headphones, Car stereo).
  - You can change either side independently without modifying the other.

| Aspect               | **Strategy Pattern**                                                                    | **Bridge Pattern**                                                              |
|----------------------|-----------------------------------------------------------------------------------------|---------------------------------------------------------------------------------|
| **Intent**           | Define a family of algorithms, encapsulate each one, and make them interchangeable      | Decouple an abstraction from its implementation so they can vary independently  |
| **Focus**            | **Behavior selection** (which algorithm to use)                                         | **Abstraction–Implementation separation**                                       |
| **Used When**        | You need to switch between different algorithms at runtime                              | You want to combine different abstractions with different implementations       |
| **Resulting Object** | A context object delegates work to the selected strategy                                | An abstraction delegates work to the chosen implementor                         |
| **Pattern Type**     | Behavioral                                                                              | Structural                                                                      |
| **Structure**        | Context → Strategy Interface → ConcreteStrategies                                       | Abstraction → RefinedAbstraction → Implementor Interface → ConcreteImplementors |
| **Flexibility**      | High flexibility in changing **how something is done**                                  | High flexibility in changing **where / which implementation does it**           |
| **Example**          | `PaymentContext` choosing between `CreditCardStrategy`, `PayPalStrategy`, `UPIStrategy` | `RemoteControl` (Abstraction) working with `TV` or `Radio` (Implementations)    |
