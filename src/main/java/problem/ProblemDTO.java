package problem;

import lombok.Setter;

import java.util.List;

@Setter
public class ProblemDTO {
    public int dimension;
    public int itemsCount;
    public int capacity;
    public double minSpeed;
    public double maxSpeed;
    public double rentingRatio;
    public List<NodeDTO> nodes;
    public List<ItemDTO> items;
}