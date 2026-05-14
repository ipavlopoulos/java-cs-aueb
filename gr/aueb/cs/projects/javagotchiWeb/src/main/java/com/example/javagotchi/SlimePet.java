package com.example.javagotchi;

public class SlimePet extends Pet{

    SlimePet(String name){
        super(name);
    }//Constructor

    public void tick(){
        if (this.happiness>8){//Το happiness του slime ΔΕΝ μπορεί να πέσει κάτω απο 8
            this.happiness -=1;
        }
        this.hunger -=1;
    }//tick()

    public String checkStatus(){
        return "[SLIME \uD83D\uDFE2] Jiggling...\n+"+super.checkStatus();
    }//checkStatus()
}
