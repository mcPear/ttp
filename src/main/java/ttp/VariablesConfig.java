package ttp;

import org.apache.commons.math3.linear.RealMatrix;

import java.util.List;

public class VariablesConfig {

    //tsp
    public List<TTPCity> cities;
    public RealMatrix cityDistances;
    public double maxVelocity;
    public double minVelocity;

    //kp
    public List<KPItem> items;
    public double maxKnapsackCapacity;

}
