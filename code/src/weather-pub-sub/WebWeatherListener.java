public class WebWeatherListener implements WeatherListener {

    WeatherManager manager;

    public WebWeatherListener(WeatherManager weatherManager)
    {
        this.manager = weatherManager;
    }


    @Override
    public void Update(){
        System.out.println("Weather Updated on Web");
    }
}
