package org.evrete.komet.benchmarks;

import org.evrete.komet.EvreteKnowledgeBuilder;
import org.evrete.komet.KnowledgeBuilder;
import org.evrete.komet.LegacyKnowledgeBuilder;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.Collection;

public class CreateTest {

    @Benchmark
    public void legacy(BenchmarkState state) {
        state.builderLegacy.build(state.legacyClasses);
    }

    @Benchmark
    public void latest(BenchmarkState state) {
        state.builderNew.build(state.newClasses);
    }

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        @Param({"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
        int scale;

        KnowledgeBuilder builderNew = new EvreteKnowledgeBuilder();
        KnowledgeBuilder builderLegacy = new LegacyKnowledgeBuilder();

        Collection<Class<?>> newClasses = new ArrayList<>();
        Collection<Class<?>> legacyClasses = new ArrayList<>();

        @Setup(Level.Iteration)
        public void initInvocationData() throws Exception {
            this.newClasses.clear();
            this.legacyClasses.clear();
            for (int s = 0; s < scale; s++) {
                for (String className : builderNew.unitClassNames()) {
                    this.newClasses.add(Class.forName(className + s));
                }
                for (String className : builderLegacy.unitClassNames()) {
                    this.legacyClasses.add(Class.forName(className + s));
                }
            }
        }
    }
}

/*

Benchmark          (scale)  Mode  Cnt  Score   Error  Units
CreateTest.latest        1  avgt    8  0.325 ± 0.023   s/op
CreateTest.latest        2  avgt    8  0.647 ± 0.054   s/op
CreateTest.latest        3  avgt    8  1.028 ± 0.185   s/op
CreateTest.latest        4  avgt    8  1.294 ± 0.054   s/op
CreateTest.latest        5  avgt    8  1.584 ± 0.024   s/op
CreateTest.latest        6  avgt    8  1.906 ± 0.117   s/op
CreateTest.latest        7  avgt    8  2.277 ± 0.214   s/op
CreateTest.latest        8  avgt    8  2.616 ± 0.270   s/op
CreateTest.latest        9  avgt    8  2.843 ± 0.090   s/op
CreateTest.latest       10  avgt    8  3.137 ± 0.027   s/op
CreateTest.legacy        1  avgt    8  0.354 ± 0.078   s/op
CreateTest.legacy        2  avgt    8  0.683 ± 0.051   s/op
CreateTest.legacy        3  avgt    8  1.035 ± 0.067   s/op
CreateTest.legacy        4  avgt    8  1.347 ± 0.032   s/op
CreateTest.legacy        5  avgt    8  1.669 ± 0.026   s/op
CreateTest.legacy        6  avgt    8  2.073 ± 0.166   s/op
CreateTest.legacy        7  avgt    8  2.360 ± 0.108   s/op
CreateTest.legacy        8  avgt    8  2.723 ± 0.222   s/op
CreateTest.legacy        9  avgt    8  2.992 ± 0.026   s/op
CreateTest.legacy       10  avgt    8  3.310 ± 0.063   s/op

 */
