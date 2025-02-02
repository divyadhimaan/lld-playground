# Composition Vs Aggregation

`Composition`, in the context of object-oriented programming and design patterns, is a strong `has-a` relationship between two classes.  It signifies that one class (the "owning" or "containing" class) contains an instance of another class (the "owned" or "contained" class) as a part of itself, and the lifetimes of the two are tightly coupled.  If the owning class is destroyed, the owned class instances that are part of it are also typically destroyed or become unusable.

Here's a breakdown of the key characteristics of composition:

Strong Ownership: The owning class has exclusive ownership of the owned class instance. The owned class is not typically shared with other objects.
Dependent Lifecycles: The owned object's lifecycle is bound to the owning object's lifecycle. When the owning object is destroyed, the owned object is also destroyed. This is the most critical characteristic that distinguishes composition from aggregation.
Part-Whole Relationship: The owned object is a part of the owning object, and it doesn't make sense for the part to exist independently of the whole in most contexts.
Implementation Detail: Composition is often used to implement the functionality of the owning class. The owned class is an internal component, and its existence is usually hidden from the outside world.
Example:

Think of a Car and its Engine.  A car has-a engine.  In this case, it's a composition relationship because:

The car owns the engine (in most cases, the engine isn't shared with other cars).
If the car is scrapped or destroyed, the engine is likely also destroyed or at least becomes unusable as a car engine without significant rework.
The engine is an integral part of the car. A car without an engine isn't really a car.
UML Notation:

In UML (Unified Modeling Language) diagrams, composition is represented by a solid line connecting the two classes, with a filled diamond at the owning class end.

      Car
      /|\
       |
    Engine  (Filled diamond at the Car end)
Contrast with Aggregation:

Composition is often confused with aggregation.  The key difference lies in the dependency of the owned object's lifecycle.  In aggregation (a weaker "has-a" relationship), the owned object can exist independently of the owning object.

Example of Aggregation:

A Department and Professor.  A department has-a professor. This is aggregation because if the department is dissolved, the professors still exist and can work in other departments.  The professor's existence is independent of the department's existence.  In UML, aggregation is represented with an unfilled diamond.

In summary:  Composition emphasizes a strong, dependent relationship where the owned object is an essential part of the owning object and its lifecycle is controlled by the owning object.  It's a crucial concept for designing robust and well-structured object-oriented systems.