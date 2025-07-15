package trafficSignal;

import java.util.Objects;

public class TrafficSignalContext {

    private static TrafficSignalContext context;
    private TrafficState currentState;

    public TrafficSignalContext() {
        currentState = new RedLightState();
        this.currentState.setContext(this);
    }
    public static synchronized TrafficSignalContext getInstance() {
        if (context == null) {
            context = new TrafficSignalContext();
        }
        return context;
    }

    public void startTrafficSignal() throws InterruptedException {
        while (true) {
            currentState.setDuration(); // Set time based on traffic
            System.out.println("🚦 Current State: " + currentState.getClass().getSimpleName() +
                    " | Duration: " + currentState.getDuration() + " seconds");



            Thread.sleep(currentState.getDuration() * 1000L);

            TrafficState nextState = currentState.getNextState();
            setState(nextState);
        }
    }


    public void setState(TrafficState state) {
        if (state != null && !Objects.equals(currentState.getClass(), state.getClass())) {
            this.currentState = state;
            this.currentState.setContext(this);
        }
    }

}
