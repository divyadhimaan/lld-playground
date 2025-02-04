import java.util.Scanner;

public class pubSubDemo {
    public static void main(String[] args)
    {
        TopicRegistry topicRegistry = TopicRegistry.getInstance();
        topicRegistry.createTopic("Computers");
        topicRegistry.createTopic("Biology");


        //creating subscribers dynamically
        Subscriber subscriber1 = new Subscriber("subscriber1");
        Subscriber subscriber2 = new Subscriber("subscriber2");
        Subscriber subscriber3 = new Subscriber("subscriber3");


        subscriber1.subscribe(topicRegistry, "Computers");
        subscriber2.subscribe(topicRegistry, "Biology");
        subscriber2.subscribe(topicRegistry, "Computers");
        subscriber3.subscribe(topicRegistry, "Computers");

        //publisher publishes
        Publisher publisher1 = new Publisher("DailyCode", topicRegistry);
        publisher1.publish("Computers", new Message("New OOPs language is in town."));

        Publisher publisher2 = new Publisher("DailyBio", topicRegistry);
        publisher2.publish("Biology", new Message("New neurology study is in town."));


        subscriber2.unsubscribe(topicRegistry, "Computers");
        publisher1.publish("Computers", new Message("New ReactJs version available. Learn today!"));


    }
}
