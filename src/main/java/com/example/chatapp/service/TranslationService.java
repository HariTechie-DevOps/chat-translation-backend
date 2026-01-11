package com.example.chatapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class TranslationService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String translate(String text, String from, String to) {
        if (from.equals(to)) return text;

        Map<String, String> request = Map.of(
                "q", text,
                "source", from,
                "target", to,
                "format", "text"
        );

        Map response = restTemplate.postForObject(
                "https://libretranslate.com/translate",
                request,
                Map.class
        );

        return response.get("translatedText").toString();
    }
}
