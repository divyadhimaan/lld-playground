interface PublisherInterface {
    void publish(String topicName, Message message);
}

public class Publisher implements PublisherInterface {
    private String publisherName;
    private TopicRegistry topicRegistry;

    public Publisher(String name, TopicRegistry topicRegistry)
    {
        this.publisherName = name;
        this.topicRegistry = topicRegistry;
    }
    public void publish(String topicName, Message message)
    {
        System.out.println("[Publisher " + publisherName + "] Publishing to '" + topicName + "': " + message.getContent());
        topicRegistry.publish(topicName, message);
    }

}
