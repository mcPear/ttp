package impl.genetic.operation;

import context.TTPContext;
import impl.genetic.Rand;
import problem.ProblemDTO;
import problem.TTPGenome;

import java.util.ArrayList;
import java.util.List;

public class Selection {

    public static List<TTPGenome> select(List<TTPGenome> basePopulation, int tournamentSize, ProblemDTO problemDTO, TTPContext ttpContext) {
        List<TTPGenome> selectedPopulation = new ArrayList<>();
        while (selectedPopulation.size() < basePopulation.size()) {
            selectedPopulation.add(tournament(basePopulation, tournamentSize, problemDTO, ttpContext));
        }
        return selectedPopulation;
    }

    private static TTPGenome tournament(List<TTPGenome> population, int tournamentSize, ProblemDTO problemDTO, TTPContext ttpContext) {
        TTPGenome winner = null;
        for (int i = 0; i < tournamentSize; i++) {
            TTPGenome randomGenome = population.get(Rand.object.nextInt(population.size()));
            if (winner == null || winner.evaluate(problemDTO, ttpContext) < randomGenome.evaluate(problemDTO, ttpContext)) {
                winner = randomGenome;
            }
        }
        return winner;
    }

}
