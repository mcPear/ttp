package ea;

import java.util.ArrayList;
import java.util.List;

public class OverallResult {

    private List<GenerationResult> allGenerations = new ArrayList<>();

    public void add(GenerationResult generationResult) {
        allGenerations.add(generationResult);
    }

    public List<GenerationResult> getAllGenerations() {
        return allGenerations;
    }

    public String toCsvString() {
        final String COMMA_DEL = ", ";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < allGenerations.size(); i++) {
            GenerationResult e = allGenerations.get(i);
            builder.append((i) + COMMA_DEL + e.best + COMMA_DEL + e.middle + COMMA_DEL + e.worst + "\n");
        }
        return builder.toString();
    }

}
