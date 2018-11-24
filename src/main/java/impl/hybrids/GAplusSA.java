package impl.hybrids;

import api.AlgorithmRun;
import impl.anneal.AnnealRun;
import impl.genetic.GeneticRun;
import problem.TTPGenome;

public class GAplusSA implements AlgorithmRun {

    private final String fileName;

    public GAplusSA(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public RunResult run() {
        TTPGenome gaBest = new GeneticRun(fileName).run().getGenome();
        return new AnnealRun(fileName, gaBest, null).run();
    }
}
