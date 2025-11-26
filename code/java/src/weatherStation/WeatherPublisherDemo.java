package weatherStation;

import weatherStation.model.WeatherData;
import weatherStation.observer.WeatherListener;
import weatherStation.listeners.MobileWeatherListener;
import weatherStation.listeners.WebWeatherListener;
import weatherStation.model.WeatherManager;

import java.util.Scanner;

public class WeatherPublisherDemo {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        WeatherManager weatherManager = new WeatherManager();

        weatherManager.subscribe("Pune", new WebWeatherListener());
        weatherManager.subscribe("Bangalore", new MobileWeatherListener());


        int counter = 5;
        while (counter-- > 0) {

            System.out.print("\nEnter city: ");
            String city = scanner.nextLine();

            System.out.print("Enter temperature: ");
            int temp = scanner.nextInt();

            System.out.print("Enter humidity: ");
            int hum = scanner.nextInt();

            System.out.print("Enter wind speed: ");
            int wind = scanner.nextInt();
            scanner.nextLine();

            weatherManager.updateWeather(city, new WeatherData(temp, hum, wind));
        }

    }
}
