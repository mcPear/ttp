package context;

import problem.DistanceTable;
import problem.GreedyPickingPlanGenerator;
import problem.ProblemDTO;

public class TTPContext {

    public static void initGreedyPickingPlanStatics(ProblemDTO problemDTO) {
        DistanceTable.initDistances(problemDTO.nodes);
        GreedyPickingPlanGenerator.initPickedItemsWeightInNode(problemDTO.nodes, problemDTO.items, problemDTO.capacity);
    }

}
