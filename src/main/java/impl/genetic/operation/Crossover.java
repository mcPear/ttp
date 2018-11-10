package impl.genetic.operation;

import impl.genetic.Rand;
import problem.TTPGenome;

import java.util.*;

public class Crossover {

    public static List<TTPGenome> cross(List<TTPGenome> basePopulation, double crossingChancePercent) {
        List<TTPGenome> crossedPopulation = new ArrayList<>();
        while (!basePopulation.isEmpty()) {
            TTPGenome ttpGenome1 = randomGenome(basePopulation);
            basePopulation.remove(ttpGenome1);
            TTPGenome ttpGenome2 = randomGenome(basePopulation);
            basePopulation.remove(ttpGenome2);
            if (occurredCrossover(crossingChancePercent)) {
                Children<TTPGenome, TTPGenome> children = children(ttpGenome1, ttpGenome2);
                crossedPopulation.add(children.son);
                crossedPopulation.add(children.daughter);
            } else {
                crossedPopulation.add(ttpGenome1);
                crossedPopulation.add(ttpGenome2);
            }
        }
        return crossedPopulation;
    }

    private static boolean occurredCrossover(double crossingChancePercent) {
        return Rand.object.nextInt(Rand.fullChancePercent) < crossingChancePercent;
    }

    private static Children<TTPGenome, TTPGenome> children(TTPGenome father, TTPGenome mother) {
        Children<List<Integer>, List<Integer>> childrenTours = children(father.getTour(), mother.getTour());

        TTPGenome son = new TTPGenome(father.getPickingPlan(), childrenTours.son);
        TTPGenome daughter = new TTPGenome(mother.getPickingPlan(), childrenTours.daughter);
        return new Children<>(son, daughter);
    }

    private static Children<List<Integer>, List<Integer>> children(List<Integer> father, List<Integer> mother) {
        if (father.size() == 1 || father.size() == 2) {
            return new Children<>(father, mother);
        }
        int half = father.size() / 2;

        List<Integer> son = new ArrayList<>(father.subList(0, half));
        List<Integer> daughter = new ArrayList<>(mother.subList(0, half));
        son.addAll(new ArrayList<>(mother.subList(half, mother.size())));
        daughter.addAll(new ArrayList<>(father.subList(half, father.size())));
        fix(son, father);
        fix(daughter, mother);
        return new Children<>(son, daughter);
    }

    private static void fix(List<Integer> brokenVector, List<Integer> correctVector) {
        List<Integer> sortedCopyOfVector = copyAndSortVector(brokenVector);
        Set<Integer> duplicatedGenes = getDuplicatedGenes(sortedCopyOfVector);
        List<Integer> missingGenes = getMissingGenes(correctVector, brokenVector);
        replaceDuplicatedGenes(brokenVector, duplicatedGenes, missingGenes);
    }

    private static List<Integer> copyAndSortVector(List<Integer> vector) {
        List<Integer> result = new ArrayList<>(vector);
        Collections.sort(result);
        return result;
    }

    private static Set<Integer> getDuplicatedGenes(List<Integer> sortedVector) {//todo test
        List<Integer> duplicates = new ArrayList<>();
        sortedVector.forEach(el -> {
            if (Collections.frequency(sortedVector, el) > 1) {
                duplicates.add(el);
            }
        });
        return new HashSet<>(duplicates);
    }

    private static List<Integer> getMissingGenes(List<Integer> correctVector, List<Integer> brokenVector) {
        List<Integer> missingGenes = new ArrayList<>();
        correctVector.forEach(correctGene -> {
            if (!brokenVector.contains(correctGene)) {
                missingGenes.add(correctGene);
            }
        });
        return missingGenes;
    }

    private static void replaceDuplicatedGenes(List<Integer> vector, Set<Integer> duplicatedGenes, List<Integer> missingGenes) {
        final int FIRST_INDEX = 0;
        for (int i = 0; i < vector.size(); i++) {
            Integer currentGene = vector.get(i);
            if (duplicatedGenes.contains(currentGene)) { //FIXME połowa pierwszego genomu ulegnie zmianie, połowa drugiego się nie zmieni
                vector.set(i, missingGenes.get(FIRST_INDEX));
                duplicatedGenes.remove(currentGene);
                missingGenes.remove(FIRST_INDEX);
            }
        }
    }

    private static TTPGenome randomGenome(List<TTPGenome> population) {
        return population.get(Rand.object.nextInt(population.size()));
    }

    private static class Children<X, Y> {
        public final X son;
        public final Y daughter;

        public Children(X son, Y daughter) {
            this.son = son;
            this.daughter = daughter;
        }
    }

}
