package proxy;

// Subject interface
interface Database {
    void query(String sql);
}

// Real Subject
class RealDatabase implements Database {
    private String connectionString;

    public RealDatabase(String connectionString) {
        this.connectionString = connectionString;
        connectToDatabase();
    }

    private void connectToDatabase() {
        System.out.println("Connecting to database: " + connectionString);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Database connection established");
    }

    public void query(String sql) {
        System.out.println("Executing query: " + sql);
    }
}

// Proxy
class DatabaseProxy implements Database {
    private RealDatabase realDatabase;
    private String connectionString;
    private String userRole;

    public DatabaseProxy(String connectionString, String userRole) {
        this.connectionString = connectionString;
        this.userRole = userRole;
        // No immediate connection - lazy loading
    }

    public void query(String sql) {
        // Access control
        if (!hasPermission(sql)) {
            System.out.println("Access denied: Insufficient permissions for user role: " + userRole);
            return;
        }

        // Lazy loading - create connection only when needed
        if (realDatabase == null) {
            realDatabase = new RealDatabase(connectionString);
        }

        // Logging
        System.out.println("Logging: Query request from user role: " + userRole);

        // Delegate to real object
        realDatabase.query(sql);

        // Additional logging
        System.out.println("Logging: Query completed successfully");
    }

    private boolean hasPermission(String sql) {
        if (userRole.equals("admin")) {
            return true;
        }
        if (userRole.equals("user") && sql.toLowerCase().startsWith("select")) {
            return true;
        }
        return false;
    }
}

// Usage
public class ProxySample {
    public static void main(String[] args) {
        System.out.println("=== Admin User ===");
        Database adminDb = new DatabaseProxy("jdbc:mysql://localhost:3306/mydb", "admin");
        adminDb.query("SELECT * FROM users");
        adminDb.query("DELETE FROM users WHERE id = 1");

        System.out.println("\n=== Regular User ===");
        Database userDb = new DatabaseProxy("jdbc:mysql://localhost:3306/mydb", "user");
        userDb.query("SELECT * FROM products");
        userDb.query("DELETE FROM products WHERE id = 1");

        // Benefits:
        // 1. Lazy loading - connections created only when needed
        // 2. Access control - permissions checked before execution
        // 3. Logging and monitoring capabilities
        // 4. Transparent to client - same interface as real object
    }
}