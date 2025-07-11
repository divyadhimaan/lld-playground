package singleton;


// Singleton class
class PrinterSpooler2 {
    private static PrinterSpooler2 instance;

    // Private constructor to prevent instantiation
    private PrinterSpooler2() {
        System.out.println("Spooler initialized");
    }

    // Thread-safe, lazy initialization
    public static synchronized PrinterSpooler2 getInstance() {
        if (instance == null) {
            instance = new PrinterSpooler2();
        }
        return instance;
    }

    public void addPrintJob(String job) {
        System.out.println("Added print job: " + job);
    }
}

// Usage
public class SingletonSample {
    public static void main(String[] args) {
        PrinterSpooler2 spooler1 = PrinterSpooler2.getInstance();
        spooler1.addPrintJob("Doc1");

        PrinterSpooler2 spooler2 = PrinterSpooler2.getInstance();
        spooler2.addPrintJob("Doc2");

        System.out.println("spooler1 == spooler2: " + (spooler1 == spooler2)); // true ✅
    }
}
