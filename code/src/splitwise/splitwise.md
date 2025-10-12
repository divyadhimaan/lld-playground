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