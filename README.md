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
  <version>0.0.15</version>
</dependency>
```

## Usage
You will need to implement the `SearchState<T>` interface. It defines only one method, `T step()`, which should return a randomly chosen perturbation of the current state. Also, you will need to implement the `Problem<T>` interface, which initializes a state and evaluates the energey of states.

You will need to choose a `Scheduler` implementation, which determines the speed and shape of the annealing process. We provide two built-in options, `LinearDecayScheduler` and `ExponentialDecayScheduler`.

Finally, just create a `Solver` and call solve!

```java
Scheduler scheduler = new LinearDecayScheduler(INITIAL_TEMPERATURE, NUMBER_OF_STEPS);
Problem<VRPSearchState> problem = new VehicleRoutingProblem(...);
Solver<VRPSearchState> solver = new Solver(problem, scheduler);
VRPSearchState solution = solver.solve();
```

## Examples

See [kochab/simulatedannealing-examples](https://github.com/kochab/simulatedannealing-examples) for a simple demo program that uses simulated annealing to solve a graphical optimization problem (minimizing adjacent difference between pixels).

## LICENSE

[MIT](https://raw.githubusercontent.com/kochab/simulatedannealing/master/LICENSE).
