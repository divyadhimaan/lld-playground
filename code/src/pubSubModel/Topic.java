import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Topic {
    private String topicName;
    private final Set<Subscriber> subscribers = ConcurrentHashMap.newKeySet();

    public Topic(String name)
    {
        topicName = name;
    }

    public void subscribe(Subscriber subscriber)
    {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber)
    {
        subscribers.remove(subscriber);
    }

    public void broadcast(Message message)
    {
        for(Subscriber subscriber: subscribers){
            subscriber.update(topicName, message);
        }
    }


}
