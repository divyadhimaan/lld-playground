# Builder v/s Decorator


Analogy where both might confuse:

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

