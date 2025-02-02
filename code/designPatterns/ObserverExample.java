import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// The base publisher class includes subscription management
// code and notification methods.
class EventManager {
    private Map<String, List<EventListener>> listeners = new HashMap<>();

    public EventManager() {
        listeners.put("open", new ArrayList<>());
        listeners.put("save", new ArrayList<>());
    }

    public void subscribe(String eventType, EventListener listener) {
        listeners.get(eventType).add(listener);
    }

    public void unsubscribe(String eventType, EventListener listener) {
        listeners.get(eventType).remove(listener);
    }

    public void notify(String eventType, String data) {
        for (EventListener listener : listeners.get(eventType)) {
            listener.update(data);
        }
    }
}

// The concrete publisher contains real business logic that's
// interesting for some subscribers.
class Editor {
    public EventManager events;
    private File file;

    public Editor() {
        events = new EventManager();
    }

    // Methods of business logic can notify subscribers about changes.
    public void openFile(String path) {
        this.file = new File(path);
        events.notify("open", file.getName());
    }

    public void saveFile() throws IOException {
        if (file != null) {
            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write(""); // Simulating a save operation
            }
            events.notify("save", file.getName());
        }
    }
}

// The subscriber interface
interface EventListener {
    void update(String filename);
}

// Concrete subscribers react to updates issued by the publisher.
class LoggingListener implements EventListener {
    private File log;
    private String message;

    public LoggingListener(String logFilename, String message) {
        this.log = new File(logFilename);
        this.message = message;
    }

    @Override
    public void update(String filename) {
        try (FileWriter writer = new FileWriter(log, true)) {
            writer.write(message.replace("%s", filename) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class EmailAlertsListener implements EventListener {
    private String email;
    private String message;

    public EmailAlertsListener(String email, String message) {
        this.email = email;
        this.message = message;
    }

    @Override
    public void update(String filename) {
        System.out.println("Sending email to " + email + ": " + message.replace("%s", filename));
    }
}

// Application class configuring publishers and subscribers
public class ObserverExample {
    public static void main(String[] args) {
        Editor editor = new Editor();

        LoggingListener logger = new LoggingListener(
                "log.txt",
                "Someone has opened the file: %s"
        );
        editor.events.subscribe("open", logger);

        EmailAlertsListener emailAlerts = new EmailAlertsListener(
                "admin@example.com",
                "Someone has changed the file: %s"
        );
        editor.events.subscribe("save", emailAlerts);

        try {
            editor.openFile("test.txt");
            editor.saveFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
