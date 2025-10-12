# Design expense sharing app: Splitwise

## According to the requirements, here are the actors and APIs needed for the Splitwise application:

1. User Management:
   - Create User: `POST /user` 
     - Input: User details (name, email, password, etc.)
   - Update User Profile: `PUT /users/{userId}`
   - Get User Profile: `GET /users/{userId}`
   - List Expenses for User: `GET /users/{userId}/expenses`
   - Transaction History: `GET /users/{userId}/transactions`
   - List Groups for User: `GET /users/{userId}/groups`


2. Group Management:
    - Create Group: `POST /groups`
    - Add User to Group: `POST /groups/{groupId}/users`
    - Remove User from Group: `DELETE /groups/{groupId}/users/{userId}`
    - Get Group Details: `GET /groups/{groupId}`
    - Delete Group: `DELETE /groups/{groupId}`
    - Group Expense Summary: `GET /groups/{groupId}/summary`
   
3. Expense Management:
    - Add Expense: `POST /groups/{groupId}/expenses`
    - Get Expense Details: `GET /groups/{groupId}/expenses/{expenseId}`
    - List Expenses in Group: `GET /groups/{groupId}/expenses`
    - Update Expense: `PUT /groups/{groupId}/expenses/{expenseId}`
    - Delete Expense: `DELETE /groups/{groupId}/expenses/{expenseId}`
    - Settle Up: `POST /users/{userId}/settle/{otherUserId}`

4. Split Methods:
   - Equal Split: Handled in the Add Expense API
   - Percentage Split: Handled in the Add Expense API
   - Exact Amounts: Handled in the Add Expense API


## Class Diagram

```mermaid
classDiagram
    %% Interface Layer
    class SplitwiseInterface {
        - SplitwiseOrchestration orchestration
        + addUser(name, email, phone)
        + viewUser(userId)
        + createGroup(name)
        + addUserToGroup(userId, groupId)
        + removeUserFromGroup(userId, groupId)
        + showGroupDetails(groupId)
        + showGroupsForUser(userId)
        + addExpense(groupId, amount, desc, adderId, participants, strategy)
        + addExpense(groupId, amount, desc, adderId, participants, strategy, splitMap)
        + viewExpenseSummaryForUser(userId)
        + showTransactionHistoryForUser(userId)
        + showGroupBalanceSummary(groupId)
    }

    %% Core Orchestration
    class SplitwiseOrchestration {
        - static SplitwiseOrchestration orchestration
        - UserInventory userInventory
        - GroupInventory groupInventory
        - UserFactory userFactory
        - GroupFactory groupFactory
        - ExpenseFactory expenseFactory
        - Logger logger
        + getInstance()
        + addUser(name, email, phone)
        + viewUser(userId)
        + createGroup(name)
        + addUserToGroup(userId, groupId)
        + removeUserFromGroup(userId, groupId)
        + addExpense(...)
        + viewExpenseSummaryForUser(userId)
        + showGroupBalanceSummary(groupId)
    }

    %% Domain Layer
    class User {
        + String id
        + String name
        + String email
        + String phone
        + List~Expense~ expenses
        + Map~User,Double~ owedAmountPerUser
        + addExpense(expense)
        + updateOwedAmount(user, amount)
    }

    class Group {
        + String id
        + String name
        + Map~String,User~ members
        + Map~String,Expense~ expenses
        + addMember(user)
        + removeMember(user)
        + addExpense(exp)
        + getExpenseById(expId)
    }

    class Expense {
        + String expenseId
        + double amount
        + String description
        + User expenseAddingUser
        + Group group
        + List~User~ users
        + SplitStrategy strategy
        + calculateSplit()
        + addUserToExpense(user)
        + removeUserFromExpense(user)
        + updateAmount(amount)
    }

    %% Factories
    class UserFactory {
        + createUser(name, email, phone)
    }

    class GroupFactory {
        + createGroup(name)
    }

    class ExpenseFactory {
        + createExpense(group, amount, desc, adder, users, strategy, splitMap)
    }

    %% Strategies
    class SplitStrategy {
        <<interface>>
        + split(amount, users, expenseAddingUser)
    }

    class EqualSplit {
        + split(amount, users, expenseAddingUser)
    }

    class PercentageSplit {
        - Map~User,Double~ percentageMap
        + split(amount, users, expenseAddingUser)
    }

    class ExactAmountSplit {
        - Map~User,Double~ exactAmountMap
        + split(amount, users, expenseAddingUser)
    }

    %% Utilities
    class UserInventory {
        - Map~String,User~ userMap
        - Map~User,List~Group~~ userGroupMap
        + addUser(user)
        + getUser(id)
        + addGroupForUser(user, group)
        + getGroupsForUser(user)
    }

    class GroupInventory {
        - Map~String,Group~ groupMap
        + addGroup(group)
        + getGroup(id)
    }

    class Logger {
        + displayUserDetails(user)
        + displayGroupDetails(group)
        + displayExpense(type, expense, splitAmounts)
        + displayExpenseForUser(map, user)
        + displayGroupBalances(map, group)
    }

    %% Relationships
    SplitwiseInterface --> SplitwiseOrchestration
    SplitwiseOrchestration --> UserInventory
    SplitwiseOrchestration --> GroupInventory
    SplitwiseOrchestration --> UserFactory
    SplitwiseOrchestration --> GroupFactory
    SplitwiseOrchestration --> ExpenseFactory
    SplitwiseOrchestration --> Logger
    SplitwiseOrchestration --> Expense
    SplitwiseOrchestration --> User
    SplitwiseOrchestration --> Group
    ExpenseFactory --> SplitStrategy
    Expense --> SplitStrategy
    EqualSplit ..|> SplitStrategy
    PercentageSplit ..|> SplitStrategy
    ExactAmountSplit ..|> SplitStrategy
    Group --> Expense
    Group --> User
    User --> Expense
    UserInventory --> User
    GroupInventory --> Group
    Logger --> Expense
    Logger --> User
    Logger --> Group

```

