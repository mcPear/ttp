import executor.TenTimesExecutor;
import executor.TenTimesExecutorResult;
import impl.anneal.AnnealRun;

public class Main {

    public static void main(String... args) {
        String fileName = "medium_0";
        TenTimesExecutorResult tenTimesExecutorResult = TenTimesExecutor.execute(new AnnealRun(fileName));
        System.out.println(tenTimesExecutorResult);
    }

}
