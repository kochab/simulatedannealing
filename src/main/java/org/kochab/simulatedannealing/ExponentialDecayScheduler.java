package org.kochab.simulatedannealing;

/**
 * Exponential annealing scheduler.
 *
 * This scheduler defines the temperature for an iteration i according to the formula
 *  t(i) = t0 * exp(decay * i)
 *
 * where decay is defined as
 *  decay = log(epsilon / t0) / iterations
 */
public class ExponentialDecayScheduler implements Scheduler {
    static final double EPSILON = 0.001;
    final double initialTemperature;
    final double decayRate;

    /**
     * Creates a new exponential annealing scheduler.
     *
     * @param initialTemperature The starting temperature.
     * @param totalSteps The total number of steps the annealing process should have.
     */
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
