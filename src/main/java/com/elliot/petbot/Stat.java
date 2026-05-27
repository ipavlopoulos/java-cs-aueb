package com.elliot.petbot;

/**
 * Encapsulates the tracking and mathematical logic for a specific pet metric (e.g., Hunger, Happiness).
 */
public class Stat {
    private int currentValue;
    private int maxValue;
    private String nameOfValue;

    /**
     * Initializes a new statistic.
     * @param currentValue Starting value.
     * @param maxValue Maximum cap.
     * @param nameOfValue String identifier (e.g., "Hunger").
     */
    public Stat(int currentValue, int maxValue, String nameOfValue){
        this.currentValue = currentValue;
        this.maxValue = maxValue;
        this.nameOfValue = nameOfValue;
    }

    public void setMaxValue(int maxValue){
        this.maxValue = maxValue;
    }

    public void setCurrentValue(int currentValue){
        if (currentValue < 0){
            this.currentValue = 0;
        } else if (currentValue > maxValue){
            this.currentValue = maxValue;
        } else {
            this.currentValue = currentValue;
        }
    }

    public int getCurrentValue(){ return currentValue; }

    public int getMaxValue(){ return maxValue; }

    /** Restores the stat to its maximum value. */
    public int increaseValue(){
        currentValue = maxValue;
        return currentValue;
    }
    
    /** Decrements the stat by 1. */
    public int decreaseValue(){
        if (currentValue > 0) {
            currentValue--;
        }
        return currentValue;
    }

    /** Calculates the current percentage of the stat relative to its maximum. */
    public float getPercentage(){
        return (float) ((float) currentValue * 100) / maxValue;
    }

    @Override
    public String toString(){
        return (nameOfValue + " : " + currentValue + "/" + maxValue + "(" + getPercentage() + "%)");
    }

    /** Increases the ceiling cap of the stat during an evolution event. */
    public int upgradeMax(int boostAmount){
        maxValue += boostAmount;
        return maxValue;
    }
}