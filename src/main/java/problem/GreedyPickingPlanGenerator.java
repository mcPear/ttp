package problem;

import problem.ItemDTO;
import problem.NodeDTO;

import java.util.*;
import java.util.stream.Collectors;

public class GreedyPickingPlanGenerator { //todo force instance

    private static List<Integer> pickingPlanStatic;
    private static List<ItemDTO> pickedItemsStatic;
    public static Map<Integer, Integer> pickedItemsWeightInNode;

    public static List<Integer> getPickingPlan(List<ItemDTO> items, int capacity) {
        if (pickingPlanStatic == null) {
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
            pickingPlanStatic = pickingPlan;
        }
        return pickingPlanStatic;
    }

    private static boolean isUnderCapacity(List<Integer> pickingPlan, List<ItemDTO> items, ItemDTO itemToAdd, int capacity) {
        int totalWeight = 0;
        for (int i = 0; i < pickingPlan.size(); i++) {
            if (pickingPlan.get(i) != 0) {
                totalWeight += items.get(i).getWeight();
            }
        }
        return totalWeight + itemToAdd.getWeight() < capacity;
    }

    public static List<ItemDTO> pickedItems(List<ItemDTO> items, List<Integer> pickingPlan) {
        if (pickedItemsStatic == null) {
            List<ItemDTO> pickedItems = new ArrayList<>();
            for (int i = 0; i < pickingPlan.size(); i++) {
                if (pickingPlan.get(i) != 0) {
                    pickedItems.add(items.get(i));
                }
            }
            pickedItemsStatic = pickedItems;
        }
        return pickedItemsStatic;
    }

    public static void initPickedItemsWeightInNode(List<NodeDTO> nodes, List<ItemDTO> items, int capacity) {
        pickedItemsWeightInNode = new HashMap<>();
        nodes.forEach(node ->
                pickedItemsWeightInNode.put(node.getIndex(), pickedItemsWeightInNode(node.getIndex(), items, capacity))
        );
    }

    private static int pickedItemsWeightInNode(int node, List<ItemDTO> items, int capacity) {
        List<ItemDTO> itemsPickedInNode = pickedItems(items, getPickingPlan(items, capacity))
                .stream().filter(item -> item.getAssignedNode() == node).collect(Collectors.toList());
        return itemsPickedInNode.stream().mapToInt(ItemDTO::getWeight).sum();
    }

}
