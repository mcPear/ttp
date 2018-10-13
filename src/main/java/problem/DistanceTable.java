package problem;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.List;

public class DistanceTable {

    public static Table<Integer, Integer, Double> distances = HashBasedTable.create();

    public static double get(int from, int to) {
        return distances.get(from, to);
    }

    public static void initDistances(List<NodeDTO> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                distances.put(i, j, distance(i + 1, j + 1, nodes));
            }
        }
    }

    private static double distance(int from, int to, List<NodeDTO> nodes) {
        NodeDTO fromNode = nodes.stream().filter(node -> node.getIndex() == from).findFirst().get();
        NodeDTO toNode = nodes.stream().filter(node -> node.getIndex() == to).findFirst().get();
        return Math.sqrt(Math.pow(fromNode.getX() - toNode.getX(), 2) + Math.pow(fromNode.getY() - toNode.getY(), 2));
    }
}
