package com.eureka.kidsworld.ai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
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
        String s= (String) chatClient.prompt()
                .user(userPrompt)
                .call()
                .content();
        log.info("추출된 mbti: {}",s);
        return s;
    }
}
