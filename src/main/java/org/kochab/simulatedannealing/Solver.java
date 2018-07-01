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
    MinimumListener<? super T> listener;

    /**
     * Creates a new optimizer.
     *
     * @param problem The minimization problem's representation.
     * @param scheduler The annealing scheduler to use.
     * @param random The random number source to use for calculating acceptance probabilities.
     */
    public Solver(Problem<T> problem, Scheduler scheduler, Random random) {
        this.problem = problem;
        this.scheduler = scheduler;
        this.random = random;
    }

    /**
     * Creates a new optimizer that uses java.util.Random to calculated acceptance
     * probabilities.
     *
     * @param problem The minimization problem's representation.
     * @param scheduler The annealing scheduler to use.
     */
    public Solver(Problem<T> problem, Scheduler scheduler) {
        this(problem, scheduler, new Random());
    }

    /**
     * Sets the minimum listener for this optimizer.
     *
     * The listener callback will be called when a new local minimum is discovered.
     *
     * @param listener The minimum listener to use.
     */
    public void setListener(MinimumListener<? super T> listener) {
        this.listener = listener;
    }

    /**
     * Solves the minimization problem.
     *
     * @return The state with the least amount of energy (minimum).
     */
    public T solve() {
        T currentState = problem.initialState();
        double currentStateEnergy = problem.energy(currentState);
        while (!Double.isFinite(currentStateEnergy)) {
            currentState = currentState.step();
            currentStateEnergy = problem.energy(currentState);
        }

        T minState = currentState;
        double minStateEnergy = currentStateEnergy;
        long steps = 0;

        for (;;) {
            double temperature = scheduler.getTemperature(steps++);

            if (temperature <= 0.0) {
                return minState;
            }

            T nextState = currentState.step();
            double nextStateEnergy = problem.energy(nextState);
            while (!Double.isFinite(nextStateEnergy)) {
                nextState = currentState.step();
                nextStateEnergy = problem.energy(nextState);
            }

            double energyChange = nextStateEnergy - currentStateEnergy;
            if (acceptChange(temperature, energyChange)) {
                currentState = nextState;
                currentStateEnergy = nextStateEnergy;
                if (currentStateEnergy < minStateEnergy) {
                    minState = currentState;
                    minStateEnergy = currentStateEnergy;
                    if (listener != null) {
                        listener.onMinimum(temperature, steps, minState, minStateEnergy);
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
            return random.nextDouble() <= Math.exp(-1.0 * energyChange / temperature);
        }
    }
}
