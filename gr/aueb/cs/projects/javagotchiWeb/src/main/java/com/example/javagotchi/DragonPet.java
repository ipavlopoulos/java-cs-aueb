package com.example.javagotchi;

public class DragonPet extends Pet{

    DragonPet(String name){
        super(name);
    }//Constructor

    public void tick(){ //Μειώνει το hunger κατά 2 και το happiness κατά 1
        this.hunger -= 2;
        this.happiness -=1;
    }//tick()

    public String checkStatus(){
        String finalString = " [ΔΡΑΚΟΣ \uD83D\uDC32]\n"+super.checkStatus();

        if (this.hunger<4){//Extra μήνυμα ότι βγάζει καπνούς αν η πείνα πέσει κάτω απο 4
            finalString = finalString +"\n ΠΡΟΕΙΔΟΠΟΙΗΣΗ ! Το κατοικίδιο "+this.name+"βγάζει καπνούς!!!";
        }

        return finalString;

    }//checkStatus()


}
