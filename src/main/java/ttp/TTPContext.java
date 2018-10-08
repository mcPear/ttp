package ttp;

public class TTPContext {

    private final VariablesConfig varConfig;

    public TTPContext(VariablesConfig variablesConfig) {
        this.varConfig = variablesConfig;
    }

    private Double currentKnapsackWeight;

    public double getCurrentVelocity() {
        return varConfig.maxVelocity -
                currentKnapsackWeight * (varConfig.maxVelocity - varConfig.minVelocity) / varConfig.maxKnapsackCapacity;
    }

}
