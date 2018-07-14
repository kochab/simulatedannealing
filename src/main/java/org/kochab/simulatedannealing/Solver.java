package org.kochab.simulatedannealing;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Simulated annealing optimizer.
 *
 * @param <T> Type of the optimization problem's search states.
 */
public class Solver<T> implements Iterable<Candidate<T>> {
    final Minimizable<T> minimizable;
    final Scheduler scheduler;
    final Random random;
    final T initial;

    /**
     * Creates a new optimizer.
     *
     * @param minimizable The minimization problem's representation.
     * @param scheduler The annealing scheduler to use.
     * @param initial The initial state of the problem.
     * @param random The random number source to use for calculating acceptance probabilities.
     */
    public Solver(Minimizable<T> minimizable, Scheduler scheduler, T initial, Random random) {
        this.minimizable = minimizable;
        this.scheduler = scheduler;
        this.initial = initial;
        this.random = random;
    }

    /**
     * Creates a new optimizer.
     *
     * @param minimizable The minimization problem's representation.
     * @param scheduler The annealing scheduler to use.
     * @param initial The initial state of the problem.
     */
    public Solver(Minimizable<T> minimizable, Scheduler scheduler, T initial) {
        this(minimizable, scheduler, initial, new Random());
    }

    class CandidateIterator implements Iterator<Candidate<T>>, Candidate<T> {
        T state = initial;
        T minimum = state;
        long iteration = 0;
        double temperature = scheduler.getTemperature(iteration);
        boolean improved = false;

        @Override
        public long getIteration() {
            return iteration;
        }

        @Override
        public double getTemperature() {
            return temperature;
        }

        @Override
        public T getState() {
            return state;
        }

        @Override
        public boolean isMinimum() {
            return improved;
        }

        @Override
        public boolean hasNext() {
            return temperature > 0;
        }

        @Override
        public Candidate<T> next() {
            if (temperature > 0) {
                final T succ = minimizable.next(state);
                final double dT = minimizable.energy(succ) - minimizable.energy(state);
                if (accept(temperature, dT)) {
                    state = succ;
                    if (minimizable.energy(state) < minimizable.energy(minimum)) {
                        minimum = state;
                        improved = true;
                    } else {
                        improved = false;
                    }
                } else {
                    improved = false;
                }
                ++iteration;
                temperature = scheduler.getTemperature(iteration);
                return this;
            } else {
                throw new NoSuchElementException("Minimum temperature reached: " + temperature);
            }
        }
    }

    @Override
    public Iterator<Candidate<T>> iterator() {
        return new CandidateIterator();
    }

    /** Always accept changes that decrease energy. Otherwise, use the simulated annealing. */
    boolean accept(double t, double dT) {
        if (dT < 0.0) {
            return true;
        } else {
            return random.nextDouble() <= Math.exp(-dT / t);
        }
    }
}
