package org.kochab.simulatedannealing;

import java.util.Random;

public class Solver<T extends SearchState<T>> {
    final Problem<T> problem;
    final Scheduler scheduler;
    final Random random;

    public Solver(Problem<T> problem, Scheduler scheduler, Random random) {
        this.problem = problem;
        this.scheduler = scheduler;
        this.random = random;
    }
    
    public Solver(Problem<T> problem, Scheduler scheduler) {
        this(problem, scheduler, new Random());
    }

    public T solve() throws InfeasibleProblemException {
        T currentState = problem.initialState();
        T minState = currentState;
        int steps = 0;
        while (true) {
            double temperature = scheduler.getTemperature(steps++);
            if (temperature <= 0.0) {
                return minState;
            }
            T nextState = currentState.step();
            double energyChange = problem.energy(nextState) - problem.energy(currentState);
            if (acceptChange(temperature, energyChange)) {
                currentState = nextState;
                if (problem.energy(currentState) < problem.energy(minState)) {
                    minState = currentState;
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