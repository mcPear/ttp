package problem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO implements Comparable<ItemDTO> {//todo czy lepiej mieć miasto przy itemie, czy itemy przy mieście ?
    private int index; //not really used
    private int profit;
    private int weight;
    private int assignedNode;

    public ItemDTO copy() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setIndex(index);
        itemDTO.setProfit(profit);
        itemDTO.setWeight(weight);
        itemDTO.setAssignedNode(assignedNode);
        return itemDTO;
    }

    @Override
    public int compareTo(ItemDTO o) {
        return Integer.compare(profit, o.profit);
    }
}
