package problem;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ProblemReader {

    private static final InputStream FILE = ProblemReader.class.getResourceAsStream("/ttp_student/trivial_0.ttp");
    private static final Scanner sc = new Scanner(FILE);

    public static ProblemDTO read() {
        ProblemDTO problemDTO = new ProblemDTO();
        problemDTO.setDimension(nextInt());
        problemDTO.setItemsCount(nextInt());
        problemDTO.setCapacity(nextInt());
        problemDTO.setMinSpeed(nextDouble());
        problemDTO.setMaxSpeed(nextDouble());
        problemDTO.setRentingRatio(nextDouble());
        problemDTO.setNodes(new ArrayList<NodeDTO>());
        while (!sc.hasNext("ITEMS")) {
            NodeDTO nodeDTO = new NodeDTO();
            nodeDTO.setIndex(nextInt());
            nodeDTO.setX((int) nextDouble());
            nodeDTO.setY((int) nextDouble());
            problemDTO.getNodes().add(nodeDTO);
        }
        problemDTO.setItems(new ArrayList<ItemDTO>());
        while (sc.hasNext()) {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setIndex(nextInt());
            itemDTO.setProfit(nextInt());
            itemDTO.setWeight(nextInt());
            itemDTO.setAssignedNode(nextInt());
            problemDTO.getItems().add(itemDTO);
        }
        return problemDTO;
    }

    private static int nextInt() {
        while (!sc.hasNextInt()) {
            String next = sc.next();
            System.out.println(next);
        }
        int outVal = sc.nextInt();
        System.out.println("OUT: " + outVal);
        return outVal;
    }

    private static double nextDouble() {
        while (!sc.hasNextDouble()) {
            sc.next();
        }
        return sc.nextDouble();
    }

}
