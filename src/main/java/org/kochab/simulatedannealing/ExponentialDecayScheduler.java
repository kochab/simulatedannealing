package org.kochab.simulatedannealing;

public class ExponentialDecayScheduler implements Scheduler {
    static final double EPSILON = 0.001;
    final double initialTemperature;
    final double decayRate;

    public ExponentialDecayScheduler(double initialTemperature, long totalSteps) {
        this.initialTemperature = initialTemperature;
        decayRate = Math.log(EPSILON / initialTemperature) / totalSteps;
    }

    @Override
    public double getTemperature(long steps) {
        double temperature = initialTemperature * Math.exp(decayRate * steps);
        return temperature < EPSILON ? 0 : temperature;
    }
}
