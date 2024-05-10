package org.evrete.komet.run;

import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;

public abstract class AbstractTestRunner {


    protected static void runTest(Class<?> benchmark) {
        try {
            TimeValue duration = TimeValue.milliseconds(2000L);
            int iterations = 8;
            Options opt = new OptionsBuilder()
                    .include(benchmark.getSimpleName())
                    .warmupIterations(iterations)
                    .warmupTime(duration)
                    .measurementIterations(iterations)
                    .measurementTime(duration)
                    .mode(Mode.AverageTime)
                    .timeUnit(TimeUnit.SECONDS)
                    .forks(1)
                    .build();

            new Runner(opt).run();
        } catch (RunnerException e) {
            throw new RuntimeException(e);
        }
    }
}
