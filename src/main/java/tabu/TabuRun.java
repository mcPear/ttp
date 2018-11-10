package tabu;

import genetic.Rand;
import problem.ProblemDTO;
import problem.ProblemReader;
import problem.TTPGenome;

import java.util.*;

public class TabuRun {
    private static final ProblemDTO problemDTO = ProblemReader.read();
    private static final Set<TTPGenome> tabu = new HashSet<>();
    private static final int ITERATIONS_COUNT = 900;
    private static final int NO_PROGRESS_ITERATIONS_COUNT = ITERATIONS_COUNT;
    private static final int NEIGHBOURS_COUNT = 150;

    public static void run() {
        genetic.TTPContext.initGreedyPickingPlanStatics(problemDTO);
        Set<TTPGenome> neighbours;
        Integer noProgressIterationsCounter = 0;
        TTPGenome currentBest = TTPGenome.randomInstance(problemDTO.dimension, problemDTO.items, problemDTO.capacity);
        TTPGenome overallBest = currentBest;
        tabu.add(currentBest);

        for (int i = 0; stopCondition(i, noProgressIterationsCounter); i++) {
            neighbours = neighbours(currentBest, NEIGHBOURS_COUNT);
            neighbours.removeAll(tabu);
            currentBest = best(neighbours);
            tabu.add(currentBest); //todo czy warto ograniczyć rozmiar i zrzucać najstarsze ?
            if (isGreater(currentBest, overallBest)) {
                overallBest = currentBest;
                noProgressIterationsCounter = 0;
            } else {
                noProgressIterationsCounter++;
            }
//            logCurrentBest(currentBest);
        }

        logOverallBest(overallBest);
    }

    private static boolean stopCondition(int i, int noProgressIterationsCounter) {
        return i < ITERATIONS_COUNT && noProgressIterationsCounter < NO_PROGRESS_ITERATIONS_COUNT;
    }

    private static boolean isGreater(TTPGenome currentBest, TTPGenome overallBest) {
        return currentBest.evaluate(problemDTO) > overallBest.evaluate(problemDTO);
    }

    private static Set<TTPGenome> neighbours(TTPGenome genome, int neighboursCount) {//definicja sąsiedztwa - dokładnie 2 miasta są zamienione
        Set<TTPGenome> neighbours = new HashSet<>();
        List<Integer> tourCopy = new ArrayList<>(genome.getTour());
        int i, j;
        while (neighbours.size() < neighboursCount) {
            i = 0;
            j = 0;
            while (i == j) {
                try {
                    i = Rand.object.nextInt(tourCopy.size());
                    j = i + 1 + Rand.object.nextInt(tourCopy.size() - i - 1);
                } catch (IllegalArgumentException e) {
                }
            }
            Collections.swap(tourCopy, i, j);
            neighbours.add(new TTPGenome(genome.getPickingPlan(), new ArrayList<>(tourCopy)));
            Collections.swap(tourCopy, i, j);
        }
        return neighbours;
    }

    private static TTPGenome best(Set<TTPGenome> list) {
        return Collections.max(list, TTPGenome.evaluationComparator(problemDTO));
    }

    private static void logCurrentBest(TTPGenome currentBest) {
        System.out.println(currentBest.evaluate(problemDTO).intValue());
    }

    private static void logOverallBest(TTPGenome overallBest) {
        System.out.println("Overall best: " + overallBest.evaluate(problemDTO) + "\n");
    }

}
