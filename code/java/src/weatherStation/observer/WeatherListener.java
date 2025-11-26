package weatherStation.observer;

import weatherStation.model.WeatherEvent;

public interface WeatherListener {
    void update(WeatherEvent event);
}