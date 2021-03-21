package de.chris.apps.cars.entitiy;

public class Trend {

    public enum TrendType {
        NO_TREND, STABLE, FALLING, RISING 
    }

    private final TrendType type; 
    private final int difference; 

    public Trend(TrendType type, int difference) {
        this.type = type; 
        this.difference = difference;
    }

    public int getDifference() {
        return difference;
    }

    public TrendType getType() {
        return type;
    }
}
