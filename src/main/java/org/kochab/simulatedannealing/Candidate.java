package org.kochab.simulatedannealing;

public interface Candidate<T> {
    /**
     * Get the iteration of this candidate solution.
     *
     * @return The iteration this solution was found at.
     */
    long getIteration();

    /**
     * Get the temperature of this candidate solution.
     *
     * @return The temperature this solution was found at.
     */
    double getTemperature();

    /**
     * Get the value of this candidate of this candidate solution.
     *
     * @return The value of the candidate solution.
     */
    T getState();

    /**
     * Returns whether this candidate solution is a new minimum.
     *
     * @return true if this solution is a new minimum, false otherwise.
     */
    boolean isMinimum();
}
