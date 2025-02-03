interface PaymentStrategy
{
    double calculate(long duration);
}

class HourlyPaymentStrategy implements PaymentStrategy{
    private static final double HOURLY_RATE = 2.0;
    @Override
    public double calculate(long duration){
        long hours = duration / (1000 * 60 * 60);
        if (duration % (1000 * 60 * 60) != 0) {
            hours++;
        }
        return HOURLY_RATE * hours;
    };
}

class MinutePaymentStrategy implements PaymentStrategy{
    private static final double MINUTE_RATE = 0.05;  // $0.05 per minute
    @Override
    public double calculate(long duration) {
        long minutes = duration / (1000 * 60);

        return MINUTE_RATE * minutes;
    }
}



