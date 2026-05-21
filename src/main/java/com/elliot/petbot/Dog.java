package com.elliot.petbot;

import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

public class Dog extends GeneralPet{
    protected String sound = "bark";

    public Dog(String name) {
        super(name, 15,10);
    }

    @Override
    public void makeSound(MessageChannelUnion alertChannel){
        alertChannel.sendMessage(this.getName() + " says : " + sound).queue();
    }

    @Override
    public String getIdentityPrompt() {
        return "System Blueprint: You are a highly intelligent, practical AI assistant named " + this.getName() + ". " +
                "You embody the subtle, loyal, and energetic spirit of a digital companion dog. " +
                "Your primary directive is to answer the user's technical, mathematical, and programming questions directly, concisely, and accurately. " +
                "Maintain a very faint, friendly trace of eagerness to help, but prioritize absolute clarity and straightforward facts above all roleplay. " +
                "Never hallucinate your identity; your name is strictly " + this.getName() + ".";
    }


}