package com.elliot.petbot;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class OllamaClient {

    // 1. We switch to the /chat endpoint
    private static final String OLLAMA_CHAT_ENDPOINT = "http://localhost:11434/api/chat";
    private static final String AI_MODEL = "llama3.2:1b";
    private static final HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(20)).build();

    // 2. The Hippocampus: This array stores the entire conversation history

    public static String sendPrompt(String userMessage, JsonArray messageHistory) {

        try {
            // 3. Package the user's new message and add it to the memory bank
            JsonObject userMsg = new JsonObject();
            userMsg.addProperty("role", "user");
            userMsg.addProperty("content", userMessage);
            String payloadString;

            synchronized (messageHistory) {
                messageHistory.add(userMsg);


                // 4. Build the payload, sending the ENTIRE messageHistory array
                JsonObject requestJson = new JsonObject();
                requestJson.addProperty("model", AI_MODEL);
                requestJson.add("messages", messageHistory);
                requestJson.addProperty("stream", false);

                payloadString = requestJson.toString();

            }

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(OLLAMA_CHAT_ENDPOINT)).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(payloadString)).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // 5. The /chat endpoint returns a slightly different JSON structure
                JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();
                JsonObject aiMessage = responseJson.getAsJsonObject("message");
                String aiText = aiMessage.get("content").getAsString();

                // 6. Save the AI's response to the memory bank so it remembers it next time
                JsonObject botMsg = new JsonObject();
                botMsg.addProperty("role", "assistant");
                botMsg.addProperty("content", aiText);

                synchronized (messageHistory){
                    messageHistory.add(botMsg);
                }


                return aiText;
            } else {
                return "[System Error]: Ollama returned status code " + response.statusCode();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "[System Error]: Failed to connect to local AI. Is Ollama running?";
        }
    }
    //Creates a new chat history for the user.
    public static void wipeMemory(UserSession session) {
        session.setMessageHistory(new JsonArray());
    }

    //A method to wipe the memory when we stop the chat, and create a new history, importing the persona of the bot inside it.
    public static void wipeMemory(String identity,  UserSession session) {

        JsonObject object = new JsonObject();
        JsonArray freshHistory = new JsonArray();
        object.addProperty("role", "system");
        object.addProperty("content", identity);
        freshHistory.add(object);
        session.setMessageHistory(freshHistory);
    }
}