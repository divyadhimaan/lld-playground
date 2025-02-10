// Step 1: Abstract Handler
abstract class Logger {
    protected Logger nextLogger;

    public void setNextLogger(Logger nextLogger) {
        this.nextLogger = nextLogger;
    }

    public void logMessage(int level, String message) {
        if (canHandle(level)) {
            writeMessage(message);
        }
        if (nextLogger != null) {
            nextLogger.logMessage(level, message);
        }
    }

    protected abstract boolean canHandle(int level);
    protected abstract void writeMessage(String message);
}

// Step 2: Concrete Handlers
class InfoLogger extends Logger {
    protected boolean canHandle(int level) {
        return level == 1;
    }

    protected void writeMessage(String message) {
        System.out.println("INFO: " + message);
    }
}

class DebugLogger extends Logger {
    protected boolean canHandle(int level) {
        return level == 2;
    }

    protected void writeMessage(String message) {
        System.out.println("DEBUG: " + message);
    }
}

class ErrorLogger extends Logger {
    protected boolean canHandle(int level) {
        return level == 3;
    }

    protected void writeMessage(String message) {
        System.out.println("ERROR: " + message);
    }
}

// Step 3: Client Code to Set up the Chain
public class ChainOfResponsibilityExample {
    public static void main(String[] args) {
        Logger infoLogger = new InfoLogger();
        Logger debugLogger = new DebugLogger();
        Logger errorLogger = new ErrorLogger();

        // Set up the chain
        infoLogger.setNextLogger(debugLogger);
        debugLogger.setNextLogger(errorLogger);

        // Make log requests
        infoLogger.logMessage(1, "This is an info message.");
        infoLogger.logMessage(2, "This is a debug message.");
        infoLogger.logMessage(3, "This is an error message.");
    }
}


