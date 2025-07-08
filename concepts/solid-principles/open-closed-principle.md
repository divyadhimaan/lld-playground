# ```O``` - Open Closed Principle (OCP)

> Class should be Open for Extension but closed for Modification


- Software entities should be open for extension, but closed for modifications
- Make minimal changes to existing code when introducing new functionality, should not modify already tested code.

![open-close.png](../../images/open-closed.png)
  


# Code Sample with Explanation

Code Samples:
- [Existing Code](../../code/solidPrinciples/OpenClosed/OpenClosedViolation.java)
> Feature Request: We need a different tax calculation (e.g., GST, VAT, regional tax rates)

According to the Open Closed Principle, we should not modify the existing code (OpenClosedViolation.java) to add new tax calculation logic. Instead, we will create new implementations of TaxCalculator.
- [OCP following code](../../code/solidPrinciples/OpenClosed/OpenClosedFixed.java)

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
