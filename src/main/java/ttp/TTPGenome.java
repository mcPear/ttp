package ttp;

import lombok.Getter;
import lombok.Setter;
import problem.DistanceTable;
import problem.ItemDTO;
import problem.ProblemDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class TTPGenome {

    private final List<Integer> pickingPlan; //list of cities, 0 means item is not picked, item 1 is at position 0
    private final List<Integer> tour;
    private Double evaluation;

    public TTPGenome(List<Integer> pickingPlan, List<Integer> tour) {
        this.pickingPlan = pickingPlan;
        this.tour = tour;
    }

    public double evaluate(ProblemDTO data) {
        if (evaluation == null) {
            evaluation = profitSum(data.items) - data.rentingRatio * totalTime(data.maxSpeed, data.minSpeed, data.capacity);
        }
        return evaluation;
    }

    public void resetEvaluation() {
        evaluation = null;
    }

    private int profitSum(List<ItemDTO> items) {
        int sum = 0;
        for (int i = 0; i < pickingPlan.size(); i++) {
            if (pickingPlan.get(i) != 0) {
                sum += items.get(i).getProfit();
            }
        }
        return sum;
    }

    private double totalTime(double maxSpeed, double minSpeed, int capacity) {
        double time = 0;
        int currentKnapsackWeight = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            int from = tour.get(i);
            int to = tour.get(i + 1);
            double distance = distance(from, to);
            currentKnapsackWeight += pickedItemsWeight(from);
            time += distance * velocity(maxSpeed, minSpeed, capacity, currentKnapsackWeight);
        }
        return time;
    }

    private int pickedItemsWeight(int node) {
        return GreedyPickingPlanGenerator.pickedItemsWeightInNode.get(node);
    }

    private double velocity(double maxSpeed, double minSpeed, int maxKnapsackCapacity, int currentKnapsackWeight) {
        return maxSpeed -
                currentKnapsackWeight * (maxSpeed - minSpeed) / maxKnapsackCapacity;
    }

    private double distance(int from, int to) {
        return DistanceTable.get(from, to);
    }

    public static TTPGenome randomInstance(int citiesCount, List<ItemDTO> items, int capacity) {
        List<Integer> tour = new ArrayList<>();
        for (int i = 1; i < citiesCount; i++) {
            tour.add(i);
        }
        Collections.shuffle(tour);
        List<Integer> pickingPlan = GreedyPickingPlanGenerator.getPickingPlan(items, capacity);
        return new TTPGenome(pickingPlan, tour);
    }

}
