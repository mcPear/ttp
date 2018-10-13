import ea.*;
import problem.ProblemDTO;
import problem.ProblemReader;
import ttp.TTPGenome;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final int populationSize = 1000;
    private static final int tournamentSize = 5;
    private static final double crossingChancePercent = 80;
    private static final double mutationChancePercent = 5;
    private static final double generationsCount = 100;
    private static final ProblemDTO problemDTO = ProblemReader.read();

    public static void main(String... args) {
        OverallResult overallResult = new OverallResult();
        List<TTPGenome> currentPopulation = initialPopulation();
        for (int i = 0; i < generationsCount; i++) {
            overallResult.add(generationResult(currentPopulation, problemDTO));
            currentPopulation = nextPopulation(currentPopulation);
        }
        System.out.println(overallResult.toCsvString());
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
