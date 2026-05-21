package com.elliot.petbot;

import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public abstract class GeneralPet {
    private String name;
    private boolean isAlive;
    private Stat hunger;
    private Stat happiness;
    private int happinessTicks;
    private boolean hasUpgraded = false;
    private int boost = 5;
    protected String status;

    public  GeneralPet(String name, int startingHunger, int startingHappiness){
        this.name = name;
        this.hunger = new Stat(startingHunger,startingHunger,"Hunger");
        this.happiness = new Stat(startingHappiness,startingHappiness,"Happiness");
        isAlive = true;
    }

    public abstract void makeSound(MessageChannelUnion alertChannel);

    public abstract String getIdentityPrompt();

    public String getName() {return name;}

    public int getHappinessTicks(){
        return happinessTicks;
    }

    public void setHappinessTicks(int happinessTicks){
        this.happinessTicks = happinessTicks;
    }

    public boolean getHasUpgraded(){
        return hasUpgraded;
    }

    public void setHasUpgraded(boolean hasUpgraded){
        this.hasUpgraded = hasUpgraded;
    }

    public boolean getIsAlive(){return isAlive;}

    public void setIsAlive(boolean variable){
        this.isAlive = variable;
    }

    public Stat getHunger(){
        return hunger;
    }

    public Stat getHappiness(){
        return happiness;
    }

    public void triggerDecay(MessageChannel alertChannel){

            this.hunger.decreaseValue();
            this.happiness.decreaseValue();
            alertChannel.sendMessage(this.getName() + " is getting hungrier. ").queue();
            if (this.getHunger().getCurrentValue() <= 3){
                alertChannel.sendMessage("Warning: " + this.getName() + " is starving !").queue();
            }
            if (this.getHunger().getCurrentValue() <= 0) {
                if (this.getIsAlive()){
                    this.setIsAlive(false);
                    alertChannel.sendMessage("I am sorry... " + this.getName() + " is dead. 💀").queue();
                }
            }

            if (this.getHappiness().getCurrentValue() > 5)  {happinessTicks += 1; } else {happinessTicks = 0;}

            if (!hasUpgraded){
                if (happinessTicks == 24){
                    this.name = "super " + name  ;
                    this.hasUpgraded = true;
                    alertChannel.sendMessage("Your pet has been upgraded to: " + this.name ).queue();
                    this.getHappiness().upgradeMax(boost);

                }
            }
    }

    public void feed(){
        this.getHunger().increaseValue();
    }

    public void play(){
        this.getHappiness().increaseValue();
    }

    public String checkStatus(){
        String string1 = "\n-----STATUS-REPORT-----\n";
        String string2 = "Name : " + this.getName();
        String string3 = "\n" +  this.getHunger().toString();
        String string4 = "\n" + this.getHappiness().toString();
        String string5 = "\n-----------------------\n";
        status = string1 + string2 + string3 + string4 + string5;
        return  status;
    }


}

