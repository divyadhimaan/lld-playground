import java.util.Scanner;

public class WeatherPublisherDemo {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        WeatherManager weatherManager = new WeatherManager();

        WeatherListener webWeatherListener = new WebWeatherListener(weatherManager);
        weatherManager.subscribe("Pune", webWeatherListener);


        WeatherListener mobileWeatherListener = new MobileWeatherListener(weatherManager);
        weatherManager.subscribe("Bangalore", mobileWeatherListener);

        System.out.println(weatherManager.getTemp("Pune"));
        System.out.println(weatherManager.getTemp("Bangalore"));

        int counter = 5;
        while(counter>0)
        {

            System.out.print("enter city: ");
            String city = scanner.nextLine();

            System.out.print("enter temperature: ");
            int temp = scanner.nextInt();
            scanner.nextLine();

            weatherManager.setTemp(city, temp);
            counter--;
        }




    }
}
