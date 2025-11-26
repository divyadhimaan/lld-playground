package weatherStation.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherData {
    private int temperature;
    private int humidity;
    private int windSpeed;

    public WeatherData(int temp, int humidity, int windSpeed){
        this.temperature = temp;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    @Override
    public String toString() {
        return "Temp: " + temperature + ", Humidity: " + humidity + ", Wind: " + windSpeed;
    }
}
