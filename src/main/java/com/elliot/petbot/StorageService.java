package com.elliot.petbot;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Handles persistent storage, reading and writing pet state to the local file system.
 */
public class StorageService {

    /**
     * Serializes the user's pet state to a JSON file.
     * @param session The active user session to save.
     */
    public static void savePet(UserSession session){
        try{
            GeneralPet pet = session.getMyPet();
            if (pet == null){return;}

            JsonObject saveData = new JsonObject();
            saveData.addProperty("userId", session.getUserId());
            saveData.addProperty("name", pet.getName());
            saveData.addProperty("species", session.getPendingSpecies());
            saveData.addProperty("hunger", pet.getHunger().getCurrentValue());
            saveData.addProperty("happiness", pet.getHappiness().getCurrentValue());
            saveData.addProperty("maxHunger", pet.getHunger().getMaxValue());
            saveData.addProperty("maxHappiness", pet.getHappiness().getMaxValue());
            saveData.addProperty("happinessTicks", pet.getHappinessTicks());
            saveData.addProperty("hasUpgraded", pet.getHasUpgraded());
            saveData.addProperty("lastSaved", System.currentTimeMillis());

            java.nio.file.Files.writeString(java.nio.file.Path.of(session.getUserId()+".txt"), saveData.toString());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a user's pet state from a JSON file, calculates offline decay, and rebuilds the object.
     * @param userId The Discord ID of the user.
     * @return The rebuilt UserSession, or null if no save file exists or the pet died offline.
     */
    public static UserSession loadPet(String userId){
        if (java.nio.file.Files.exists(java.nio.file.Path.of(userId+".txt"))) {
            try{
                String data = java.nio.file.Files.readString(java.nio.file.Path.of(userId+".txt"));
                JsonObject loadedData = JsonParser.parseString(data).getAsJsonObject();
                UserSession currentUser = new UserSession(userId);
                currentUser.setSetupState(3);
                String petName = loadedData.get("name").getAsString();
                int petSpecies = loadedData.get("species").getAsInt();
                int petHunger = loadedData.get("hunger").getAsInt();
                int petHappiness = loadedData.get("happiness").getAsInt();
                int maxHunger = loadedData.has("maxHunger") ? loadedData.get("maxHunger").getAsInt() : 20;
                int maxHappiness = loadedData.has("maxHappiness") ? loadedData.get("maxHappiness").getAsInt() : 20;
                int happinessTicks = loadedData.has("happinessTicks") ? loadedData.get("happinessTicks").getAsInt() : 0;
                boolean hasUpgraded = loadedData.has("hasUpgraded") && loadedData.get("hasUpgraded").getAsBoolean();
                long lastSaved = loadedData.has("lastSaved") ? loadedData.get("lastSaved").getAsLong() : System.currentTimeMillis();
                GeneralPet rebuiltPet = null;

                if (petSpecies == 1 ){
                    rebuiltPet = new Dog(petName);
                }else if  (petSpecies == 2){
                    rebuiltPet = new Cat(petName);
                }else if (petSpecies == 3){
                    rebuiltPet = new Dragon(petName);
                }else if (petSpecies == 4){
                    rebuiltPet = new Slime(petName);
                }

                if (rebuiltPet == null) {return null;}

                rebuiltPet.getHunger().setMaxValue(maxHunger);
                rebuiltPet.getHappiness().setMaxValue(maxHappiness);
                rebuiltPet.getHunger().setCurrentValue(petHunger);
                rebuiltPet.getHappiness().setCurrentValue(petHappiness);
                rebuiltPet.setHappinessTicks(happinessTicks);
                rebuiltPet.setHasUpgraded(hasUpgraded);

                long elapsedMillis =  System.currentTimeMillis() - lastSaved;
                int missedTicks = (int) (elapsedMillis/43200000L);

                for (int i = 0; i < missedTicks ; i++){
                    rebuiltPet.getHunger().decreaseValue();
                    rebuiltPet.getHappiness().decreaseValue();
                }

                if (rebuiltPet.getHunger().getCurrentValue() <=0 || rebuiltPet.getHappiness().getCurrentValue() <= 0){
                    Files.deleteIfExists(Path.of(userId +".txt"));
                    System.out.println("[REAPER]: User " + userId + "'s daemon succumned to offline decay upon loading.");
                    return null;
                }

                currentUser.setMyPet(rebuiltPet);
                currentUser.setPendingSpecies(petSpecies);
                OllamaClient.wipeMemory(rebuiltPet.getSystemPrompt(), currentUser);

                savePet(currentUser);
                return currentUser;

            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * Permanently deletes a user's pet data due to starvation/death.
     * @param userId The Discord ID of the user whose pet is being deleted.
     * @param channel The Discord channel to send the death alert to.
     */
    public static void eradicatePet(String userId, MessageChannel channel){
        if (Main.pets.containsKey(userId)){
           UserSession doomedSession = Main.pets.remove(userId);
           if (doomedSession != null){
               if (doomedSession.activePrompt != null) {
                   doomedSession.activePrompt.cancel(true);
               }
           }
        }
        try{
            Path filepath = Path.of(userId+".txt");
            Files.deleteIfExists(filepath);
        }catch (java.io.IOException e){
            e.printStackTrace();
        }

        if (channel != null){
            channel.sendMessage("[System Alert]: Your daemon has completely starved to death. Its persistent matrix, memories, and files have been permanently wiped from the server. You must return to the public gateway and type '!createPet' to register a new one.").queue();
        }
    }
}
