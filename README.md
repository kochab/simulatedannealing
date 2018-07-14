# Simulated Annealing

A library for solving optimization problems by simulated annealing, based on [pathfinder's original implementation](https://github.com/csse497/pathfinder-routing).

## Install

Using [jitpack.io](https://jitpack.io/#kochab/simulatedannealing)

```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
<dependency>
  <groupId>com.github.kochab</groupId>
  <artifactId>simulatedannealing</artifactId>
  <version>0.1.0</version>
</dependency>
```

## Usage
You will need to implement the `Minimizable<T>` interface. It defines only two methods, `T next(T state)`, which should return a randomly chosen perturbation of the given state, and `double energy(T state)`, which returns the energy level of the given state.

You will need to choose a `Scheduler` implementation, which determines the speed and shape of the annealing process. We provide two built-in options, `LinearDecayScheduler` and `ExponentialDecayScheduler`.

Finally, just create a `Solver` and iterate over the solution space.

```java
Scheduler scheduler = new LinearDecayScheduler(INITIAL_TEMPERATURE, NUMBER_OF_STEPS);
Minimizable<VRPSearchState> mz = new VehicleRoutingProblem(...);
Solver<VRPSearchState> solver = new Solver(mz, scheduler, INITIAL_STATE);
for (Candidate<VRPSearchState> candidate : solver) {
    if (candidate.isMinimum()) { ... }
    else { ... }
}
```

The last minimum found (`candidate.isMinimum()`) is the global minimum.

## Examples

See [kochab/simulatedannealing-examples](https://github.com/kochab/simulatedannealing-examples) for a simple demo program that uses simulated annealing to solve a graphical optimization problem (minimizing adjacent difference between pixels).

## LICENSE

[MIT](https://raw.githubusercontent.com/kochab/simulatedannealing/master/LICENSE).
