package executor;

import api.AlgorithmRun;

import java.util.ArrayList;
import java.util.List;

public class FilesExecutor {

    public static FilesExecutorResult execute(Class<AlgorithmRun> algorithmRunClass, List<String> fileNames) throws NoSuchMethodException {
        List<TenTimesExecutorResult> tenTimesExecutorResults = new ArrayList<>();
        for (String fileName : fileNames) {
            tenTimesExecutorResults.add(
                    TenTimesExecutor.execute(
                            algorithmRunClass.getConstructor(String.class).newInstance(fileName)))
        }

    }


}
