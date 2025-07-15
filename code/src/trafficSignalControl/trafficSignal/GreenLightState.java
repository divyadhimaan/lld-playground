package trafficSignal;

class GreenLightState implements TrafficState {

    private int time = 4;
    private TrafficSignalContext context;

    @Override
    public TrafficState getNextState() {
        return new RedLightState();
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
                System.out.println("Emergency vehicle detected, Already Green Light");
                this.time = 5;
            }
            case null, default -> System.out.println("No data from sensor, using default duration.");
        }
    }
}
