# ```L``` - Liskov Substitution Principle (LSP)

> If S is a subtype of T, then objects of type T in a program may be replaced with objects of type S without altering any of the desirable properties of that program.

**Goal** - This principle aims to enforce consistency so that the parent Class or its child Class can be used in the same way without any errors.

- Subclass should extend the properties of parent class, not narrow it down.

![Liskov Substitution Principle.png](../../images/liskov-substitution.png)
  


# Code Sample with Explanation
## Without Liskov Substitution Principle [LSP]
```mermaid
classDiagram

class Bird {
    <<abstract>>
    +fly(): void
}

class Sparrow {
    +fly(): void
}
Bird <|-- Sparrow

class Penguin {
    +fly(): void
}
Bird <|-- Penguin

class LiskovSubstitutionViolation {
    +main(String[]): void
}

LiskovSubstitutionViolation --> Bird

```
- The Penguin class extends Bird but does not support fly(), violating LSP.
- A better approach would be segregating the hierarchy.
- [Code Violating LSP](../../code/solidPrinciples/LiskovSubstitution/LiskovSubstitutionViolation.java)
## With Liskov Substitution Principle [LSP]
```mermaid
classDiagram

class Bird2 {
    <<abstract>>
}

class FlyingBird2 {
    <<abstract>>
    +fly(): void
}
Bird2 <|-- FlyingBird2

class NonFlyingBird2 {
    <<abstract>>
}
Bird2 <|-- NonFlyingBird2

class Sparrow2 {
    +fly(): void
}
FlyingBird2 <|-- Sparrow2

class Penguin2
NonFlyingBird2 <|-- Penguin2

class LiskovSubstitutionFixed {
    +main(String[]): void
}

LiskovSubstitutionFixed --> FlyingBird2
LiskovSubstitutionFixed --> NonFlyingBird2

```
Fixed version: [Refer](../../code/solidPrinciples/LiskovSubstitution/LiskovSubstitutionFixed.java)