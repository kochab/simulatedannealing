package org.kochab.simulatedannealing;

/**
 * A listener callback type for minimum discovery events.
 *
 * @param <T> The type of search state.
 */
@FunctionalInterface
public interface MinimumListener<T extends SearchState<T>> {
    /**
     * Called when a new minimum has been found by the solver.
     *
     * @param temperature The temperature the new minimum was found at.
     * @param steps The iteration the new minimum was found at.
     * @param minState The newly-discovered local minimum.
     * @param minStateEnergy The energy level of the newly-discovered minimum.
     */
    void onMinimum(double temperature, long steps, T minState, double minStateEnergy);
}
