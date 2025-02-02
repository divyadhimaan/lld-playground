# Strategy: Design Pattern

>A behavioral design pattern that lets you define a family of algorithms, put each of them into a separate class, and make their objects interchangeable.

![strategy-design.png](../../../images/strategy-design.png)

## Summary

- You create a `Context class`, that contains `(has-a)` instance of `Strategy interface`.
- The `Strategy interface` is common to all concrete strategies. It declares a method the context uses to execute a strategy.
- Using this strategy interface implement `(is-a)` all concrete classes(strategies).
- The `context` calls the execution method on the linked strategy object each time it needs to run the algorithm. It doesn’t know what type of strategy it works with or how the algorithm is executed.
- The `Client` creates a specific strategy `object` and passes it to the `context`. The context exposes a setter which lets clients replace the strategy associated with the context at runtime.


### Article Reference - [here](https://refactoring.guru/design-patterns/strategy)
### Java Example - [here](./../../../code/designPatterns/StrategyExample.java)

