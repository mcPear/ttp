import org.junit.Assert;
import org.junit.Test;
import ttp.TTPGenome;

import java.util.List;

public class SelectionTest {

    @Test
    public void nextGenerationSizeIsEqual(){
        List<TTPGenome> initialPopulation = Main.initialPopulation();
        List<TTPGenome> selectedPopulation = Main.select(initialPopulation, 5);
        Assert.assertEquals(initialPopulation.size(), selectedPopulation.size());
        Assert.assertNotEquals(initialPopulation, selectedPopulation);
    }

}
