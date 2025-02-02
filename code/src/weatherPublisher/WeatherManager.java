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
        if(listeners.containsKey(city) && !listeners.get(city).isEmpty() && listeners.get(city).contains(weatherListener))
            listeners.get(city).remove(weatherListener);
        else{
            System.out.println("Cannot unsubscribe");
        }
    }

    public void notifyListeners(String city)
    {
        Set<WeatherListener> targetListeners = listeners.get(city);
        if(targetListeners != null) {
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
        return temperatures.get(city);
    }

}
