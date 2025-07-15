package trafficSignal;

class TrafficSensor {
    public String getTrafficLevel() {
        int trafficLevel = (int) (Math.random() * 4);
        return switch (trafficLevel) {
            case 0 -> "LOW";
            case 1 -> "MEDIUM";
            case 2 -> "HIGH";
            default -> {
                System.out.println("🚨 Emergency vehicle detected!");
                yield "EMERGENCY";
            }
        };
    }
}
