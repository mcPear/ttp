package impl.genetic;

import api.AlgorithmRun;
import context.TTPContext;
import impl.genetic.operation.Crossover;
import impl.genetic.operation.Mutation;
import impl.genetic.operation.Selection;
import problem.ProblemDTO;
import problem.ProblemReader;
import problem.TTPGenome;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GeneticRun implements AlgorithmRun {

    private static final int populationSize = 400;
    private static final int tournamentSize = 6;
    private static final double crossingChancePercent = 70;
    private static final double mutationChancePercent = 4;
    private static final double generationsCount = 200;
    private final ProblemDTO problemDTO;
    private Set<TTPGenome> births;
    private final TTPContext ttpContext;

    public GeneticRun(String fileName) {
        this.problemDTO = ProblemReader.read(fileName);
        ttpContext = new TTPContext(problemDTO);
    }

    public RunResult run() {
        this.births = new HashSet<>();
        OverallResult overallResult = new OverallResult();
        GenerationResult bestResult = null;
        List<TTPGenome> currentPopulation = initialPopulation();
        for (int i = 0; i < generationsCount; i++) {
            GenerationResult generationResult = generationResult(currentPopulation, problemDTO);
            overallResult.add(generationResult);
            if (bestResult == null || bestResult.lowerThan(generationResult)) {
                bestResult = generationResult;
            }
            births.addAll(currentPopulation);
            currentPopulation = nextPopulation(currentPopulation);
//            System.out.println("Gen no. " + (i + 1));
            System.out.println(generationResult.best + " " + generationResult.middle + " " + generationResult.worst);
        }
//        System.out.println(overallResult.toCsvString());
        System.out.println("The best profit: " + bestResult.best);
//        System.out.println("The best tour: " + bestResult.bestGenome);
        return new RunResult(bestResult.best, births.size());
    }

    private List<TTPGenome> nextPopulation(List<TTPGenome> basePopulation) {
        List<TTPGenome> selectedPopulation = Selection.select(basePopulation, tournamentSize, problemDTO, ttpContext);
        List<TTPGenome> crossedPopulation = Crossover.cross(selectedPopulation, crossingChancePercent);
        return Mutation.mutate(crossedPopulation, mutationChancePercent);
    }

    public List<TTPGenome> initialPopulation() {
        List<TTPGenome> initialPopulation = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            initialPopulation.add(
                    TTPGenome.randomInstance(
                            problemDTO.dimension, ttpContext.getGreedyPickingPlan()));
        }
        return initialPopulation;
    }

    private GenerationResult generationResult(List<TTPGenome> generation, ProblemDTO problemDTO) {
        return new GenerationResult(generation, problemDTO, ttpContext);
    }

}
