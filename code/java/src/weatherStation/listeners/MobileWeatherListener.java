package weatherStation.listeners;

import weatherStation.model.WeatherEvent;
import weatherStation.observer.WeatherListener;

public class MobileWeatherListener implements WeatherListener {

    @Override
    public void update(WeatherEvent event) {
        System.out.println("[Web] Weather update for " + event.getCity() + ": "
                + event.getData());
    }
}



