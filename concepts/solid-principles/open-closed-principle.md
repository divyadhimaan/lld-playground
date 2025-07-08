# ```O``` - Open Closed Principle (OCP)

> Class should be Open for Extension but closed for Modification


- Software entities should be open for extension, but closed for modifications
- Make minimal changes to existing code when introducing new functionality, should not modify already tested code.

![open-close.png](../../images/open-closed.png)
  



## Without Open Closed Principle (OCP)
Checkout the existing code for calculating tax on an order in a shopping cart application.
- [Existing Code](../../code/solidPrinciples/OpenClosed/OpenClosedViolation.java)
```mermaid
classDiagram

class TaxCalculator {
<<interface>>
+calculateTax(double): double
}

class OrderItem {
-name: String
-price: double
-quantity: int
+OrderItem(name: String, price: double, quantity: int)
+getPrice(): double
+getQuantity(): int
+toString(): String
}

class Order {
-orderId: String
-customerEmail: String
-items: List~OrderItem~
+Order(orderId: String, customerEmail: String)
+addItem(item: OrderItem): void
+getItems(): List~OrderItem~
+processOrder(taxCalculator: TaxCalculator): double
}

class OrderCalculatorOCP {
-TAX_RATE: double
+calculateTotal(order: Order): double
}

class OpenClosedViolation {
+main(String[]): void
}

Order --> "1..*" OrderItem
OpenClosedViolation --> Order
OpenClosedViolation --> OrderCalculatorOCP
Order --> TaxCalculator

```
> Feature Request: We need a different tax calculation (e.g., GST, VAT, regional tax rates)

According to the Open Closed Principle, we should not modify the existing code (OpenClosedViolation.java) to add new tax calculation logic. Instead, we will create new implementations of TaxCalculator.
## With Open Closed Principle (OCP)

- [OCP following code](../../code/solidPrinciples/OpenClosed/OpenClosedFixed.java)
```mermaid
classDiagram

class TaxCalculator2 {
    <<interface>>
    +calculateTax(double): double
}

class ReducedTaxCalculator2 {
    +calculateTax(double): double
}
TaxCalculator2 <|.. ReducedTaxCalculator2

class StandardTaxCalculator2 {
    +calculateTax(double): double
}
TaxCalculator2 <|.. StandardTaxCalculator2

class OrderItem2 {
    -name: String
    -price: double
    -quantity: int
    +OrderItem2(name, price, quantity)
    +getPrice(): double
    +getQuantity(): int
    +toString(): String
}

class Order2 {
    -orderId: String
    -customerEmail: String
    -items: List~OrderItem2~
    +Order2(orderId: String, customerEmail: String)
    +addItem(item: OrderItem2): void
    +getItems(): List~OrderItem2~
    +processOrder(taxCalculator: TaxCalculator2): double
}

class OrderCalculatorOCP2 {
    -TAX_RATE: double
    +calculateTotal(order: Order2): double
}

class OpenClosedFixed {
    +main(String[]): void
}

Order2 --> "1..*" OrderItem2
Order2 --> TaxCalculator2
OpenClosedFixed --> Order2
OpenClosedFixed --> OrderCalculatorOCP2
OpenClosedFixed --> ReducedTaxCalculator2
OpenClosedFixed --> StandardTaxCalculator2

```
1. **Interface-Based Design** (TaxCalculator)
    - The TaxCalculator interface defines a contract for tax calculation.
    - The StandardTaxCalculator implements this interface with a 15% tax rate. 
2. Extending Functionality Without Modification
   - The Order class depends on the TaxCalculator abstraction rather than a concrete implementation.
   - If we want to apply a different tax strategy (e.g., ReducedTaxCalculator or NoTaxCalculator), we simply create new classes implementing TaxCalculator instead of modifying Order.
   


   - **Example of Extension**: 
   Suppose a new tax rule requires a reduced tax rate for certain products. We can create a new class:
      ```java
      class ReducedTaxCalculator implements TaxCalculator {
          private static final double REDUCED_TAX_RATE = 0.05;
          
          public double calculateTax(double amount) {
          return amount * REDUCED_TAX_RATE;
          }
      }
                
      ```
        
   - Now, in Main, we can use:
      ```java
      TaxCalculator taxCalculator = new ReducedTaxCalculator();
     ```
      No changes are made to the Order class, making the system open for extension but closed for modification.
