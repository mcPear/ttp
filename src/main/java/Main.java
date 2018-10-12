import problem.ProblemDTO;
import problem.ProblemReader;
import ttp.TTPGenome;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int populationSize = 100;
    private static final ProblemDTO problemDTO = ProblemReader.read();

    public static void main(String... args) {
        List<TTPGenome> initialPopulation = initialPopulation();
        System.out.println(args);
    }

    private static List<TTPGenome> initialPopulation() {
        List<TTPGenome> initialPopulation = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            initialPopulation.add(
                    TTPGenome.randomInstance(
                            problemDTO.getDimension(), problemDTO.getItems(), problemDTO.getCapacity()));
        }
        return initialPopulation;
    }

}
