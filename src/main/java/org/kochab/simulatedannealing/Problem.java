package org.kochab.simulatedannealing;

/**
 * An interface representing a minimization problem.
 *
 * @param <T> The type of the search state.
 */
public interface Problem<T extends SearchState<T>> {
    /**
     * Produces the starting state of the minimization problem.
     *
     * @return The starting state of the minimization problem.
     */
    T initialState();

    /**
     * Returns the energy of the given state.
     *
     * @param searchState The state.
     * @return The energy values for the state.
     */
    double energy(T searchState);
}
