package com.elliot.petbot;

import com.google.gson.JsonArray;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.concurrent.*;

public class CommandListener extends ListenerAdapter {
//[Usage]: Sends messages to the user, while also communicates with the OllamaClient class and sends the result of the user's prompt.

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        //If statement that check's whether the message was sent  by the user, or the bot
        User author = event.getAuthor();
        if (author.isBot()) {
            return;
        }

        MessageChannelUnion channel = event.getChannel();
        Message message = event.getMessage();
        UserSession currentUser = null;
        String userInput;
        String userId = event.getAuthor().getId();


        //If statement that checks whether the user already has a session or not.
        if (Main.pets.containsKey(userId)){
            currentUser = Main.pets.get(userId);
        }else {
            currentUser = StorageService.loadPet(userId);
            if (currentUser == null){
                if (message.getContentRaw().equals("!createPet") && event.isFromGuild()) {
                    currentUser = new UserSession(userId);
                    Main.pets.put(userId, currentUser);
                    channel.sendMessage("Secure link initialized. Please check your private messages !").queue();
                    event.getAuthor().openPrivateChannel().queue(dm -> dm.sendMessage("Hello User !!!").queue());
                    return;
                }else {
                    return;
                }
            }else {
                Main.pets.put(userId, currentUser);
            }

        }

        if (currentUser.getSetupState() >= 0 && currentUser.getSetupState() <=2){
            if (event.getChannelType() == ChannelType.PRIVATE){
                SetupBot.handleSetup(event, currentUser);
                return;
            }else{
                channel.sendMessage("[System]: To set up your bot, move to your private conversations. ");
            }
        }else if (currentUser.getSetupState() == 3){
            if (event.getChannelType() != ChannelType.PRIVATE) {
                return;
            }else{
                processChat(event, currentUser);
            }
        }

    }

    private void processChat(MessageReceivedEvent event, UserSession currentUser) {
        MessageChannelUnion channel = event.getChannel();
        String userInput = event.getMessage().getContentRaw();
        GeneralPet currentPet = currentUser.getMyPet();

        if (userInput.startsWith("!")) {

            if (userInput.equals("!feed")) {
                currentPet.feed();
                StorageService.savePet(currentUser);
                channel.sendMessage("You fed " + currentPet.getName()).queue();
            } else if (userInput.equals("!play")) {
                currentPet.play();
                channel.sendMessage("You played with your pet: " + currentPet.getName()).queue();
                StorageService.savePet(currentUser);
            } else if (userInput.equals("!status")) {
                channel.sendMessage(currentPet.checkStatus()).queue();
            }else if (userInput.equals("!stop")) {
                if (currentUser.activePrompt != null) {
                    currentUser.activePrompt.cancel(true);
                    channel.sendMessage("Your prompt has been cancelled").queue();
                    return;
                }
                return;
            }else if (userInput.equals("!save")) {
                StorageService.savePet(currentUser);
                channel.sendMessage("State Saved: Daemon metrics successfully persisted to permanent disk storage!").queue();
            }else if (userInput.equals("!help")) {
                channel.sendMessage("**COMMANDS:**\n" +
                        "**!feed:** Increases the hunger status.\n" +
                        "**!play:** Increases the happiness status.\n" +
                        "**!sound:** Shows the sound every species is making.\n" +
                        "**!status:** Check your pet's current levels.\n" +
                        "**!delete:** Delete chat history.\n" +
                        "**!stop:** Stops your last prompt from being answered.\n" +
                        "**!save:** Force manual state snapshot to persistent OS disk.\n" +
                        "**!ping**  Responds with Pong.").queue();
            } else if (userInput.equals("!sound")) {
                currentPet.makeSound(channel);
            }else if (userInput.equals("!delete")) {
                if (currentUser.activePrompt != null){
                    currentUser.activePrompt.cancel(true);
                }
                OllamaClient.wipeMemory(currentPet.getIdentityPrompt(), currentUser);
                channel.sendMessage("Memory wiped. Persona re-initialized.").queue();
            } else if (userInput.equals("!ping")) {
                channel.sendMessage("Pong!").queue();
            }
            return;
        }

        JsonArray history = currentUser.getMessageHistory();
        synchronized (history){
            while (history.size() > 10){
                history.remove(1);
            }
        }

        channel.sendTyping().queue();

        if (currentUser.activePrompt != null  && !currentUser.activePrompt.isDone()){
            currentUser.activePrompt.cancel(true);
        }

        currentUser.activePrompt = CompletableFuture.supplyAsync(() -> {
            return OllamaClient.sendPrompt(userInput, history);
        }).thenAccept(response -> {
            channel.sendMessage(response).queue();
        });
    }
}
