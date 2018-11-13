package executor;

import java.util.List;

public class FilesExecutorResult {

    private final String className;
    private final List<FileExecutionResult> fileExecutionResults;

    public FilesExecutorResult(String className, List<FileExecutionResult> fileExecutionResults) {
        this.className = className;
        this.fileExecutionResults = fileExecutionResults;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(className + "\n");
        fileExecutionResults.forEach(r -> result.append(r + "\n"));
        return result.toString();
    }

}
