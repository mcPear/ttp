import ea.Crossover;
import ea.Mutation;
import ea.Selection;
import org.junit.Assert;
import org.junit.Test;
import problem.ProblemDTO;
import problem.ProblemReader;
import ttp.TTPGenome;

import java.io.InputStream;
import java.util.List;

public class SelectionTest {

    @Test
    public void nextGenerationSizeIsEqual() {
        InputStream stream = SelectionTest.class.getResourceAsStream("/ttp_student/trivial_1.ttp");
        ProblemDTO problemDTO = ProblemReader.read(stream);
        List<TTPGenome> initialPopulation = Main.initialPopulation();
        List<TTPGenome> selectedPopulation = Selection.select(initialPopulation, 5, problemDTO);
        List<TTPGenome> crossedPopulation = Crossover.cross(selectedPopulation, 50);
        List<TTPGenome> mutatedPopulation = Mutation.mutate(crossedPopulation, 1);

        Assert.assertEquals(initialPopulation.size(), mutatedPopulation.size());
        Assert.assertNotEquals(initialPopulation, mutatedPopulation);
    }

}
