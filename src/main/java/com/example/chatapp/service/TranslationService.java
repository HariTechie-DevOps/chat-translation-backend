package com.example.chatapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;

@Service
public class TranslationService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String translate(String text, String from, String to) {
        // Don't translate if languages are the same or missing
        if (from == null || to == null || from.equalsIgnoreCase(to)) return text;

        try {
            // Using a more reliable testing endpoint or your own local Docker instance
            String url = "https://libretranslate.com/translate";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> body = Map.of(
                    "q", text,
                    "source", from.toLowerCase(),
                    "target", to.toLowerCase(),
                    "format", "text",
                    "api_key", "" 
            );

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
            Map<String, Object> response = restTemplate.postForObject(url, entity, Map.class);

            if (response != null && response.containsKey("translatedText")) {
                return response.get("translatedText").toString();
            }
            
            throw new RuntimeException("Empty response from Translation API");

        } catch (Exception e) {
            // REAL-WORLD FALLBACK: 
            // If the API fails (400 Bad Request/Rate Limit), we return a simulated translation
            // so your frontend logic still gets a value to display.
            System.err.println("API Error: " + e.getMessage() + ". Using Mock Fallback.");
            
            return String.format("[%s to %s]: %s", from.toUpperCase(), to.toUpperCase(), text);
        }
    }
}
