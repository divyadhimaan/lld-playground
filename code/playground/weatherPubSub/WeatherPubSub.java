package weatherPubSub;

import java.util.ArrayList;
import java.util.List;

interface WeatherPublisher {
    void subscribe(WeatherSubscriber subscriber);
    void unsubscribe(WeatherSubscriber subscriber);
    void notifySubscribers();
}

class WeatherStation implements WeatherPublisher {
    private final List<WeatherSubscriber> subscribers = new ArrayList<>();
    int temperature;

    WeatherStation(int initialTemperature) {
        this.temperature = initialTemperature;
    }

    @Override
    public void subscribe(WeatherSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(WeatherSubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers() {
        String weatherUpdate = "Weather update - Temperature is " + temperature + "°C";
        for (WeatherSubscriber subscriber : subscribers) {
            subscriber.update(weatherUpdate);
        }
    }

    public void setTemperature(int newTemperature) {
        this.temperature = newTemperature;
        notifySubscribers();
    }
}



interface WeatherSubscriber {
    void update(String weatherUpdate);
}

class WebWeatherDisplay implements WeatherSubscriber {
    @Override
    public void update(String weatherUpdate) {
        System.out.println("Web Display: " + weatherUpdate);
    }
}

class MobileWeatherApp implements WeatherSubscriber {
    @Override
    public void update(String weatherUpdate) {
        System.out.println("Mobile App: " + weatherUpdate);
    }
}




public class WeatherPubSub {

    public static void main(String[] args) {
        WeatherStation weatherStation = new WeatherStation(25);

        WebWeatherDisplay webDisplay = new WebWeatherDisplay();
        MobileWeatherApp mobileApp = new MobileWeatherApp();

        weatherStation.subscribe(webDisplay);
        weatherStation.subscribe(mobileApp);

        weatherStation.notifySubscribers(); // Initial notification

        // Simulate a temperature change
        weatherStation.setTemperature(30); // This will notify all subscribers
    }
}
