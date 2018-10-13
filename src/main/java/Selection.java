import problem.ProblemDTO;
import ttp.TTPGenome;

import java.util.ArrayList;
import java.util.List;

public class Selection {

    public static List<TTPGenome> select(List<TTPGenome> basePopulation, int tournamentSize, ProblemDTO problemDTO) {
        List<TTPGenome> selectedPopulation = new ArrayList<>();
        while (selectedPopulation.size() < basePopulation.size()) {
            selectedPopulation.add(tournament(basePopulation, tournamentSize, problemDTO));
        }
        return selectedPopulation;
    }

    private static TTPGenome tournament(List<TTPGenome> population, int tournamentSize, ProblemDTO problemDTO) {
        TTPGenome winner = null;
        for (int i = 0; i < tournamentSize; i++) {
            TTPGenome randomGenome = population.get(Rand.object.nextInt(population.size()));
            if (winner == null || winner.evaluate(problemDTO) < randomGenome.evaluate(problemDTO)) {
                winner = randomGenome;
            }
        }
        return winner;
    }

}
