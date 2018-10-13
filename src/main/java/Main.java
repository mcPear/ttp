import ea.*;
import problem.DistanceTable;
import problem.ProblemDTO;
import problem.ProblemReader;
import ttp.GreedyPickingPlanGenerator;
import ttp.TTPGenome;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final int populationSize = 3000;
    private static final int tournamentSize = 7;
    private static final double crossingChancePercent = 55;
    private static final double mutationChancePercent = 6;
    private static final double generationsCount = 200;
    private static final ProblemDTO problemDTO = ProblemReader.read();

    public static void main(String... args) {
        DistanceTable.initDistances(problemDTO.nodes);
        GreedyPickingPlanGenerator.initPickedItemsWeightInNode(problemDTO.nodes, problemDTO.items, problemDTO.capacity);
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
        System.out.println("The best: " + bestResult.best);
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
        return new GenerationResult(generation.stream().map(genome -> genome.evaluate(problemDTO)).collect(Collectors.toList()));
    }

}
