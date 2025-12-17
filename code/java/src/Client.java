
//Base component
interface Notifier {
    void send(String message);
}

// Concrete Component (Base Notifier)
class BaseNotifier implements Notifier {
    @Override
    public void send(String message) {
        // Base behavior (can be empty or logging)
        System.out.println("Notification triggered");
    }
}


// Abstract Decorator
abstract class NotifierDecorator implements Notifier {
    protected Notifier notifier;

    public NotifierDecorator(Notifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void send(String message) {
        notifier.send(message);
    }
}

// Concrete Decorators
class SMSNotifier extends NotifierDecorator {

    public SMSNotifier(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("Sending SMS: " + message);
    }
}
class FacebookNotifier extends NotifierDecorator {

    public FacebookNotifier(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("Sending Facebook message: " + message);
    }
}
class SlackNotifier extends NotifierDecorator {

    public SlackNotifier(Notifier notifier) {
        super(notifier);
    }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("Sending Slack message: " + message);
    }
}


// Client usage

public class Client {
    public static void main(String[] args) {
        Notifier notifier =
                new SlackNotifier(
                        new SMSNotifier(
                                new BaseNotifier()
                        )
                );

        notifier.send("Server is down!");
        Notifier notifier2 =
                new SlackNotifier(
                        new FacebookNotifier(
                                new SMSNotifier(
                                        new BaseNotifier()
                                )
                        )
                );

        notifier2.send("High CPU usage detected!");

    }
}