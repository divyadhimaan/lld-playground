# Weather Pub-Sub Application

Requirements - 

1. Weather collecting application (Publisher) is city specific.
2. We have observers/subscribers waiting for weather updates for their particular subscribed cities.
3. Subscribers should be able to subscribe/unsubscribe to weather topic.
4. As soon as there is a weather change, Messages should be delivered to all subscribers.
5. There can be multiple observers.
6. Temperature is the changing parameter. (city->temp)


[Java Implementation](./../code/src/weather-pub-sub) | [Design Explanation](./../code/src/weather-pub-sub/Weather-pub-sub.md)






