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
     * @param searchState The newly-discovered local minimum.
     */
    void onMinimum(T searchState);
}
