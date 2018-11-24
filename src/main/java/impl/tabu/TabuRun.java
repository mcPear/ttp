package impl.tabu;

import api.AlgorithmRun;
import context.TTPContext;
import impl.genetic.Rand;
import problem.ProblemDTO;
import problem.ProblemReader;
import problem.TTPGenome;

import java.util.*;

public class TabuRun implements AlgorithmRun {
    private final ProblemDTO problemDTO;
    private Set<TTPGenome> tabu;
    private int ITERATIONS_COUNT = 1500;
    private static final int NO_PROGRESS_ITERATIONS_COUNT = 9999999;
    private static final int NEIGHBOURS_COUNT = 300;
    private final TTPContext ttpContext;
    private final TTPGenome initialGenome;

    public TabuRun(String fileName) {
        this(fileName, null, null);
    }

    public TabuRun(String fileName, TTPGenome initialGenome, Integer iterationsCount) {
        this.problemDTO = ProblemReader.read(fileName);
        this.initialGenome = initialGenome;
        ITERATIONS_COUNT = iterationsCount != null ? iterationsCount : ITERATIONS_COUNT;
        ttpContext = new TTPContext(problemDTO);
    }

    public RunResult run() {
        Set<TTPGenome> neighbours;
        Integer noProgressIterationsCounter = 0;
        TTPGenome currentBest = initialGenome != null ?
                initialGenome : TTPGenome.randomInstance(problemDTO.dimension, ttpContext.getGreedyPickingPlan());
        TTPGenome overallBest = currentBest;
        this.tabu = new HashSet<>();
        final Set<TTPGenome> births = new HashSet<>();
        tabu.add(currentBest);

        for (int i = 0; stopCondition(i, noProgressIterationsCounter); i++) {
            neighbours = neighbours(currentBest, NEIGHBOURS_COUNT);
            births.addAll(neighbours);
            neighbours.removeAll(tabu);
            currentBest = best(neighbours);
            tabu.add(currentBest); //todo czy warto ograniczyć rozmiar i zrzucać najstarsze ?
            if (isGreater(currentBest, overallBest)) {
                overallBest = currentBest;
                noProgressIterationsCounter = 0;
            } else {
                noProgressIterationsCounter++;
            }
            logCurrentBest(currentBest);
        }

        logOverallBest(overallBest);
        return new RunResult(overallBest.evaluate(problemDTO, ttpContext).intValue(), births.size(), overallBest);
    }

    private boolean stopCondition(int i, int noProgressIterationsCounter) {
        return i < ITERATIONS_COUNT && noProgressIterationsCounter < NO_PROGRESS_ITERATIONS_COUNT;
    }

    private boolean isGreater(TTPGenome currentBest, TTPGenome overallBest) {
        return currentBest.evaluate(problemDTO, ttpContext) > overallBest.evaluate(problemDTO, ttpContext);
    }

    private Set<TTPGenome> neighbours(TTPGenome genome, int neighboursCount) {//definicja sąsiedztwa - dokładnie 2 miasta są zamienione
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

    private TTPGenome best(Set<TTPGenome> list) {
        return Collections.max(list, TTPGenome.evaluationComparator(problemDTO, ttpContext));
    }

    private void logCurrentBest(TTPGenome currentBest) {
        System.out.println(currentBest.evaluate(problemDTO, ttpContext).intValue());
    }

    private void logOverallBest(TTPGenome overallBest) {
        System.out.println("Overall best: " + overallBest.evaluate(problemDTO, ttpContext) + "\n");
    }

}
