package com.elliot.petbot;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

//Class so I can safely create a HashMap. It creates a wrapper. It allocates memory for the Session immediately, and all the other fields stay null
public class UserSession {
    private String userId;
    private int setupState = 0;
    private GeneralPet myPet;
    private int pendingSpecies;
    protected CompletableFuture<Void> activePrompt;
    private JsonArray messageHistory;
    //Stays safely null until the exact moment a specific Pet is born

    public UserSession(String userId) {
        this.userId = userId;
        messageHistory = new JsonArray();
    }

    public String getUserId() {
        return userId;
    }

    public void setMessageHistory(JsonArray messageHistory) {
        this.messageHistory = messageHistory;
    }

    public JsonArray getMessageHistory() {
        return  messageHistory;
    }

    public int getPendingSpecies(){
        return pendingSpecies;
    }

    public void setPendingSpecies(int pendingSpecies){
        this.pendingSpecies = pendingSpecies;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getSetupState() {
        return this.setupState;
    }

    public void setSetupState(int setupState) {
        this.setupState = setupState;
    }


    public void setMyPet(GeneralPet myPet) {
        this.myPet = myPet;
    }

    public  GeneralPet getMyPet() {
        return  this.myPet;
    }
}


