// Step 1: Define the Interface
interface UserService {
    void deleteUser(String username);
}

// Step 2: Real Subject (Admin) - Implements the Actual Logic
class Admin implements UserService {
    @Override
    public void deleteUser(String username) {
        System.out.println("User '" + username + "' has been deleted.");
    }
}

// Step 3: Proxy Class (Controls Access)
class UserServiceProxy implements UserService {
    private Admin admin;
    private String currentUserRole;

    public UserServiceProxy(String role) {
        this.currentUserRole = role;
        this.admin = new Admin(); // Real admin object
    }

    @Override
    public void deleteUser(String username) {
        if ("ADMIN".equalsIgnoreCase(currentUserRole)) {
            admin.deleteUser(username);
        } else {
            System.out.println("Access Denied: Only ADMIN can delete users.");
        }
    }
}

// Step 4: Client Code
public class ProxyExample {
    public static void main(String[] args) {
        // Client with ADMIN role
        UserService adminUser = new UserServiceProxy("ADMIN");
        adminUser.deleteUser("john_doe"); // Allowed

        // Client with USER role
        UserService normalUser = new UserServiceProxy("USER");
        normalUser.deleteUser("jane_doe"); // Access Denied
    }
}

