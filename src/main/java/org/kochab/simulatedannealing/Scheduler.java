package org.kochab.simulatedannealing;

/**
 * Interface for annealing scheduler types.
 */
@FunctionalInterface
public interface Scheduler {
    /**
     * Calculates the temperate for a given iteration.
     *
     * @param steps The iteration number.
     * @return The temperature level at the given iteration.
     */
    double getTemperature(long steps);
}
