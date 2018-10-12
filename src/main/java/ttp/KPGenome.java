package ttp;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KPGenome {

    private List<Integer> pickingPlan; //list of cities, 0 means item is not picked, item 1 is at position 0

}
