package com.elliot.petbot;

import com.google.gson.JsonArray;
import java.util.concurrent.*;

/**
 * Acts as a transient memory wrapper, mapping a Discord user to their current pet, setup state, and async threads.
 */
public class UserSession {
    private String userId;
    private int setupState = 0;
    private GeneralPet myPet;
    private int pendingSpecies;
    protected CompletableFuture<Void> activePrompt;
    private JsonArray messageHistory;

    /**
     * Allocates session memory for a new user interaction.
     * @param userId The Discord ID of the user.
     */
    public UserSession(String userId) {
        this.userId = userId;
        messageHistory = new JsonArray();
    }

    public String getUserId() { return userId; }

    public void setMessageHistory(JsonArray messageHistory) { this.messageHistory = messageHistory; }

    public JsonArray getMessageHistory() { return messageHistory; }

    public int getPendingSpecies() { return pendingSpecies; }

    public void setPendingSpecies(int pendingSpecies) { this.pendingSpecies = pendingSpecies; }

    public void setUserId(String userId) { this.userId = userId; }

    public int getSetupState() { return this.setupState; }

    public void setSetupState(int setupState) { this.setupState = setupState; }

    public void setMyPet(GeneralPet myPet) { this.myPet = myPet; }

    public GeneralPet getMyPet() { return this.myPet; }
}