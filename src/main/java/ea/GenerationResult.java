package ea;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class GenerationResult {

    public final int best;
    public final int middle;
    public final int worst;

    public GenerationResult(List<Double> evaluationList) {
        Collections.sort(evaluationList);
        best = evaluationList.get(evaluationList.size() - 1).intValue();
        middle = evaluationList.get(evaluationList.size() / 2).intValue();
        worst = evaluationList.get(0).intValue();
    }

    public boolean lowerThan(GenerationResult other) {
        return this.best < other.best;
    }

}
