# Observer: Design Pattern

> A behavioral design pattern that lets you define a subscription mechanism to notify multiple objects about any events that happen to the object they’re observing.



![img.png](../../../images/observer-design.png)

## Summary

- The `Publisher` issues events of interest to other objects. These events occur when the publisher changes its state or executes some behaviors. Publishers contain a subscription infrastructure that lets new subscribers join and current subscribers leave the list.
- When a new event happens, the publisher goes over the subscription list and calls the notification method declared in the subscriber interface on each subscriber object.
- The `Subscriber interface` declares the notification interface. In most cases, it consists of a single update method. The method may have several parameters that let the publisher pass some event details along with the update.
- `Concrete Subscribers` perform some actions in response to notifications issued by the publisher. All of these classes must implement the same interface so the publisher isn’t coupled to concrete classes.
- Usually, subscribers need some contextual information to handle the update correctly. For this reason, publishers often pass some context data as arguments of the notification method. The publisher can pass itself as an argument, letting subscriber fetch any required data directly.
- The Client creates publisher and subscriber objects separately and then registers subscribers for publisher updates.



### Article Reference - [here](https://refactoring.guru/design-patterns/strategy)
### Java Example - [here](./../../../code/designPatterns/ObserverExample.java)


![img.png](../../../images/observer-design-2.png)


