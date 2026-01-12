package com.example.chatapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;

@Service
public class TranslationService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String translate(String text, String from, String to) {
        if (from == null || to == null || from.equalsIgnoreCase(to)) return text;

        try {
            String url = "https://libretranslate.com/translate";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> body = Map.of(
                    "q", text,
                    "source", from.toLowerCase(),
                    "target", to.toLowerCase(),
                    "format", "text",
                    "api_key", "" // Leave empty if using a local/free instance
            );

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
            Map<String, Object> response = restTemplate.postForObject(url, entity, Map.class);

            return response.get("translatedText").toString();
        } catch (Exception e) {
            System.err.println("Translation failed: " + e.getMessage());
            return text; // Fallback to original text if API fails
        }
    }
}
