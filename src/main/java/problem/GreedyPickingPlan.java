package problem;

import java.util.*;
import java.util.stream.Collectors;

public class GreedyPickingPlan {

    private final List<Integer> pickingPlan;
    private final List<ItemDTO> pickedItems;
    private final Map<Integer, Integer> pickedItemsWeightInNode;

    public GreedyPickingPlan(ProblemDTO problemDTO) {
        this.pickingPlan = getPickingPlan(problemDTO);
        this.pickedItems = getPickedItems(problemDTO, pickingPlan);
        this.pickedItemsWeightInNode = getPickedItemsWeightInNode(problemDTO);
    }

    private List<Integer> getPickingPlan(ProblemDTO problemDTO) {
        List<ItemDTO> items = problemDTO.items;
        int capacity = problemDTO.capacity;

        List<Integer> pickingPlan = new ArrayList<>(Collections.nCopies(items.size(), 0));
        List<ItemDTO> itemsCopy = new ArrayList<>(items);
        itemsCopy = itemsCopy.stream().map(ItemDTO::copy).collect(Collectors.toList());

        while (!itemsCopy.isEmpty()) {
            ItemDTO maxValueItem = Collections.max(itemsCopy);
            if (isUnderCapacity(pickingPlan, items, maxValueItem, capacity)) {
                pickingPlan.set(maxValueItem.getIndex() - 1, maxValueItem.getAssignedNode());
            }
            itemsCopy.remove(maxValueItem);
        }
        return pickingPlan;
    }

    private boolean isUnderCapacity(List<Integer> pickingPlan, List<ItemDTO> items, ItemDTO itemToAdd, int capacity) {
        int totalWeight = 0;
        for (int i = 0; i < pickingPlan.size(); i++) {
            if (pickingPlan.get(i) != 0) {
                totalWeight += items.get(i).getWeight();
            }
        }
        return totalWeight + itemToAdd.getWeight() < capacity;
    }

    private static List<ItemDTO> getPickedItems(ProblemDTO problemDTO, List<Integer> pickingPlan) {
        List<ItemDTO> pickedItems = new ArrayList<>();
        for (int i = 0; i < pickingPlan.size(); i++) {
            if (pickingPlan.get(i) != 0) {
                pickedItems.add(problemDTO.items.get(i));
            }
        }
        return pickedItems;
    }

    private Map<Integer, Integer> getPickedItemsWeightInNode(ProblemDTO problemDTO) {
        List<NodeDTO> nodes = problemDTO.nodes;
        Map<Integer, Integer> pickedItemsWeightInNode = new HashMap<>();
        nodes.forEach(node ->
                pickedItemsWeightInNode.put(node.getIndex(), pickedItemsWeightInNode(node.getIndex(), problemDTO))
        );
        return pickedItemsWeightInNode;
    }

    private int pickedItemsWeightInNode(int node, ProblemDTO problemDTO) {
        List<ItemDTO> itemsPickedInNode = getPickedItems(problemDTO, getPickingPlan(problemDTO))
                .stream().filter(item -> item.getAssignedNode() == node).collect(Collectors.toList());
        return itemsPickedInNode.stream().mapToInt(ItemDTO::getWeight).sum();
    }

    public List<Integer> getPickingPlan() {
        return pickingPlan;
    }

    public List<ItemDTO> getPickedItems() {
        return pickedItems;
    }

    public Map<Integer, Integer> getPickedItemsWeightInNode() {
        return pickedItemsWeightInNode;
    }
}
