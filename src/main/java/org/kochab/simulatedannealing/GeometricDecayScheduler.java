package org.kochab.simulatedannealing;

public class GeometricDecayScheduler implements Scheduler {
    static final double EPSILON = 0.001;
    final double initialTemperature;
    final double alpha;

    public GeometricDecayScheduler(double initialTemperature, int totalSteps) {
        this.initialTemperature = initialTemperature;
        alpha = Math.pow(EPSILON, 1.0 / totalSteps);
    }

    @Override
    public double getTemperature(int steps) {
        double temperature = initialTemperature * Math.pow(alpha, (double)steps);
        return temperature < EPSILON ? 0 : temperature;
    }
}
