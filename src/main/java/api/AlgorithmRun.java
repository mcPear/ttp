package api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import problem.TTPGenome;

public interface AlgorithmRun {

    RunResult run();

    @AllArgsConstructor
    @Getter
    class RunResult {
        private final int profit;
        private final int birthsCount;
        private final TTPGenome genome;

    }

}
