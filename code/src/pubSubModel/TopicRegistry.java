import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TopicRegistry {
    private static TopicRegistry instance;
    private Map<String, Topic> topics = new ConcurrentHashMap<>();


    public static synchronized TopicRegistry getInstance()
    {
        if(instance == null)
            instance = new TopicRegistry();
        return instance;
    }

    public void createTopic(String topicName)
    {
        System.out.println("Topic Created: [" + topicName + "]");
        topics.putIfAbsent(topicName, new Topic(topicName));
    }

    public void subscribe(String topicName, Subscriber subscriber)
    {
        topics.computeIfAbsent(topicName, Topic::new).subscribe(subscriber);
    }

    public void unsubscribe(String topicName, Subscriber subscriber)
    {
        if(topics.containsKey(topicName))
        {
            topics.get(topicName).unsubscribe(subscriber);
        }
    }

    public void publish(String topicName, Message message)
    {
        if(topics.containsKey(topicName))
        {
            topics.get(topicName).broadcast(message);
        }
    }

    public void displayAllTopics()
    {
        System.out.println("Available Topics: " + topics.keySet());
    }



}
