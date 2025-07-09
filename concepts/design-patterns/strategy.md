# Strategy: Design Pattern

>  A behavioral design pattern that lets you define a family of algorithms, put each of them into a separate class, and make their objects interchangeable.
> 
>  It lets the algorithm vary independently from the clients that use it.


## When to use Strategy Pattern
- When you have multiple ways to perform a task and want to choose the method at runtime
- When you want to avoid long if-else or switch statements for different behaviors
- When you need to add new algorithms without modifying existing code
- When different classes differ only in their behavior

## Real world analogy
Consider navigation apps like Google Maps. 
You can choose different travel strategies: driving (fastest route), walking (pedestrian paths), cycling (bike lanes), or public transit (bus/train routes). 
The destination remains the same, but the strategy to reach it changes based on your selection.


![strategy-design.png](../../images/strategy-design.png)

## Problem Solved

- Eliminates complex conditional statements
- Makes it easy to add new algorithms without changing existing code
- Allows runtime selection of algorithms
- Promotes code reusability and separation of concerns
- Reduces code duplication when multiple classes have similar behaviors



## Summary

- You create a `Context class`, that contains `(has-a)` instance of `Strategy interface`.
- The `Strategy interface` is common to all concrete strategies. It declares a method the context uses to execute a strategy.
- Using this strategy interface implement `(is-a)` all concrete classes(strategies).
- The `context` calls the execution method on the linked strategy object each time it needs to run the algorithm. It doesn’t know what type of strategy it works with or how the algorithm is executed.
- The `Client` creates a specific strategy `object` and passes it to the `context`. The context exposes a setter which lets clients replace the strategy associated with the context at runtime.

## Violation Code

[Payment Strategy Violation Code](../../code/designPatterns/strategy/StrategyViolation.java)

```mermaid
classDiagram

class ShoppingCart2 {
    -paymentMethod: String
    +setPaymentMethod(paymentMethod: String): void
    +checkout(amount: int): void
}

class StrategyViolation {
    +main(args: String[]): void
}

StrategyViolation --> ShoppingCart2

```

### Issues with above code
1. Violates Open/Closed Principle - Adding new payment methods requires modifying existing code
2. All payment logic is tightly coupled in one class
3. Conditional complexity increases with each new payment method
4. Hard to test individual payment methods in isolation
5. No polymorphism - uses string-based switching instead
6. Prone to runtime errors with invalid payment method strings

## Enhanced Code 

[Payment Strategy Example](../../code/designPatterns/strategy/StrategySample.java)
```mermaid
classDiagram

class PaymentStrategy {
    <<interface>>
    +pay(amount: int): void
}

class CreditCardPayment {
    +pay(amount: int): void
}
PaymentStrategy <|.. CreditCardPayment

class PayPalPayment {
    +pay(amount: int): void
}
PaymentStrategy <|.. PayPalPayment

class BankTransferPayment {
    +pay(amount: int): void
}
PaymentStrategy <|.. BankTransferPayment

class ShoppingCart {
    -paymentStrategy: PaymentStrategy
    +setPaymentStrategy(ps: PaymentStrategy): void
    +checkout(amount: int): void
}

class StrategySample {
    +main(args: String[]): void
}

ShoppingCart --> PaymentStrategy
StrategySample --> ShoppingCart

```


## Common LLD Problems Using Strategy Pattern:

### 1. Payment Gateway Integration
- **Strategies:** `CreditCardPayment`, `UPIPayment`, `NetBankingPayment`, `WalletPayment`
- **Context:** User selects a payment method dynamically.

---

### 2. Navigation / Map Routing System
- **Strategies:** `ShortestPathStrategy`, `ScenicRouteStrategy`, `TollFreeRouteStrategy`
- **Context:** Based on user’s route preference.

---

### 3. Compression Tool / File Zipper
- **Strategies:** `ZipCompression`, `RarCompression`, `TarCompression`
- **Context:** Chosen depending on file format or user input.

---

### 4. Tax Calculation System
- **Strategies:** `IndiaTaxStrategy`, `USATaxStrategy`, `UKTaxStrategy`
- **Context:** Ecommerce platforms dealing with international markets.

---

### 5. Sorting Algorithms
- **Strategies:** `QuickSort`, `MergeSort`, `HeapSort`
- **Context:** Selected dynamically based on data characteristics.

---

### 6. Discount / Promotion Engine
- **Strategies:** `FlatDiscount`, `PercentageDiscount`, `BuyOneGetOneFree`
- **Context:** Applied at checkout based on business rules.

---

### 7. Logging System
- **Strategies:** `ConsoleLogger`, `FileLogger`, `DatabaseLogger`, `CloudLogger`
- **Context:** Environment-based or pluggable logging backends.

---

### 8. Recommendation Engine
- **Strategies:** `CollaborativeFiltering`, `ContentBased`, `TrendingBased`, `HybridRecommendation`
- **Context:** Selectable or combined to serve better recommendations.

---



| References | Links                                                                                            |
|------------|--------------------------------------------------------------------------------------------------|
| Article Reference | [Refactoring Guru](https://refactoring.guru/design-patterns/strategy) |
| Boiler Plate Code | [Strategy Example](../../code/designPatterns/strategy/StrategyExample.java)                      |

