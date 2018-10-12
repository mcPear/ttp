import problem.ProblemDTO;
import problem.ProblemReader;
import ttp.TTPGenome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static final int populationSize = 100;
    private static final int tournamentSize = 100;
    private static final ProblemDTO problemDTO = ProblemReader.read();

    public static void main(String... args) {
        List<TTPGenome> initialPopulation = initialPopulation();
        List<TTPGenome> selectedPopulation = select(initialPopulation, tournamentSize, problemDTO);
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

    public static List<TTPGenome> select(List<TTPGenome> basePopulation, int tournamentSize, ProblemDTO problemDTO) {
        List<TTPGenome> selectedPopulation = new ArrayList<>();
        while (selectedPopulation.size() < basePopulation.size()) {
            selectedPopulation.add(tournament(basePopulation, tournamentSize, problemDTO));
        }
        return selectedPopulation;
    }

    public static TTPGenome tournament(List<TTPGenome> population, int tournamentSize, ProblemDTO problemDTO) {
        TTPGenome winner = null;
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < tournamentSize; i++) {
            TTPGenome randomGenome = population.get(random.nextInt(population.size()));
            if (winner == null || winner.evaluate(problemDTO) < randomGenome.evaluate(problemDTO)) {
                winner = randomGenome;
            }
        }
        return winner;
    }

}