## Design Patterns Used

| **Design Pattern**                                | **Used In / Classes**                                                                                                               | **Type**   | **Purpose**                                                                                                            | **Key Benefit**                                                            |
|---------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------|------------|------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------|
| **Singleton**                                     | `SplitwiseOrchestration`, `UserInventory`, `GroupInventory`, `Logger`                                                               | Creational | Ensures only one global instance is used throughout the system for coordination, data access, and logging.             | Centralized control and shared state consistency.                          |
| **Factory Method**                                | `UserFactory`, `GroupFactory`, `ExpenseFactory`                                                                                     | Creational | Encapsulates the object creation logic for `User`, `Group`, and `Expense` objects, hiding instantiation details.       | Reduces coupling; simplifies object creation.                              |
| **Strategy Pattern**                              | `SplitStrategy` (interface), `EqualSplit`, `PercentageSplit`, `ExactAmountSplit`                                                    | Behavioral | Allows flexible choice of splitting logic at runtime. Different strategies can be swapped without modifying `Expense`. | Promotes open/closed principle and cleaner extension for new split types.  |
| **Facade Pattern**                                | `SplitwiseInterface`                                                                                                                | Structural | Acts as the unified entry point for all client operations like adding users, creating groups, or adding expenses.      | Simplifies usage; hides orchestration and internal subsystem complexities. |
| **Observer (Lightweight Use)**                    | `User` and `Expense` (implicit relationship)                                                                                        | Behavioral | When an expense is added/updated, user owed amounts are automatically updated.                                         | Keeps user balances consistent with changes in expenses.                   |
| **Builder (Optional - for future extensibility)** | Could be applied in `ExpenseFactory` when creating complex expense objects with optional fields like description, map, or metadata. | Creational | Handles construction of complex `Expense` objects step-by-step.                                                        | Improves readability and flexibility for complex object creation.          |
