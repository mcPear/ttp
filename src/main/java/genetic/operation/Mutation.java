package genetic.operation;

import genetic.Rand;
import problem.TTPGenome;

import java.util.Collections;
import java.util.List;

public class Mutation {

    public static List<TTPGenome> mutate(List<TTPGenome> basePopulation, double mutationChancePercent) {
        for (TTPGenome genome :
                basePopulation) {
            mutate(genome, mutationChancePercent);
            genome.resetEvaluation(); //fixme easy to forget
        }
        return basePopulation;
    }

    private static void mutate(TTPGenome genome, double mutationChancePercent) {
        List<Integer> tour = genome.getTour();
        mutateGenomeByGenes(tour, mutationChancePercent);
    }

    private static void mutateGenomeByGenes(List<Integer> genome, double mutationChancePercent) {
        for (int i = 0; i < genome.size(); i++) {
            if (occurredMutation(mutationChancePercent)) {
                swapGene(genome, i);
            }
        }
    }

    private static void swapGene(List<Integer> genome, int index) {
        int j;
        do {
            j = Rand.object.nextInt(genome.size());
        }
        while (index == j);
        Collections.swap(genome, index, j);
    }

    private static boolean occurredMutation(double mutationChancePercent) {
        return Rand.object.nextInt(Rand.fullChancePercent) < mutationChancePercent;
    }

}
