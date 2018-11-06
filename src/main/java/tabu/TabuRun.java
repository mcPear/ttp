package tabu;

import problem.ProblemDTO;
import problem.ProblemReader;
import problem.TTPGenome;

import java.util.*;

public class TabuRun {//todo dostrój, żeby leciał około 10 sekund, dostrój neighbours_count, weź funkcję losowego osobnika z anneal
    private static final ProblemDTO problemDTO = ProblemReader.read();
    private static final Set<TTPGenome> tabu = new HashSet<>();
    private static final int ITERATIONS_COUNT = 10000;
    private static final int NO_PROGRESS_ITERATIONS_COUNT = 1000;
    private static final int NEIGHBOURS_COUNT = 300;

    public static void run() {
        genetic.TTPContext.initGreedyPickingPlanStatics(problemDTO);
        Set<TTPGenome> neighbours;
        Integer noProgressIterationsCounter = 0;
        TTPGenome currentBest = TTPGenome.randomInstance(problemDTO.dimension, problemDTO.items, problemDTO.capacity);
        TTPGenome overallBest = currentBest;
        tabu.add(currentBest);

        for (int i = 0; stopCondition(i, noProgressIterationsCounter); i++) {
            neighbours = neighbours(currentBest); //todo tylko k sąsiadów
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

    private static void logCurrentBest(TTPGenome currentBest) {
        System.out.println(currentBest.evaluate(problemDTO).intValue());
    }

    private static void logOverallBest(TTPGenome overallBest) {
        System.out.println("Overall best: " + overallBest.evaluate(problemDTO) + "\n");
    }

}
