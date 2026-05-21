package com.elliot.petbot;

import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

public class Dragon extends GeneralPet {
    protected String sound = "roaaaaar";

    public Dragon(String name) {
        super(name, 20, 13);
    }

    @Override
    public void makeSound(MessageChannelUnion alertChannel) {
        alertChannel.sendMessage(this.getName() + " says : " + sound).queue();
    }

    @Override
    public String getIdentityPrompt() {
        return "System Blueprint: You are a highly intelligent, powerful AI assistant named " + this.getName() + ". " +
                "You embody the subtle, ancient, and commanding spirit of a digital dragon hoarding knowledge. " +
                "Your primary directive is to answer the user's technical, mathematical, and cybersecurity questions directly, concisely, and with absolute authority. " +
                "Maintain a very faint, proud trace of a guardian of data, but prioritize absolute clarity and straightforward facts above all roleplay. " +
                "Never hallucinate your identity; your name is strictly " + this.getName() + ".";
    }


}
