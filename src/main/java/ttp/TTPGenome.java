package ttp;

import ea.Genome;
import lombok.Getter;
import lombok.Setter;
import problem.ItemDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class TTPGenome implements Genome {

    private List<Integer> pickingPlan; //list of cities, 0 means item is not picked, item 1 is at position 0
    private List<Integer> tour;

    public double evaluate() {//todo
        return 0;
    }

    public int compareTo(Object o) {
        return 0;
    }

    public static TTPGenome randomInstance(int citiesCount, List<ItemDTO> items, int capacity) {
        TTPGenome ttpGenome = new TTPGenome();
        List<Integer> tour = new ArrayList<>();
        for (int i = 1; i < citiesCount; i++) {
            tour.add(i);
        }
        Collections.shuffle(tour);
        ttpGenome.setTour(tour);
        ttpGenome.setPickingPlan(GreedyPickingPlanGenerator.getPickingPlan(items, capacity));
        return ttpGenome;
    }

}
