package control;

import trafficSignal.TrafficSignalContext;

public class TrafficSignalControl {
    public static void main(String[] args) throws InterruptedException {
        TrafficSignalContext context = TrafficSignalContext.getInstance();
        context.startTrafficSignal();

    }
}
