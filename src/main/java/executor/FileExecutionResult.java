package executor;

public class FileExecutionResult {

    private final String fileName;
    private final TenTimesExecutorResult tenTimesExecutorResult;

    public FileExecutionResult(String fileName, TenTimesExecutorResult tenTimesExecutorResult) {
        this.fileName = fileName;
        this.tenTimesExecutorResult = tenTimesExecutorResult;
    }

    @Override
    public String toString() {
        return "fileName='" + fileName + '\'' + ", " + tenTimesExecutorResult;
    }
}
