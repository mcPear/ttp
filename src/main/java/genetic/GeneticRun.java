package genetic;

import genetic.operation.Crossover;
import genetic.operation.Mutation;
import genetic.operation.Selection;
import problem.ProblemDTO;
import problem.ProblemReader;
import problem.TTPGenome;

import java.util.ArrayList;
import java.util.List;

public class GeneticRun {

    private static final int populationSize = 400;
    private static final int tournamentSize = 6;
    private static final double crossingChancePercent = 70;
    private static final double mutationChancePercent = 4;
    private static final double generationsCount = 90;
    private static final ProblemDTO problemDTO = ProblemReader.read();

    public static void run() {
        genetic.TTPContext.initGreedyPickingPlanStatics(problemDTO);
        OverallResult overallResult = new OverallResult();
        GenerationResult bestResult = null;
        List<TTPGenome> currentPopulation = initialPopulation();
        for (int i = 0; i < generationsCount; i++) {
            GenerationResult generationResult = generationResult(currentPopulation, problemDTO);
            overallResult.add(generationResult);
            if (bestResult == null || bestResult.lowerThan(generationResult)) {
                bestResult = generationResult;
            }
            currentPopulation = nextPopulation(currentPopulation);
            System.out.println("Gen no. " + (i + 1));
        }
        System.out.println(overallResult.toCsvString());
        System.out.println("The best profit: " + bestResult.best);
        System.out.println("The best tour: " + bestResult.bestGenome);
    }

    private static List<TTPGenome> nextPopulation(List<TTPGenome> basePopulation) {
        List<TTPGenome> selectedPopulation = Selection.select(basePopulation, tournamentSize, problemDTO);
        List<TTPGenome> crossedPopulation = Crossover.cross(selectedPopulation, crossingChancePercent);
        return Mutation.mutate(crossedPopulation, mutationChancePercent);
    }

    public static List<TTPGenome> initialPopulation() {
        List<TTPGenome> initialPopulation = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            initialPopulation.add(
                    TTPGenome.randomInstance(
                            problemDTO.dimension, problemDTO.items, problemDTO.capacity));
        }
        return initialPopulation;
    }

    private static GenerationResult generationResult(List<TTPGenome> generation, ProblemDTO problemDTO) {
        return new GenerationResult(generation, problemDTO);
    }

}
