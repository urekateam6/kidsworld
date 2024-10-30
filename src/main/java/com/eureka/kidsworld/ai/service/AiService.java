package com.eureka.kidsworld.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiService {
    private final ChatClient chatClient;

    @Value("${app.prompt-template}")
    private String promptTemplate;

    public String generateResponse(String userInput) {
        return chatClient.prompt()
                .user(userInput)
                .call()
                .content();
    }
    public String getRecommendedMbtiByBook(String title, String author) {
        String userPrompt = String.format(promptTemplate, title, author);
        return chatClient.prompt()
                .user(userPrompt)
                .call()
                .content();
    }
}
