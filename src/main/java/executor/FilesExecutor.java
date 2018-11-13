package executor;

import api.AlgorithmRun;

import java.util.ArrayList;
import java.util.List;

public class FilesExecutor {

    public static FilesExecutorResult execute(Class<? extends AlgorithmRun> algorithmRunClass, List<String> fileNames) throws Exception {
        List<FileExecutionResult> fileExecutionResults = new ArrayList<>();
        for (String fileName : fileNames) {
            fileExecutionResults.add(new FileExecutionResult(
                    fileName,
                    TenTimesExecutor.execute(
                            algorithmRunClass.getConstructor(String.class).newInstance(fileName))
            ));
            System.out.println("FilesExecutor: done file: " + fileName);
        }
        return new FilesExecutorResult(algorithmRunClass.getSimpleName(), fileExecutionResults);
    }


}
