package tabu;

import problem.ProblemDTO;
import problem.ProblemReader;
import problem.TTPGenome;

import java.util.*;

public class TabuRun {
    private static final ProblemDTO problemDTO = ProblemReader.read();
    private static final Set<TTPGenome> tabu = new HashSet<>();
    private static final int ITERATIONS_COUNT = 10000;
    private static final int NO_PROGRESS_ITERATIONS_COUNT = 300;

    public static void run() {
        genetic.TTPContext.initGreedyPickingPlanStatics(problemDTO);
        Set<TTPGenome> neighbours;
        int noProgressIterationsCounter = 0;
        TTPGenome currentBest = TTPGenome.randomInstance(problemDTO.dimension, problemDTO.items, problemDTO.capacity);
        TTPGenome overallBest = currentBest;
        tabu.add(currentBest);
        for (int i = 0; stopCondition(i, noProgressIterationsCounter); i++) {
            neighbours = neighbours(currentBest);
            neighbours.removeAll(tabu);
            currentBest = best(neighbours);
            tabu.add(currentBest);
            if (currentBest.evaluate(problemDTO) > overallBest.evaluate(problemDTO)) {
                overallBest = currentBest;
                noProgressIterationsCounter = 0;
            } else {
                noProgressIterationsCounter++;
            }
            System.out.println(currentBest.evaluate(problemDTO).intValue());
        }
        System.out.println("Overall best: " + overallBest.evaluate(problemDTO) + "\n");
    }

    private static boolean stopCondition(int i, int noProgressIterationsCounter) {
        return i < ITERATIONS_COUNT && noProgressIterationsCounter < NO_PROGRESS_ITERATIONS_COUNT;
    }

    private static Set<TTPGenome> neighbours(TTPGenome genome) {//definicja sąsiedztwa - dokładnie 2 miasta są zamienione
        Set<TTPGenome> neighbours = new HashSet<>();
        List<Integer> tourCopy = new ArrayList<>(genome.getTour());
        for (int i = 0; i < tourCopy.size(); i++) {
            for (int j = i + 1; j < tourCopy.size(); j++) {
                if (i != j) {
                    Collections.swap(tourCopy, i, j);
                    neighbours.add(new TTPGenome(genome.getPickingPlan(), new ArrayList<>(tourCopy)));
                    Collections.swap(tourCopy, i, j);
                }
            }
        }
        return neighbours;
    }

    private static TTPGenome best(Set<TTPGenome> list) {
        return Collections.max(list, TTPGenome.evaluationComparator(problemDTO));
    }

}
