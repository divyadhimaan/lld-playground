# ```S``` - Single Responsibility Principle ( SRP)

- A ```class``` should have ```single responsibility```.
- Should not have extra/irrelevant functionalities


  **Goal** - This principle aims to separate behaviours so that if bugs arise as a result of your change, it won’t affect other unrelated behaviours.

  ![single-responsibility.png](../../images/single-responsibility.png)
  


## Principle Violation

```java
class User {
    private String name;
    private String email;

    public User(String name, String email){
        this.name = name;
        this.email = email;
    }

    // Business logic + persistence + notification — violating SRP
    public void saveUserToDatabase() {
        System.out.println("Saving user " + name + " to database");
    }

    public void sendEmail() {
        System.out.println("Sending email to " + email);
    }
}

```
### Problem:
- User class has multiple responsibilities: user data, database persistence, and email notifications.
- Changes in email or database logic would require modifying the User class, violating SRP.

## Following SRP

```java
class User {
    private String name;
    private String email;

    public User(String name, String email){
        this.name = name;
        this.email = email;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
}

// Handles persistence
class UserRepository {
    public void save(User user) {
        System.out.println("Saving user " + user.getName() + " to database");
    }
}

// Handles email notifications
class EmailService {
    public void sendEmail(User user) {
        System.out.println("Sending email to " + user.getEmail());
    }
}

```

### Benefits
- Each class now has a single responsibility.
- Changes in email logic don’t affect persistence or user data class.
- Easier to maintain and test.