package trafficSignal;

class RedLightState implements TrafficState {
    private int time = 3;
    private TrafficSignalContext context;


    @Override
    public TrafficState getNextState() {
        return new YellowLightState();
    }

    @Override
    public void setContext(TrafficSignalContext context) {
        this.context = context;
    }

    @Override
    public int getDuration() {
        return this.time;
    }

    @Override
    public void setDuration() throws InterruptedException {
        TrafficSensor sensor = new TrafficSensor();
        String trafficLevel = sensor.getTrafficLevel();

        switch (trafficLevel) {
            case "LOW" -> this.time = 2;
            case "MEDIUM" -> this.time = 3;
            case "HIGH" -> this.time = 4;
            case "EMERGENCY" -> {
                System.out.println("Emergency vehicle detected, changing to green light immediately!");
                context.setState(new GreenLightState());
            }
            default -> System.out.println("No data from sensor, using default duration.");
        }
    }
}
