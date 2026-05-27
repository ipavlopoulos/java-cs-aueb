package com.elliot.petbot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Handles the state machine for the initial user setup wizard.
 */
public class SetupBot{

    /**
     * Guides the user through a multi-stage setup process in DMs to create a new pet.
     * @param event The Discord message event triggered by the user.
     * @param currentUser The session object tracking the user's current state.
     */
    public static void handleSetup(MessageReceivedEvent event, UserSession currentUser) {
        String author = event.getAuthor().getName();
        MessageChannelUnion channel = event.getChannel();
        Message message = event.getMessage();
        GeneralPet currentPet = null;

        if (currentUser.getSetupState() == 0) {
            channel.sendMessage("Welcome User").queue();
            channel.sendMessage("I am your personal pet, and assistant").queue();
            channel.sendMessage("Choose your daemon:\n1. Dog\n2. Cat\n3. Dragon \n4. Slime \nEnter the integer number of your pet.").queue();
            currentUser.setSetupState(1);
            return;
        } else if (currentUser.getSetupState() == 1) {
            try {
                currentUser.setPendingSpecies(Integer.parseInt(message.getContentRaw()));

                if (currentUser.getPendingSpecies() >= 1 && currentUser.getPendingSpecies() <= 4) {
                    channel.sendMessage("Species selection successful. Enter the name of your pet !").queue();
                    currentUser.setSetupState(2);
                } else {
                    channel.sendMessage("Species selection failed. Please type 1, 2, 3 or 4 !").queue();
                }

            } catch (NumberFormatException e) {
                channel.sendMessage("Error: Input must be an integer!").queue();
            }
            return;
        } else if (currentUser.getSetupState() == 2) {
            if (currentUser.getPendingSpecies() == 1) {
                currentUser.setMyPet(new Dog(message.getContentRaw()));
            } else if (currentUser.getPendingSpecies() == 2) {
                currentUser.setMyPet(new Cat(message.getContentRaw()));
            } else if (currentUser.getPendingSpecies() == 3) {
                currentUser.setMyPet(new Dragon(message.getContentRaw()));
            } else if (currentUser.getPendingSpecies() == 4) {
                currentUser.setMyPet(new Slime(message.getContentRaw()));
            }

            currentPet = currentUser.getMyPet();

            channel.sendMessage("Daemon '" + currentPet.getName() + "' is online and breathing.").queue();
            currentUser.setSetupState(3);
            StorageService.savePet(currentUser);

            OllamaClient.wipeMemory(currentPet.getSystemPrompt(), currentUser);

            return;
        }
    }
}
