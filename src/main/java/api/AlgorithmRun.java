package api;

public interface AlgorithmRun {

    RunResult run();

    class RunResult {
        private final int profit;
        private final int birthsCount;

        public RunResult(int profit, int birthsCount) {
            this.profit = profit;
            this.birthsCount = birthsCount;
        }

        public int getProfit() {
            return profit;
        }

        public int getBirthsCount() {
            return birthsCount;
        }
    }

}
