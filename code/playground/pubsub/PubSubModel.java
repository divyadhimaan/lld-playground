package pubsub;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class Topic {
    private String name;
    private final Set<Subscriber> subscribers = ConcurrentHashMap.newKeySet();

    public Topic(String name) {
        this.name = name;
    }

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void broadcast(Message message) {
        for (Subscriber subscriber : subscribers) {
            subscriber.update(name, message);
        }
    }

    public String getTopicName() {
        return name;
    }
}

class TopicRegistry {
    private static TopicRegistry instance;
    private final Map<String, Topic> topics = new ConcurrentHashMap<>();

    public static synchronized TopicRegistry getInstance() {
        if (instance == null) {
            instance = new TopicRegistry();
        }
        return instance;
    }

    public void createTopic(String topicName) {
        System.out.println("Topic Created: [" + topicName + "]");
        topics.putIfAbsent(topicName, new Topic(topicName));
    }

    public void subscribe(String topicName, Subscriber subscriber) {
        topics.computeIfAbsent(topicName, Topic::new).subscribe(subscriber);
    }

    public void unsubscribe(String topicName, Subscriber subscriber) {
        if (topics.containsKey(topicName)) {
            topics.get(topicName).unsubscribe(subscriber);
        }
    }

    public void publish(String topicName, Message message) {
        if (topics.containsKey(topicName)) {
            topics.get(topicName).broadcast(message);
        } else {
            System.out.println("Topic '" + topicName + "' does not exist.");
        }
    }

    public void displayAllTopics() {
        System.out.println("Available Topics: " + topics.keySet());
    }
}

class Message {
    private final String content;

    public Message(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

class Publisher {
    private final String name;
    private final TopicRegistry topicRegistry;

    public Publisher(String name, TopicRegistry topicRegistry) {
        this.name = name;
        this.topicRegistry = topicRegistry;
    }

    public void publish(String topicName, Message message) {
        System.out.println("[Publisher " + name + "] Publishing to '" + topicName + "': " + message.getContent());
        topicRegistry.publish(topicName, message);
    }
}

class Subscriber {
    private final String name;

    public Subscriber(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void update(String topicName, Message message) {
        System.out.println("[" + name + "] received message on '" + topicName + "': " + message.getContent());
    }

    public void subscribe(TopicRegistry topicRegistry, String topicName) {
        System.out.println("[" + name + "] subscribed to '" + topicName + "'");
        topicRegistry.subscribe(topicName, this);
    }

    public void unsubscribe(TopicRegistry topicRegistry, String topicName) {
        System.out.println("[" + name + "] unsubscribed from '" + topicName + "'");
        topicRegistry.unsubscribe(topicName, this);
    }
}



public class PubSubModel {
    public static void main(String[] args){
        TopicRegistry topicRegistry = TopicRegistry.getInstance();

        topicRegistry.createTopic("Maths");
        topicRegistry.createTopic("Biology");

        Subscriber A = new Subscriber("A");
        Subscriber B = new Subscriber("B");
        Subscriber C = new Subscriber("C");

        A.subscribe(topicRegistry, "Maths");
        B.subscribe(topicRegistry, "Biology");
        C.subscribe(topicRegistry, "Maths");

        Publisher P1 = new Publisher("P1", topicRegistry);
        P1.publish("Maths", new Message("Maths is fun!"));


        Publisher P2 = new Publisher("P2", topicRegistry);
        P2.publish("Biology", new Message("Biology is fascinating!"));



    }

}
