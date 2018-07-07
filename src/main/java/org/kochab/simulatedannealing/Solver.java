package org.kochab.simulatedannealing;

import java.util.Random;

/**
 * Simulated annealing optimizer.
 *
 * @param <T> Type of the optimization problem's search states.
 */
public class Solver<T extends SearchState<T>> {
    final Problem<T> problem;
    final Scheduler scheduler;
    final Random random;
    final MinimumListener<? super T> listener;

    /**
     * Creates a new optimizer.
     *
     * @param problem The minimization problem's representation.
     * @param scheduler The annealing scheduler to use.
     * @param random The random number source to use for calculating acceptance probabilities.
     * @param listener The minimum listener used for this optimizer.
     */
    public Solver(Problem<T> problem, Scheduler scheduler, Random random, MinimumListener<? super T> listener) {
        this.problem = problem;
        this.scheduler = scheduler;
        this.random = random;
        this.listener = listener;
    }

    /**
     * Creates a new optimizer.
     *
     * @param problem The minimization problem's representation.
     * @param scheduler The annealing scheduler to use.
     * @param random The random number source to use for calculating acceptance probabilities.
     */
    public Solver(Problem<T> problem, Scheduler scheduler, Random random) {
        this(problem, scheduler, random, null);
    }

    /**
     * Creates a new optimizer.
     *
     * @param problem The minimization problem's representation.
     * @param scheduler The annealing scheduler to use.
     * @param listener The minimum listener used for this optimizer.
     */
    public Solver(Problem<T> problem, Scheduler scheduler, MinimumListener<? super T> listener) {
        this(problem, scheduler, new Random(), listener);
    }

    /**
     * Creates a new optimizer.
     *
     * @param problem The minimization problem's representation.
     * @param scheduler The annealing scheduler to use.
     */
    public Solver(Problem<T> problem, Scheduler scheduler) {
        this(problem, scheduler, new Random(), null);
    }

    /**
     * Solves the minimization problem.
     *
     * @return The state with the least amount of energy (minimum).
     */
    public T solve() {
        // Generate the initial state.
        T currentState = problem.initialState();

        // If the initial state is invalid (energy=NaN), keep trying mutating it until a state with a valid
        // (non-NaN) energy is produced.
        while (Double.isNaN(problem.energy(currentState))) {
            currentState = currentState.step();
        }

        // At the first iteration, the minimum state (i.e. state with the least energy) is the initial state.
        T minState = currentState;

        long steps = 0;

        for (;;) {
            double temperature = scheduler.getTemperature(steps++);

            // Terminate the annealing if the cooling ends (temperature reaches zero).
            if (temperature <= 0.0) {
                return minState;
            }

            // Generate the next state.
            T nextState = currentState.step();

            // If the next state is invalid (energy=NaN), keep mutating its predecessor until a state with
            // a valid (non-NaN) energy value is produced.
            while (Double.isNaN(problem.energy(nextState))) {
                nextState = currentState.step();
            }

            // Calculate the change in energy between the next state and its predecessor.
            double energyChange = problem.energy(nextState) - problem.energy(currentState);

            if (acceptChange(temperature, energyChange)) {
                // On acceptance, the successor state becomes the current state (state transition).
                currentState = nextState;

                // Check if the state we transitioned into is a new local minimum (i.e. has less energy than our
                // current minimum).
                if (problem.energy(currentState) < problem.energy(minState)) {
                    // Update the best-so-far minimum.
                    minState = currentState;
                    // Call the minimum listener callback if it exists.
                    if (listener != null) {
                        listener.onMinimum(temperature, steps, minState);
                    }
                }
            }
        }
    }

    /** Always accept changes that decrease energy. Otherwise, use the simulated annealing. */
    private boolean acceptChange(double temperature, double energyChange) {
        if (energyChange < 0.0) {
            return true;
        } else {
            return random.nextDouble() <= Math.exp(-energyChange / temperature);
        }
    }
}
