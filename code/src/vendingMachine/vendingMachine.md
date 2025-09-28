# Vending Machine

## Design Patterns Used

| Pattern       | Purpose / Need                         |
|---------------|----------------------------------------|
| **Facade**    | Simplifies usage for client            |
| **State**     | Manages machine conditions cleanly     |
| **Strategy**  | Flexible payment handling              |
| **Singleton** | One consistent inventory across system |
| **Observer**  | Decoupled stock/collection monitoring  |
| **Factory**   | Centralized product creation           |
### 1. Facade Pattern

**Where:** `VendingMachine` (MyVendingMachine)

**Need:**

* Provides a **simple interface** for clients (`selectProduct()`, `insertMoney()`, etc.).
* Hides internal complexity (controller, inventory, payments, states).
* Keeps client usage clean and straightforward.

---

### 2. State Pattern

**Where:** `MachineState` and its implementations (`IdleState`, `WaitingForMoneyState`, `DispensingProductState`, `OutOfStockState`, `InsufficientFundsState`)

**Need:**

* Machine behavior **changes based on state** (idle, waiting for money, dispensing, etc.).
* Removes long `if-else` / `switch` logic.
* Each state defines its **own valid actions and transitions**.

---

### 3. Strategy Pattern

**Where:** `PaymentStrategy`, `CoinPaymentStrategy`, `NotePaymentStrategy`, `PaymentService`

**Need:**

* Different logic for different payment methods (coin vs note).
* Enables **easily plugging in new strategies** (e.g., CardPayment, UPI).
* Makes `PaymentService` flexible and **open for extension**.

---

### 4. Singleton Pattern

**Where:** `VendingInventory`

**Need:**

* Only one **shared inventory** should exist.
* Ensures **global access** to stock data.
* Prevents inconsistencies in stock management.
* Thread-safety with `synchronized` methods ensures **safe concurrent access**.

---

### 5. Observer Pattern

**Where:** `VendingInventory` (subject) → `RestockService`, `MoneyCollectionService` (observers)

**Need:**

* Automatic **notifications** when stock is low or collection is required.
* Decouples inventory from dependent services.
* Supports adding new observers (e.g., analytics, logging, alerts) without changing core logic.

---

### 6. Factory Pattern

**Where:** `ProductFactory`

**Need:**

* Provides a **centralized way** to create products.
* Simplifies adding new product types.
* Keeps creation logic separate from business logic.

---




## Flow Diagram

```mermaid
classDiagram

%% === Product ===
    class Product {
        - name : String
        - price : double
        - quantity : int
    }

%% === Product Factory ===
  class ProductFactory {
    + createProduct(name : String) : Product
  }

  ProductFactory --> Product


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
    class VendingInventory {
        <<Singleton>>
        - productsAvailable : Map<String, Product>
        + addProduct(name: String, qty: int)
        + getProduct(name: String) : Product
        + checkAvailability(name: String) : boolean
        + updateProductStock(name: String)
        + dispense(name: String) : boolean <<synchronized>>
        + addObserver(obs : Observer)
        + removeObserver(obs : Observer)
        + notifyObservers()
    }

  VendingInventory --> ProductFactory : "uses"
  VendingInventory --> Product : "manages"
  VendingInventory --> Observer : "notifies"

%% === Controller (Internal) ===
    class Controller {
        - inventory : VendingInventory
        - paymentService : PaymentService
        - MachineState state
        - Product selectedProduct
        + selectProduct(name : String)
        + handlePayment(amount : double)
        + dispenseProduct(name : String)
        + collectMoney()
    }

    Controller --> VendingInventory
    Controller --> PaymentService
    Controller --> MachineState

%% === Vending Machine (Facade) ===
    class VendingMachine {
        <<Facade>>
        - controller : Controller
        + insertMoney(amount : double)
        + selectProduct(type : String)
        + dispenseProduct()
        + showOptions()
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

  VendingInventory ..|> Observer : "subject"
    RestockService ..|> Observer
    MoneyCollectionService ..|> Observer

%% === State Pattern ===
    class MachineState {
        <<interface>>
        + insertMoney(amount : double)
        + selectProduct(type : String)
        + dispenseProduct()
    }

    class WaitingForMoney
    class DispensingProduct
    class OutOfStockState
    class InsufficientFundsState
    class IdleState

  class RestockService {
    +update(Product)
  }

  MachineState <|.. WaitingForMoney
  MachineState <|.. DispensingProduct
  MachineState <|.. OutOfStockState
  MachineState <|.. InsufficientFundsState
  MachineState <|.. IdleState
  VendingInventory --> RestockService : notifies

```

