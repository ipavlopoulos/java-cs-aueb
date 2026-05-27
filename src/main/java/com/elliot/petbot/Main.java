package com.elliot.petbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.EnumSet;
import java.util.concurrent.*;

/**
 * Entry point for the Javagotchi application.
 * Initializes the JDA client and fires the scheduled heartbeat executor for decay loops.
 */
public class Main {

    /** Central memory registry tracking all active users */
    public static ConcurrentHashMap<String, UserSession> pets = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        String token = System.getenv("Discord_Token");
        if (token == null || token.isEmpty()) {
            System.err.println("FATAL: Discord_Token environment variable not found!");
            return;
        }
        
        EnumSet<GatewayIntent> intents = EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.MESSAGE_CONTENT);
        JDABuilder builder = JDABuilder.createLight(token, intents);
        builder.addEventListeners(new CommandListener());
        builder.setStatus(OnlineStatus.ONLINE);
        
        try {
            JDA jda = builder.build();
            jda.awaitReady();
            
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(() -> {
                try{
                    for (UserSession session : Main.pets.values()){
                        String uid = session.getUserId();

                        jda.retrieveUserById(uid).queue(user -> {
                            user.openPrivateChannel().queue(dm -> {
                                if (session.getSetupState() == 3 && session.getMyPet() != null){
                                    session.getMyPet().triggerDecay(dm);
                                
                                    if (session.getMyPet().getHunger().getCurrentValue() <= 0 || session.getMyPet().getHappiness().getCurrentValue() <= 0){
                                        StorageService.eradicatePet(uid, dm);
                                    }else {
                                        StorageService.savePet(session);
                                    }
                                }    
                            }, error -> System.err.println("Decay DM blocked for: " + uid));
                        }, error -> System.err.println("User left server: " + uid));
                    }
                } catch (Exception e){
                    System.err.println("FATAL : Master Scheduler encountered an in-memory crash. ");
                    e.printStackTrace();
                }
            }, 43200, 43200, TimeUnit.SECONDS);    

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}