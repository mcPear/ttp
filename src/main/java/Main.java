import api.AlgorithmRun;
import executor.FilesExecutor;
import executor.FilesExecutorResult;
import impl.genetic.GeneticRun;
import impl.hybrids.GAplusSA;
import impl.hybrids.GAplusTS;
import impl.hybrids.SAinitGA;
import impl.hybrids.TSinitGA;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String... args) throws Exception {
        String fileName = "medium_4";
        List<String> fileNames = Arrays.asList(
                "easy_0", "easy_1", "easy_2", "easy_3", "easy_4",
                "medium_0", "medium_1", "medium_2", "medium_3", "medium_4"
        );

        //fixme zmieniłem liczbę iteracji dla tych inicjalizujących i też zmieniłem GA wtedy turniej na większy, bo wszystko psuł
        //single runs
        AlgorithmRun.RunResult runResultTSIGA = new TSinitGA(fileName).run();
        AlgorithmRun.RunResult runResultSAIGA = new SAinitGA(fileName).run();
        AlgorithmRun.RunResult runResultGAPSA = new GAplusSA(fileName).run();
        AlgorithmRun.RunResult runResultGAPTS = new GAplusTS(fileName).run();
        AlgorithmRun.RunResult runResultGARandom = new GeneticRun(fileName).run();

        //10x runs //todo disable logging before running
        FilesExecutorResult filesExecutorResultTSIGA = FilesExecutor.execute(TSinitGA.class, fileNames);
        FilesExecutorResult filesExecutorResultSAIGA = FilesExecutor.execute(SAinitGA.class, fileNames);
        FilesExecutorResult filesExecutorResultGAPSA = FilesExecutor.execute(GAplusSA.class, fileNames);
        FilesExecutorResult filesExecutorResultGAPTS = FilesExecutor.execute(GAplusTS.class, fileNames);
        FilesExecutorResult filesExecutorResultGARandom = FilesExecutor.execute(GeneticRun.class, fileNames);

        System.out.println(filesExecutorResultTSIGA);
        System.out.println(filesExecutorResultSAIGA);
        System.out.println(filesExecutorResultGAPSA);
        System.out.println(filesExecutorResultGAPTS);
        System.out.println(filesExecutorResultGARandom);
    }

    private static void export(String text, String fileName) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(fileName + new Date() + ".txt")) {
            out.println(text);
        }
    }

}
