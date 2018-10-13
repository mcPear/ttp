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
    private static final ProblemDTO problemDTO = ProblemReader.read();

    public static void main(String... args) {
        List<TTPGenome> initialPopulation = initialPopulation();
        List<TTPGenome> selectedPopulation = Selection.select(initialPopulation, tournamentSize, problemDTO);
        List<TTPGenome> crossedPopulation = Crossover.cross(selectedPopulation, crossingChancePercent);
        List<TTPGenome> mutatedPopulation = Mutation.mutate(crossedPopulation, mutationChancePercent);
        System.out.println(args);
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
