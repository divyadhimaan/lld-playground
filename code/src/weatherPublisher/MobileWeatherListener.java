public class MobileWeatherListener implements WeatherListener{
    WeatherManager manager;

    public MobileWeatherListener(WeatherManager weatherManager)
    {
        this.manager = weatherManager;
    }

    @Override
    public void Update(){
        System.out.println("Weather Updated on Mobile");
    }
}



