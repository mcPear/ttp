package impl.hybrids;

import api.AlgorithmRun;
import impl.genetic.GeneticRun;
import impl.tabu.TabuRun;
import problem.TTPGenome;

public class GAplusTS implements AlgorithmRun {

    private final String fileName;

    public GAplusTS(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public RunResult run() {
        TTPGenome gaBest = new GeneticRun(fileName).run().getGenome();
        return new TabuRun(fileName, gaBest, null).run();
    }

}
