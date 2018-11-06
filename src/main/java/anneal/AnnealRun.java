package anneal;

import genetic.Rand;
import problem.ProblemDTO;
import problem.ProblemReader;
import problem.TTPGenome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnnealRun { //proper parameters
    private static final ProblemDTO problemDTO = ProblemReader.read();
    private static final int ITERATIONS_COUNT = 100_000; //fast for 100 k, great results
    private static final double TEMPERATURE = 100; //experimental
    private static final double COOLING_STEP = TEMPERATURE / ITERATIONS_COUNT;

    public static void run() {
        genetic.TTPContext.initGreedyPickingPlanStatics(problemDTO);
        TTPGenome current = TTPGenome.randomInstance(problemDTO.dimension, problemDTO.items, problemDTO.capacity);
        TTPGenome overallBest = current;
        TTPGenome neighbour;
        double temperature = TEMPERATURE;

        for (int i = 0; stopCondition(i); i++) {
            neighbour = getRandomNeighbour(current);
            if (neighbour.evaluate(problemDTO) >= current.evaluate(problemDTO)) {
                current = neighbour;
            } else if (accept(current, neighbour, temperature)) {
                current = neighbour;
            }
            if (isGreater(current, overallBest)) {
                overallBest = current;
            }
            temperature = lowerTemperature(temperature);
            //logCurrent(current);
        }

        logOverallBest(overallBest);
    }

    private static boolean stopCondition(int i) {
        return i < ITERATIONS_COUNT;
    }

    private static boolean isGreater(TTPGenome currentBest, TTPGenome overallBest) {
        return currentBest.evaluate(problemDTO) > overallBest.evaluate(problemDTO);
    }

    private static double lowerTemperature(double temperature) {
        double lower = temperature - COOLING_STEP;
        return lower > 0 ? lower : temperature;
    }

    private static TTPGenome getRandomNeighbour(TTPGenome genome) {//definicja sąsiedztwa - dokładnie 2 miasta są zamienione
        List<Integer> tourCopy = new ArrayList<>(genome.getTour());
        int i = 0, j = 0;
        while (i == j) {
            try {
                i = Rand.object.nextInt(tourCopy.size());
                j = i + 1 + Rand.object.nextInt(tourCopy.size() - i - 1);
//                System.out.println("i: " + i + "  j: " + j);
            } catch (IllegalArgumentException e) {

            }
        }
        Collections.swap(tourCopy, i, j);
        return new TTPGenome(genome.getPickingPlan(), new ArrayList<>(tourCopy));
    }

    private static void logCurrent(TTPGenome currentBest) {
        System.out.println(currentBest.evaluate(problemDTO).intValue());
    }

    private static void logOverallBest(TTPGenome overallBest) {
        System.out.println("Overall best: " + overallBest.evaluate(problemDTO) + "\n");
    }

    private static boolean accept(TTPGenome genome, TTPGenome neighbour, double temperature) {
        double diff = neighbour.getEvaluation() - genome.getEvaluation();
        double div = diff / temperature;
        double probability = Math.exp(div);
        return Rand.object.nextDouble() <= probability;
    }

}
