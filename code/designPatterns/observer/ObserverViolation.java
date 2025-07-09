
package observer;

// VIOLATES Observer Pattern - Tight coupling between subject and observers
class NewsAgency2 {
    private String news;

    // VIOLATION: Direct references to concrete observer classes
    private NewsChannel2 cnn;
    private NewsChannel2 bbc;
    private NewsChannel2 fox;

    public void setCNN(NewsChannel2 cnn) {
        this.cnn = cnn;
    }

    public void setBBC(NewsChannel2 bbc) {
        this.bbc = bbc;
    }

    public void setFox(NewsChannel2 fox) {
        this.fox = fox;
    }

    public void setNews(String news) {
        this.news = news;

        // VIOLATION: Manually calling each observer - tight coupling
        if (cnn != null) {
            cnn.receiveNews(news);
        }
        if (bbc != null) {
            bbc.receiveNews(news);
        }
        if (fox != null) {
            fox.receiveNews(news);
        }

        // VIOLATION: Adding new observers requires code changes here
        // No dynamic subscription/unsubscription
    }
}

class NewsChannel2 {
    private String channelName;

    public NewsChannel2(String channelName) {
        this.channelName = channelName;
    }

    public void receiveNews(String news) {
        System.out.println(channelName + " received news: " + news);
    }
}

public class ObserverViolation {
    public static void main(String[] args) {
        NewsAgency2 agency = new NewsAgency2();

        NewsChannel2 cnn = new NewsChannel2("CNN");
        NewsChannel2 bbc = new NewsChannel2("BBC");

        // VIOLATION: Must manually wire each observer
        agency.setCNN(cnn);
        agency.setBBC(bbc);

        agency.setNews("Breaking: New technology discovered!");
        // Output:
        // CNN received news: Breaking: New technology discovered!
        // BBC received news: Breaking: New technology discovered!

        // PROBLEM: Can't easily add/remove observers at runtime
        // PROBLEM: Agency must know about all observer types
    }
}
