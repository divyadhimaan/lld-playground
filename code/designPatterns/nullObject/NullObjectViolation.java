package nullObject;

// User class
class User {
    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public void displayInfo() {
        System.out.println("User: " + name + ", Email: " + email);
    }
}

// Service that might return null
class UserService {
    public User findUserById(int id) {
        if (id == 1) {
            return new User("John Doe", "john@example.com");
        }
        return null; // Returns null for non-existent users
    }
}

// Client code with null checks everywhere
public class NullObjectViolation {
    public static void main(String[] args) {
        UserService userService = new UserService();

        // Need null checks everywhere
        User user1 = userService.findUserById(1);
        if (user1 != null) {
            user1.displayInfo();
            System.out.println("Welcome " + user1.getName());
        } else {
            System.out.println("User not found");
        }

        User user2 = userService.findUserById(999);
        if (user2 != null) {
            user2.displayInfo();
            System.out.println("Welcome " + user2.getName());
        } else {
            System.out.println("User not found");
        }

        // More complex scenarios
        processUser(userService.findUserById(1));
        processUser(userService.findUserById(999));
    }

    static void processUser(User user) {
        if (user != null) {
            if (user.getName() != null) {
                System.out.println("Processing user: " + user.getName());
            }
            if (user.getEmail() != null) {
                System.out.println("Sending email to: " + user.getEmail());
            }
        } else {
            System.out.println("Cannot process null user");
        }
    }
}