package impl.hybrids;

import api.AlgorithmRun;
import context.TTPContext;
import impl.genetic.GeneticRun;
import impl.tabu.TabuRun;
import problem.ProblemDTO;
import problem.ProblemReader;
import problem.TTPGenome;

import java.util.ArrayList;
import java.util.List;

public class TSinitGA implements AlgorithmRun {

    private final String fileName;

    public TSinitGA(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public RunResult run() {
        ProblemDTO problemDTO = ProblemReader.read(fileName);
        TTPContext ttpContext = new TTPContext(problemDTO);
        int populationSize = 400;
        return new GeneticRun(problemDTO, ttpContext, initialPopulation(problemDTO, ttpContext, populationSize), populationSize).run();
    }

    private List<TTPGenome> initialPopulation(ProblemDTO problemDTO, TTPContext ttpContext, int populationSize) {
        List<TTPGenome> initialPopulation = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            TTPGenome random = TTPGenome.randomInstance(
                    problemDTO.dimension, ttpContext.getGreedyPickingPlan());
            initialPopulation.add(new TabuRun(fileName, random, 50).run().getGenome());

        }
        return initialPopulation;
    }

}
