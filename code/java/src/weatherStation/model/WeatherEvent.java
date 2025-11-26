package weatherStation.model;

import lombok.Getter;

@Getter
public class WeatherEvent {
    private final String city;
    private final WeatherData data;

    public WeatherEvent(String city, WeatherData data) {
        this.city = city;
        this.data = data;
    }
}
