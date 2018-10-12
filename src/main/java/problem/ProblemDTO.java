package problem;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProblemDTO {
    private int dimension;
    private int itemsCount;
    private int capacity;
    private double minSpeed;
    private double maxSpeed;
    private double rentingRatio;
    private List<NodeDTO> nodes;
    private List<ItemDTO> items;
}