package  com.elliot.petbot;

public  class Stat{

    private int currentValue;
    private int maxValue;
    private String nameOfValue;


    public Stat(int currentValue,int maxValue,String nameOfValue ){
        this.currentValue=currentValue;
        this.maxValue=maxValue;
        this.nameOfValue=nameOfValue;

    }

    public void setMaxValue(int maxValue){
        this.maxValue=maxValue;
    }

    public void setCurrentValue(int currentValue){
        if (currentValue < 0){
            this.currentValue=0;
        }else if (currentValue > maxValue){
            this.currentValue=maxValue;
        }else {
            this.currentValue = currentValue;
        }
    }

    public int getCurrentValue(){
        return currentValue;
    }

    public int getMaxValue(){
        return maxValue;
    }

    public int increaseValue(){
        currentValue = maxValue;
        return currentValue;
    }
    public int decreaseValue(){
        if (currentValue > 0) {
            currentValue--;
        }
        return currentValue;
    }

    public float getPercentage(){
        float percentage  = (float) ((float) currentValue*100)/maxValue;
        return percentage;

    }
    @Override
    public String toString(){
        return (nameOfValue + " : " + currentValue + "/" + maxValue + "(" + getPercentage() + "%)");
    }

    public int upgradeMax(int boostAmount){
        maxValue += boostAmount;
        return maxValue;
    }

}