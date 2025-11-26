package weatherStation.observer;

import weatherStation.model.WeatherData;

public interface WeatherSubject {
    public void subscribe(String city, WeatherListener weatherListener);
    public void unSubscribe(String city, WeatherListener weatherListener);
    public void notifyListeners(String city, WeatherData data);
}
