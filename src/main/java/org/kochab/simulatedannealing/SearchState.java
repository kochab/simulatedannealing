package org.kochab.simulatedannealing;

public interface SearchState<T extends SearchState<T>> {
    T step();
}
