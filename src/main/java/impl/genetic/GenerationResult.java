package impl.genetic;

import lombok.Getter;
import problem.ProblemDTO;
import problem.TTPGenome;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GenerationResult {

    public final int best;
    public final int middle;
    public final int worst;
    public final TTPGenome bestGenome;

    public GenerationResult(List<TTPGenome> generation, ProblemDTO problemDTO) {
        List<Double> evaluationList = generation.stream().map(genome -> genome.evaluate(problemDTO)).collect(Collectors.toList());
        Collections.sort(evaluationList);
        best = evaluationList.get(evaluationList.size() - 1).intValue();
        middle = evaluationList.get(evaluationList.size() / 2).intValue();
        worst = evaluationList.get(0).intValue();
        bestGenome = generation.stream().filter(genome -> genome.evaluate(problemDTO).intValue() == best).findFirst().get();
    }

    public boolean lowerThan(GenerationResult other) {
        return this.best < other.best;
    }

}
