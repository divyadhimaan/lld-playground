package observer;
import java.util.*;

// Observer interface
interface Observer {
    void update(String news);
}

// Subject interface
interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
}

// Concrete Subject
class NewsAgency implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private String news;

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(news);
        }
    }

    public void setNews(String news) {
        this.news = news;
        notifyObservers();
    }
}

// Concrete Observers
class NewsChannel implements Observer {
    private String channelName;

    public NewsChannel(String channelName) {
        this.channelName = channelName;
    }

    public void update(String news) {
        System.out.println(channelName + " received news: " + news);
    }
}

// Usage
public class ObserverSample {
    public static void main(String[] args) {
        NewsAgency agency = new NewsAgency();

        NewsChannel cnn = new NewsChannel("CNN");
        NewsChannel bbc = new NewsChannel("BBC");

        agency.attach(cnn);
        agency.attach(bbc);

        agency.setNews("Breaking: New technology discovered!");
        // Output:
        // CNN received news: Breaking: New technology discovered!
        // BBC received news: Breaking: New technology discovered!
    }
}