package impl.anneal;

import api.AlgorithmRun;
import context.TTPContext;
import impl.genetic.Rand;
import problem.ProblemDTO;
import problem.ProblemReader;
import problem.TTPGenome;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class AnnealRun implements AlgorithmRun {
    private final ProblemDTO problemDTO;
    private static final int ITERATIONS_COUNT = 25_000; //fast for 100 k, great results
    private static final double TEMPERATURE = 100; //experimental
    private static final double COOLING_STEP = TEMPERATURE / ITERATIONS_COUNT;
    private final TTPContext ttpContext;
    private List<Integer> results;

    public AnnealRun(String fileName) {
        this.problemDTO = ProblemReader.read(fileName);
        ttpContext = new TTPContext(problemDTO);
    }

    public RunResult run() {
        TTPGenome current = TTPGenome.randomInstance(problemDTO.dimension, ttpContext.getGreedyPickingPlan());
        TTPGenome overallBest = current;
        TTPGenome neighbour;
        final Set<TTPGenome> births = new HashSet<>();
        results = new ArrayList<>();
        double temperature = TEMPERATURE;

        for (int i = 0; stopCondition(i); i++) {
            neighbour = getRandomNeighbour(current);
            births.add(neighbour);
            if (neighbour.evaluate(problemDTO, ttpContext) >= current.evaluate(problemDTO, ttpContext)) {
                current = neighbour;
            } else if (accept(current, neighbour, temperature)) {
                current = neighbour;
            }
            if (isGreater(current, overallBest)) {
                overallBest = current;
            }
            temperature = lowerTemperature(temperature);
            results.add(current.evaluate(problemDTO, ttpContext).intValue());
//            logCurrent(current);
        }

        export(results);
        logOverallBest(overallBest);
        return new RunResult(overallBest.evaluate(problemDTO, ttpContext).intValue(), births.size());
    }

    private static void export(List<Integer> results) {
        try (PrintWriter out = new PrintWriter("anneal result " + new Date() + ".txt")) {
            results.forEach(out::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean stopCondition(int i) {
        return i < ITERATIONS_COUNT;
    }

    private boolean isGreater(TTPGenome currentBest, TTPGenome overallBest) {
        return currentBest.evaluate(problemDTO, ttpContext) > overallBest.evaluate(problemDTO, ttpContext);
    }

    private double lowerTemperature(double temperature) {
        double lower = temperature - COOLING_STEP;
        return lower > 0 ? lower : temperature;
    }

    private TTPGenome getRandomNeighbour(TTPGenome genome) {//definicja sąsiedztwa - dokładnie 2 miasta są zamienione
        List<Integer> tourCopy = new ArrayList<>(genome.getTour());
        int i = 0, j = 0;
        while (i == j) {
            try {
                i = Rand.object.nextInt(tourCopy.size());
                j = i + 1 + Rand.object.nextInt(tourCopy.size() - i - 1);
            } catch (IllegalArgumentException e) {

            }
        }
        Collections.swap(tourCopy, i, j);
        return new TTPGenome(genome.getPickingPlan(), new ArrayList<>(tourCopy));
    }

    private void logCurrent(TTPGenome currentBest) {
        System.out.println(currentBest.evaluate(problemDTO, ttpContext).intValue());
    }

    private void logOverallBest(TTPGenome overallBest) {
        System.out.println("Overall best: " + overallBest.evaluate(problemDTO, ttpContext) + "\n");
    }

    private boolean accept(TTPGenome genome, TTPGenome neighbour, double temperature) {
        double diff = neighbour.getEvaluation() - genome.getEvaluation();
        double div = diff / temperature;
        double probability = Math.exp(div);
        return Rand.object.nextDouble() <= probability;
    }

}
