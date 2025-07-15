package trafficSignal;

public interface TrafficState {
    int getDuration();
    void setDuration() throws InterruptedException;
    TrafficState getNextState();
    void setContext(TrafficSignalContext context);
}

