package com.elliot.petbot;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Handles REST API communication with the local Ollama LLM instance.
 */
public class OllamaClient {

    // Pull configurations from Environment Variables if available, otherwise use defaults.
    private static final String OLLAMA_CHAT_ENDPOINT = System.getenv("OLLAMA_URL") != null ? System.getenv("OLLAMA_URL") : "http://localhost:11434/api/chat";
    private static final String AI_MODEL = System.getenv("OLLAMA_MODEL") != null ? System.getenv("OLLAMA_MODEL") : "llama3.2:1b";
    
    private static final HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(120)).build();

    /**
     * Sends a user message to the local AI and appends it to the memory bank.
     * @param userMessage The message input from the user.
     * @param messageHistory The JsonArray representing the conversation history.
     * @return The AI's response as a string.
     */
    public static String sendPrompt(String userMessage, JsonArray messageHistory) {
        try {
            JsonObject userMsg = new JsonObject();
            userMsg.addProperty("role", "user");
            userMsg.addProperty("content", userMessage);
            String payloadString;

            synchronized (messageHistory) {
                messageHistory.add(userMsg);

                JsonObject requestJson = new JsonObject();
                requestJson.addProperty("model", AI_MODEL);
                requestJson.add("messages", messageHistory);
                requestJson.addProperty("stream", false);

                payloadString = requestJson.toString();
            }

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(OLLAMA_CHAT_ENDPOINT)).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(payloadString)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();
                JsonObject aiMessage = responseJson.getAsJsonObject("message");
                String aiText = aiMessage.get("content").getAsString();

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

    /**
     * Wipes the memory bank completely, creating a fresh chat history.
     * @param session The user session whose memory should be wiped.
     */
    public static void wipeMemory(UserSession session) {
        session.setMessageHistory(new JsonArray());
    }

    /**
     * Wipes the memory bank and re-injects the system prompt to establish the bot's persona.
     * @param identity The system prompt establishing the bot's behavior.
     * @param session The user session whose memory should be wiped.
     */
    public static void wipeMemory(String identity, UserSession session) {
        JsonObject object = new JsonObject();
        JsonArray freshHistory = new JsonArray();
        object.addProperty("role", "system");
        object.addProperty("content", identity);
        freshHistory.add(object);
        session.setMessageHistory(freshHistory);
    }
}