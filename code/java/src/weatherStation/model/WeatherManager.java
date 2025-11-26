package weatherStation.model;

import weatherStation.observer.WeatherListener;
import weatherStation.observer.WeatherSubject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WeatherManager implements WeatherSubject {
    private final Map<String, Set<WeatherListener>> listeners;
    private final Map<String, WeatherData> weatherMap;

    public WeatherManager()
    {
        listeners = new HashMap<>();
        this.weatherMap = new HashMap<>();
        weatherMap.put("Pune", new WeatherData(32, 40, 10));
        weatherMap.put("Bangalore", new WeatherData(28, 50, 8));
    }

    @Override
    public void subscribe(String city, WeatherListener weatherListener)
    {
        listeners.computeIfAbsent(city, k-> new HashSet<>()).add(weatherListener);
    }

    @Override
    public void unSubscribe(String city, WeatherListener weatherListener)
    {
        if (!listeners.containsKey(city)) {
            System.out.println("Error: No listeners exist for " + city);
            return;
        }
        listeners.get(city).remove(weatherListener);

        if (listeners.get(city).isEmpty()) {
            listeners.remove(city);
            System.out.println("No more listeners for " + city + ". Removing from subscription list.");
        }
        System.out.println("Unsubscribed successfully from " + city);

    }

    @Override
    public void notifyListeners(String city, WeatherData data)
    {
        if (!listeners.containsKey(city)) {
            System.out.println("⚠ No listeners subscribed for " + city);
            return;
        }

        WeatherEvent event = new WeatherEvent(city, data);

        for(WeatherListener listener: listeners.get(city)){
            listener.update(event);
        }

    }

    public void updateWeather(String city, WeatherData newData) {
        weatherMap.put(city, newData);
        notifyListeners(city, newData);
    }

    public WeatherData getWeather(String city) {
        return weatherMap.get(city);
    }

}