## Actors & Responsibilities

### 1. `MyVendingMachine` (Facade)

* Entry point for the client.
* Provides simple methods:

  * `selectProduct()`
  * `insertMoney()`
  * `dispenseProduct()`
  * `showOptions()`
* Internally delegates requests to the **VendingMachineController**.

---

### 2. `VendingMachineController` (Core Orchestrator)

* Coordinates between **inventory**, **payment service**, and **state machine**.
* Maintains the current state (`Idle`, `WaitingForMoney`, etc.) and current product selection.
* Routes calls:

  * `selectProduct()` → handled by current state
  * `handlePayment()` → forwards to current state
  * `dispenseProduct()` → handled by current state
* Responsible for giving change (`collectMoney()`).

---

### 3. `MachineState` & Implementations (State Pattern)

Represents different machine conditions:

* **IdleState** → waiting for user to pick a product.
* **WaitingForMoneyState** → product selected, waiting for payment.
* **DispensingProductState** → dispensing product + returning change.
* **OutOfStockState** → selected product not available.
* **InsufficientFundsState** → user inserted less money, needs to add more.

Each state defines allowed operations and restricts invalid actions.

---

### 4. `VendingInventory` (Singleton + Subject)

* Manages stock of products.
* Provides methods to check availability, dispense, and update stock.
* Notifies observers (e.g., `RestockService`) when product stock changes.

---

### 5. `Product` & `ProductFactory`

* **Product**: Represents an item (name, price, quantity).
* **ProductFactory**: Creates predefined products (`coke`, `pepsi`, `water`).

---

### 6. `VendingPaymentService` (Payment Handler)

* Handles received money, tracks balance, and calculates change.
* Uses **PaymentStrategy** to handle mode-specific logic (coin/note).

---

### 7. `PaymentStrategy` & Implementations (Strategy Pattern)

* **CoinPaymentStrategy** → handles payment in coins.
* **NotePaymentStrategy** → handles payment in notes.

---

### 8. `Observer` Pattern (Restock Service)

* `RestockService` observes inventory stock changes.
* Prints a notification and alerts if restock is needed.

---

## Flow (High-Level)

1. **User selects product** → `IdleState` validates availability.
2. Machine enters `WaitingForMoneyState`.
3. **User inserts money** → payment processed via `VendingPaymentService` and appropriate strategy.
4. If enough → moves to `DispensingProductState` → product dispensed + change returned → state resets to `IdleState`.
5. If insufficient → `InsufficientFundsState` prompts for more money.
6. If product is out of stock → `OutOfStockState`.



## Changes to ensure thread safety and Concurrency
### 1. VendingMachineController

- Original: VendingMachineController.currentSelection was a single shared field.

- Change: Made it ThreadLocal<Product>, so each user/thread can select a product independently without collisions.

- ```private ThreadLocal<Product> currentSelection;```

### 2. Per-thread payment tracking

- Original: VendingPaymentService.amountReceived was shared across threads → race conditions.

- Change: Made amountReceived ThreadLocal or used ThreadLocal PaymentService per thread.

- Ensures each transaction maintains its own payment state.

- ```private ThreadLocal<Double> amountReceived = ThreadLocal.withInitial(() -> 0.0);```

### 3. Synchronized inventory operations

- Original: Inventory methods could be accessed concurrently → stock updates could collide.

- Change: Added synchronized to critical inventory methods:

  - addProduct()
  - getProduct()
  - dispense()
  - updateProductStock()

- ```public synchronized void dispense(String productName) { ... }```

- Ensures that stock decrement, availability check, and notification happen atomically.