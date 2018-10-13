package ea;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class GenerationResult {

    public final double best;
    public final double middle;
    public final double worst;

    public GenerationResult(List<Double> evaluationList) {
        Collections.sort(evaluationList);
        best = evaluationList.get(evaluationList.size() - 1);
        middle = evaluationList.get(evaluationList.size() / 2);
        worst = evaluationList.get(0);
    }

    public boolean lowerThan(GenerationResult other) {
        return this.best < other.best;
    }

}
