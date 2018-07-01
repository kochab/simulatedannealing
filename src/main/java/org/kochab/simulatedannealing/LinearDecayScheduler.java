package org.kochab.simulatedannealing;

/**
 * Linear annealing scheduler.
 *
 * This scheduler defines the temperature for an iteration i according to the formula
 *  t(i) = 1 - (i / iteration) * t0
 */
public class LinearDecayScheduler implements Scheduler {
    final double initialTemperature;
    final long totalSteps;

    /**
     * Creates a new linear annealing scheduler.
     *
     * @param initialTemperature The starting temperature.
     * @param totalSteps The total number of steps the annealing process should have.
     */
    public LinearDecayScheduler(double initialTemperature, long totalSteps) {
        this.initialTemperature = initialTemperature;
        this.totalSteps = totalSteps;
    }

    @Override
    public double getTemperature(long steps) {
        return (1 - ((double) steps) / totalSteps) * initialTemperature;
    }
}
