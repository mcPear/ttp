import ttp.TTPGenome;

import java.util.List;

public class Mutation {

    public static List<TTPGenome> mutate(List<TTPGenome> basePopulation, double mutationChancePercent) {
        return null;
    }

    private static boolean occurredMutation(double mutationChance) {
        return Rand.object.nextInt(Rand.fullChancePercent) < mutationChance;
    }

}
