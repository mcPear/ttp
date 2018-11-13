package executor;

public class TenTimesExecutorResult {
    private final double avgProfit;
    private final double standardDeviation;
    private final double avgBirthsCount;

    public TenTimesExecutorResult(double avgProfit, double standardDeviation, double avgBirthsCount) {
        this.avgProfit = avgProfit;
        this.standardDeviation = standardDeviation;
        this.avgBirthsCount = avgBirthsCount;
    }

    public double getAvgProfit() {
        return avgProfit;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public double getAvgBirthsCount() {
        return avgBirthsCount;
    }

    @Override
    public String toString() {
        return " " +
                "avg=" + avgProfit +
                ", dev=" + standardDeviation +
                ", avgBirths=" + avgBirthsCount;
    }
}
