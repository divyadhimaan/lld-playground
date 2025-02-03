# ```I``` - Interface Segmented Principle (ISP)

> Interfaces should be such, that Clients should not be forced to depend on methods that they do not use.

**Goal** - This principle aims at splitting a set of actions into smaller sets so that a Class executes ONLY the set of actions it requires.

![interface segregation principle.png](../../images/interface-segmented.png)
  


# Code Sample with Explanation

- Violation of ISP [Refer](../../code/solidPrinciples/InterfaceSegmentation/InterfaceSegmentationViolation.java):
    - The `Waiter` class is forced to implement `washDishes()` and `cookFood()`, which are not relevant.
    - This leads to unused methods or runtime exceptions.
- Solution [Refer](../../code/solidPrinciples/InterfaceSegmentation/InterfaceSegmentationFixed.java):
  - Split the large interface into smaller, more specific interfaces (`DishWasher`, `Server`, `Chef` which extend the `RestaurantEmployee`). 
  - Avoids Unnecessary Implementations – Each class only implements methods relevant to its role.
  - Enhances Code Maintainability – Adding a new role won't affect unrelated classes.
  - Better Extensibility – We can easily introduce new roles without modifying existing code.

