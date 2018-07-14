package org.kochab.simulatedannealing;

/**
 * An interface representing a minimization problem.
 *
 * @param <T> The type of the optimization parameter.
 */
public interface Minimizable<T> {
    /**
     * Returns the energy of the given state.
     *
     * @param state The state.
     * @return The energy value for the state.
     */
    double energy(T state);

    /**
     * Returns the successor state of the given state.
     *
     * @param state The state.
     * @return The successor state.
     */
    T next(T state);
}
