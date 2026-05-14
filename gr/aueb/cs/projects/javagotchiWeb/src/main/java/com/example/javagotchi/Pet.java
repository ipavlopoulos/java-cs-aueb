package com.example.javagotchi;

public class Pet {
    protected String name;
    protected int hunger;
    protected int happiness;

    Pet(String name){
        this.name = name;
        this.hunger = 6;
        this.happiness = 9;
    }//Constructor

    public String getName(){
        return this.name;
    }//getName()

    public int getHunger(){
        return this.hunger;
    }//getHunger()

    public int getHappiness(){
        return this.happiness;
    }//getHappiness()


    public void feed() { //Αυξάνει το hunger κατά 1
        if(this.hunger<10){
            this.hunger += 1;
        }
    }//feed()

    public void play() { //Αυξάνει το happiness κατά 1
        if(this.happiness<10){
            this.happiness += 1;
        }
    }//play()

    public void tick(){ //Μειώνει το hunger και το happiness κατά 1
        this.hunger -= 1;
        this.happiness -=1;
    }//tick()

    public String checkStatus(){
        return "Όνομα κατοικιδίου: "+this.name+"\nHunger: "+this.hunger+"\nHappiness: "+this.happiness;
    }//checkStatus()

}

