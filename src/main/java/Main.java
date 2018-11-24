import api.AlgorithmRun;
import impl.hybrids.GAplusTS;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String... args) throws Exception { //todo dla wszystich leci około minuty po tuningu
        List<String> fileNames = Arrays.asList(
//                "easy_0", "easy_1", "easy_2", "easy_3", "easy_4",
//                "medium_0", "medium_1", "medium_2", "medium_3",
                "medium_4"
//                , "hard_0", "hard_1", "hard_2", "hard_3", "hard_4"
        );
        AlgorithmRun.RunResult runResult = new GAplusTS("medium_4").run();
//        AlgorithmRun.RunResult runResult = new AnnealRun("medium_4").run();
//        AlgorithmRun.RunResult runResult = new TabuRun( "medium_4").run();
//        AlgorithmRun.RunResult runResult = new GeneticRun( "medium_4").run();
//        FilesExecutorResult filesExecutorResultA = FilesExecutor.execute(AnnealRun.class, fileNames);
//        FilesExecutorResult filesExecutorResultT = FilesExecutor.execute(TabuRun.class, fileNames);
//        FilesExecutorResult filesExecutorResultG = FilesExecutor.execute(GeneticRun.class, fileNames);
//        System.out.println(filesExecutorResultA);
//        System.out.println(filesExecutorResultT);
//        System.out.println(filesExecutorResultG);
//        export(filesExecutorResultA.toString(), "AnnealRun");
//        export(filesExecutorResultT.toString(), "TabuRun");
//        export(filesExecutorResultG.toString(), "GeneticRun");
    }

    private static void export(String text, String fileName) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(fileName + new Date() + ".txt")) {
            out.println(text);
        }
    }

}
