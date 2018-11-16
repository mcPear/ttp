package context;

import problem.DistanceTable;
import problem.GreedyPickingPlan;
import problem.ProblemDTO;

public class TTPContext {

    private final DistanceTable distanceTable;
    private final GreedyPickingPlan greedyPickingPlan;

    public TTPContext(ProblemDTO problemDTO) {
        this.distanceTable = new DistanceTable(problemDTO.nodes);
        this.greedyPickingPlan = new GreedyPickingPlan(problemDTO);
    }

    public DistanceTable getDistanceTable() {
        return distanceTable;
    }

    public GreedyPickingPlan getGreedyPickingPlan() {
        return greedyPickingPlan;
    }
}
