package executor;

import api.AlgorithmRun;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.List;

public class TenTimesExecutor {

    public static TenTimesExecutorResult execute(AlgorithmRun algorithmRun) {
        List<AlgorithmRun.RunResult> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            results.add(algorithmRun.run());
        }
        DescriptiveStatistics profitStats = new DescriptiveStatistics();
        results.forEach(result -> profitStats.addValue(result.getProfit()));

        DescriptiveStatistics birthsCountStats = new DescriptiveStatistics();
        results.forEach(result -> birthsCountStats.addValue(result.getBirthsCount()));
        return new TenTimesExecutorResult(profitStats.getMean(), profitStats.getStandardDeviation(), birthsCountStats.getMean());
    }

}
