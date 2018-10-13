import problem.ProblemDTO;
import problem.ProblemReader;
import ttp.TTPGenome;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int populationSize = 100;
    private static final int tournamentSize = 100;
    private static final double crossingChancePercent = 50;
    private static final double mutationChancePercent = 1;
    private static final double generationsCount = 100;
    private static final ProblemDTO problemDTO = ProblemReader.read();

    public static void main(String... args) {
        List<TTPGenome> currentPopulation = initialPopulation();
        for (int i = 0; i < generationsCount; i++) {
            currentPopulation = nextPopulation(currentPopulation);
        }
        System.out.println(args);
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

}
