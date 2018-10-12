import problem.ProblemDTO;
import problem.ProblemReader;

public class Main {

    public static void main(String... args) {
//        ProblemReader.all();
        ProblemDTO problemDTO = ProblemReader.read();
        System.out.println(args);
    }

}
