package problem;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ProblemReader {

    private static final InputStream FILE = ProblemReader.class.getResourceAsStream("/ttp_student/medium_0.ttp");
    private static Scanner sc = new Scanner(FILE);

    public static ProblemDTO read(InputStream stream) {
        sc = new Scanner(stream);
        ProblemDTO problemDTO = new ProblemDTO();
        problemDTO.setDimension(nextInt());
        problemDTO.setItemsCount(nextInt());
        problemDTO.setCapacity(nextInt());
        problemDTO.setMinSpeed(nextDouble());
        problemDTO.setMaxSpeed(nextDouble());
        problemDTO.setRentingRatio(nextDouble());
        problemDTO.setNodes(new ArrayList<>());
        while (!sc.hasNext("ITEMS")) {
            NodeDTO nodeDTO = new NodeDTO();
            nodeDTO.setIndex(nextInt());
            nodeDTO.setX((int) nextDouble());
            nodeDTO.setY((int) nextDouble());
            problemDTO.nodes.add(nodeDTO);
        }
        problemDTO.setItems(new ArrayList<>());
        while (sc.hasNext()) {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setIndex(nextInt());
            itemDTO.setProfit(nextInt());
            itemDTO.setWeight(nextInt());
            itemDTO.setAssignedNode(nextInt());
            problemDTO.items.add(itemDTO);
        }
        return problemDTO;
    }

    public static ProblemDTO read() {
        return read(FILE);
    }

    private static int nextInt() {
        while (!sc.hasNextInt()) {
            sc.next();
        }
        return sc.nextInt();
    }

    private static double nextDouble() {
        while (!sc.hasNextDouble()) {
            sc.next();
        }
        return sc.nextDouble();
    }

}
