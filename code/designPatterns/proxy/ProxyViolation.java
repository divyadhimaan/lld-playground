package proxy;


// Direct access to expensive database operations
class DatabaseService {
    private String connectionString;

    public DatabaseService(String connectionString) {
        this.connectionString = connectionString;
        // Expensive connection setup happens immediately
        connectToDatabase();
    }

    private void connectToDatabase() {
        System.out.println("Connecting to database: " + connectionString);
        try {
            Thread.sleep(1000); // Simulate expensive operation
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Database connection established");
    }

    public void query(String sql) {
        // No access control, logging, or caching
        System.out.println("Executing query: " + sql);
    }
}

// Client code - problems with this approach
public class ProxyViolation {
    public static void main(String[] args) {
        // Connection created immediately even if not used
        DatabaseService db1 = new DatabaseService("jdbc:mysql://localhost:3306/db1");
        DatabaseService db2 = new DatabaseService("jdbc:mysql://localhost:3306/db2");

        // No access control - anyone can execute any query
        db1.query("DELETE FROM users"); // Dangerous operation allowed

        // No logging or monitoring
        db2.query("SELECT * FROM products");

        // Problems:
        // 1. Expensive connections created immediately
        // 2. No access control or security
        // 3. No logging or monitoring
        // 4. No caching capabilities
    }
}
