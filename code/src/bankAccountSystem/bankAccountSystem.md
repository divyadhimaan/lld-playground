# Solution: Bank Account System

✔ Scalable → Easy to add new account types.

✔ Maintainable → Well-structured with separation of concerns.

✔ Secure → Transactions must go through BankService.

✔ Efficient → Singleton ensures single control over transactions.



## Components:

## `IBankAccount` (Interface)
- Defines a contract for bank accounts, ensuring different account types
- Follows `Interface Segregation (ISP)`
- Methods:
  - `getAccountNumber()`
  - `getOwner()`
  - `getBalance()`
  - `getTransactionHistory()`
  
## `BankAccount` (Abstract)
- manages account details and balance operations. 
- Abstract class, allowing easy extension for new account types
- Follows `Encapsulation`, `OCP`, `LSP`
- Supports Encapsulation & Data Hiding: `balance` is private, ensuring transactions are only handled via BankService.
- Members:
  - accountNumber: String
  - owner: String
  - balance: double
  - List<Transaction> transactionHistory
- Methods
  - `depositInternal()`
  - `withdrawInternal()`
  - `getBalance()`
  - `getOwner()`
  - `getAccountNumber()`
  - `getTransactionHistory()`

## `SavingBankAccount` (Extends BankAccount)
- Follows `OCP`, `LSP`
- `applyInterest()`: Applies interest to the balance.

## `CheckingBankAccount` (Extends BankAccount)
- `canWithdraw()`: Checks if the withdrawal is allowed based on account rules.

## `Transaction`
- Encapsulates details of a financial transaction.
- Members
  - type: indicates transaction type (Deposit, Withdrawal, Transfer).
  - amount
  - timestamp
- Methods
  - toString(): Returns a formatted transaction summary.

## `BankService` 
- responsible for transaction operations (deposit, withdraw, transfer).
- follows `Singleton` Design Pattern
- methods: 
  - `deposit()`: Deposits money into the specified account.
  - `withdraw()`: Withdraws money if sufficient balance is available.
  - `transfer()`: Transfers funds between two accounts securely.
