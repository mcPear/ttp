import genetic.GeneticRun;
import genetic.operation.Crossover;
import genetic.operation.Mutation;
import genetic.operation.Selection;
import org.junit.Assert;
import org.junit.Test;
import problem.ProblemDTO;
import problem.ProblemReader;
import problem.TTPGenome;

import java.io.InputStream;
import java.util.List;

public class SelectionTest {

    @Test
    public void nextGenerationSizeIsEqual() {
        InputStream stream = SelectionTest.class.getResourceAsStream("/ttp_student/trivial_1.ttp");
        ProblemDTO problemDTO = ProblemReader.read(stream);
        List<TTPGenome> initialPopulation = GeneticRun.initialPopulation();
        List<TTPGenome> selectedPopulation = Selection.select(initialPopulation, 5, problemDTO);
        List<TTPGenome> crossedPopulation = Crossover.cross(selectedPopulation, 50);
        List<TTPGenome> mutatedPopulation = Mutation.mutate(crossedPopulation, 1);

        Assert.assertEquals(initialPopulation.size(), mutatedPopulation.size());
        Assert.assertNotEquals(initialPopulation, mutatedPopulation);
    }

}
