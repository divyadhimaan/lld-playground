# Vending Machine


## Actors (Initial Thoughts)

- Vending Machine (facade)
  - has-a controller

- Product 
  - Represents different items with prices & quantities. 

- Payment 
  - Uses ENUM for denominations (coins, notes). 
  - PaymentService with two different Strategies (coins, notes). 
  - Handles payment validation + returns change.

- Controller 
  - Orchestrates product dispensing. 
  - Calls Inventory update after successful transaction.
  - has-a inventory
  - has-a state
  - has-a payment strategy

- Inventory 
  - Maintains Product → Quantity mapping.
  - has-a (many) product
  - Must be thread-safe (handles concurrent transactions).
  - Is a subject, Whenever products are added (restock) or money is collected, the Inventory notifies observers.

- Concurrency
  - Synchronization around critical sections (inventory updates, cash handling). 
  - Restocking & Money Collection
  
- Provided via external Interfaces. 
  - Uses Observer Pattern for event-driven restock/collection.

- State Handling 
  - Machine phases represented with State Pattern:
    - Waiting for Money
    - Dispensing Product
    - Exception states (Out of Stock, Insufficient Funds, etc.)

## Rough Flow

```mermaid
classDiagram

%% === Product ===
    class Product {
        - name : String
        - price : double
        - quantity : int
    }

%% === Payment & Strategies ===
    class Denomination {
        <<enum>>
        COIN
        NOTE
    }

    class PaymentStrategy {
        <<interface>>
        + validatePayment(amount : double) : boolean
        + getChange(amount : double) : double
    }

    class CoinPaymentStrategy {
        + validatePayment(amount : double) : boolean
        + getChange(amount : double) : double
    }

    class NotePaymentStrategy {
        + validatePayment(amount : double) : boolean
        + getChange(amount : double) : double
    }

    PaymentStrategy <|.. CoinPaymentStrategy
    PaymentStrategy <|.. NotePaymentStrategy

    class PaymentService {
        - strategy : PaymentStrategy
        + setStrategy(PaymentStrategy)
        + processPayment(amount : double) : boolean
        + returnChange(amount : double) : double
    }

    PaymentService --> PaymentStrategy
    PaymentService --> Denomination

%% === Inventory (Singleton + Thread Safe) ===
    class Inventory {
        <<Singleton>>
        - productStock : Map<Product, Integer>
        - observers : List<Observer>
        + addProduct(p: Product, qty: int)
        + updateStock(p: Product, qty: int)
        + checkAvailability(p: Product) : boolean
        + dispense(p: Product) : boolean <<synchronized>>
        + addObserver(obs : Observer)
        + removeObserver(obs : Observer)
        + notifyObservers()
    }

    Inventory --> Product : "has many"
    Inventory --> Observer : "notifies"

%% === Controller (Internal) ===
    class Controller {
        - inventory : Inventory
        - paymentService : PaymentService
        - state : State
        + selectProduct(type : String)
        + handlePayment(amount : double)
        + dispenseProduct(p: Product)
        + restockProduct(p: Product, qty: int)
        + collectMoney()
    }

    Controller --> Inventory
    Controller --> PaymentService
    Controller --> State

%% === Vending Machine (Facade) ===
    class VendingMachine {
        <<Facade>>
        - controller : Controller
        + insertMoney(amount : double)
        + selectProduct(type : String)
        + dispenseProduct()
        + restock(p: Product, qty: int)
        + collectMoney()
    }

    VendingMachine --> Controller : "uses internally"

%% === Observer Pattern for Restock & Collection ===
    class Observer {
        <<interface>>
        + update()
    }

    class RestockService {
        + update()
    }

    class MoneyCollectionService {
        + update()
    }

    Inventory ..|> Observer : "subject"
    RestockService ..|> Observer
    MoneyCollectionService ..|> Observer

%% === State Pattern ===
    class State {
        <<interface>>
        + insertMoney(amount : double)
        + selectProduct(type : String)
        + dispenseProduct()
    }

    class WaitingForMoney
    class DispensingProduct
    class OutOfStockState
    class InsufficientFundsState

    State <|.. WaitingForMoney
    State <|.. DispensingProduct
    State <|.. OutOfStockState
    State <|.. InsufficientFundsState

```