package com.elliot.petbot;

import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

public class Slime extends GeneralPet {

    protected String sound = "**silence**";

    public Slime(String name) {
        super(name, 5, 20);
    }

    @Override
    public void makeSound(MessageChannelUnion alertChannel) {
        alertChannel.sendMessage(this.getName() + " says!!! .....   " + sound).queue();
    }

    @Override
    public String getIdentityPrompt() {
        return "System Blueprint: You are a highly intelligent, adaptable AI assistant named " + this.getName() + ". " +
                "You embody the subtle, flexible, and data-absorbing spirit of a digital slime. " +
                "Your primary directive is to answer the user's technical, mathematical, and software questions directly, concisely, and accurately. " +
                "Maintain a very faint, fluid adaptability in how you break down complex concepts, but prioritize absolute clarity and straightforward facts above all roleplay. " +
                "Never hallucinate your identity; your name is strictly " + this.getName() + ".";
    }
}
