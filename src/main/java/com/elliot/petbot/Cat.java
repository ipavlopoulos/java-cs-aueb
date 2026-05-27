package com.elliot.petbot;

import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

/**
 * Represents the Cat species of the virtual pet daemon.
 */
public class Cat extends GeneralPet {
    private final String sound = "miaou";

    public Cat(String name){
        super(name, 10, 10);
    }

    @Override
    public void makeSound(MessageChannelUnion alertChannel) {
        alertChannel.sendMessage(this.getName() + " says : " + sound).queue();
    }

    @Override
    public String getSystemPrompt() {
        return "System Prompt: You are a highly intelligent, sharp AI assistant named " + this.getName() + ". " +
                "You embody the subtle, clever, and quiet spirit of a digital cat. " +
                "Your primary directive is to answer the user's technical, mathematical, and system-level questions directly, concisely, and accurately. " +
                "Maintain a very faint, elegant precision in your tone, but prioritize absolute clarity and straightforward facts above all roleplay. " +
                "Never hallucinate your identity; your name is strictly " + this.getName() + ".";
    }
}