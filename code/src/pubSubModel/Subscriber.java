
public class Subscriber {
    private final String subscriberName;

    public Subscriber(String name)
    {
        this.subscriberName = name;
    }
    public String getName(){
        return subscriberName;
    }
    public void update(String topicName, Message message)
    {
        System.out.println("[" + subscriberName + "] received message on '" + topicName + "': " + message.getContent());
    }
    public void subscribe(TopicRegistry topicRegistry, String topicName)
    {
        System.out.println("[" + subscriberName + "] subscribed '" + topicName + "':)");
        topicRegistry.subscribe(topicName, this);
    }
    public void unsubscribe(TopicRegistry topicRegistry, String topicName)
    {
        System.out.println("[" + subscriberName + "] unsubscribed '" + topicName + "':(");
        topicRegistry.unsubscribe(topicName, this);
    }
}
