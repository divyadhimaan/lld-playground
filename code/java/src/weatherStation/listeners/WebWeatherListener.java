package weatherStation.listeners;

import weatherStation.model.WeatherEvent;
import weatherStation.observer.WeatherListener;

public class WebWeatherListener implements WeatherListener {


    @Override
    public void update(WeatherEvent event) {
        System.out.println("[Mobile] Weather update for " + event.getCity() + ": "
                + event.getData());
    }
}
