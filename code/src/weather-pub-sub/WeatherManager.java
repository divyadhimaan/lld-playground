import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WeatherManager {
    Map<String, Set<WeatherListener>> listeners;
    Map<String, Integer> temperatures;

    public WeatherManager()
    {
        listeners = new HashMap<>();
        this.temperatures = new HashMap<>();
        temperatures.put("Pune", 32);
        temperatures.put("Bangalore", 28);
    }

    public void subscribe(String city, WeatherListener weatherListener)
    {
        if(listeners.containsKey(city))
            listeners.get(city).add(weatherListener);
        else{
            Set<WeatherListener> currListener = new HashSet<>();
            currListener.add(weatherListener);

            listeners.put(city, currListener);
        }
    }

    public void unSubscribe(String city, WeatherListener weatherListener)
    {
        if (!listeners.containsKey(city)) {
            System.out.println("Error: No listeners exist for " + city);
            return;
        }
        Set<WeatherListener> cityListeners = listeners.get(city);

        if (!cityListeners.contains(weatherListener)) {
            System.out.println("Error: The provided listener is not subscribed to " + city);
            return;
        }

        cityListeners.remove(weatherListener);
        System.out.println("Unsubscribed successfully from " + city);

        if (cityListeners.isEmpty()) {
            listeners.remove(city);
            System.out.println("No more listeners for " + city + ". Removing from subscription list.");
        }
    }

    public void notifyListeners(String city)
    {
        Set<WeatherListener> targetListeners = listeners.get(city);
        if(targetListeners != null) {
            System.out.println("Weather in " + city + " changed to " + getTemp(city) + " Notifying all the users...");
            for (WeatherListener currListener : targetListeners) {
                currListener.Update();
            }
        }
        else{
            System.out.println("No Listeners for this city");
        }

    }

    public void setTemp(String city, int newTemp)
    {
        temperatures.put(city, newTemp);
        notifyListeners(city);
    }

    public int getTemp(String city)
    {
        if (!temperatures.containsKey(city)) {
            System.out.println("Error: Temperature data for " + city + " is not available.");
            return -1;
        }
        return temperatures.get(city);
    }

}
