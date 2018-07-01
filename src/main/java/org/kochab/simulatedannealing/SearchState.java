package org.kochab.simulatedannealing;

/**
 * Interface representing a search state.
 *
 * @param <T> Type of the search state subclass.
 */
public interface SearchState<T extends SearchState<T>> {
    /**
     * Returns a random perturbation of this state.
     *
     * @return A new random perturbation of this state.
     */
    T step();
}
