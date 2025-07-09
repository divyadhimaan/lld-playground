package nullObject;

// Abstract User interface
interface User2 {
    String getName();
    String getEmail();
    void displayInfo();
    boolean isNull();
}

// Real User implementation
class RealUser implements User2 {
    private String name;
    private String email;

    public RealUser(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }

    public void displayInfo() {
        System.out.println("User: " + name + ", Email: " + email);
    }

    public boolean isNull() { return false; }
}

// Null Object implementation
class NullUser implements User2 {
    public String getName() { return "Guest"; }
    public String getEmail() { return ""; }

    public void displayInfo() {
        System.out.println("Guest user - no information available");
    }

    public boolean isNull() { return true; }
}

// Service returns null object instead of null
class UserService2 {
    private static final User2 NULL_USER = new NullUser();

    public User2 findUserById(int id) {
        if (id == 1) {
            return new RealUser("John Doe", "john@example.com");
        }
        return NULL_USER; // Returns null object instead of null
    }
}

// Client code - no null checks needed
public class NullObjectSample {
    public static void main(String[] args) {
        UserService2 userService = new UserService2();

        // No null checks needed - objects always valid
        User2 user1 = userService.findUserById(1);
        user1.displayInfo();
        System.out.println("Welcome " + user1.getName());

        User2 user2 = userService.findUserById(999);
        user2.displayInfo();
        System.out.println("Welcome " + user2.getName());

        // Clean method calls
        processUser(userService.findUserById(1));
        processUser(userService.findUserById(999));

        // Optional: Check if object is null when needed
        if (user2.isNull()) {
            System.out.println("User not found, showing default behavior");
        }
    }

    static void processUser(User2 user) {
        // No null checks needed - safe to call methods
        System.out.println("Processing user: " + user.getName());
        System.out.println("Sending email to: " + user.getEmail());
        user.displayInfo();
    }
}