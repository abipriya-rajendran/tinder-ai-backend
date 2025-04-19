package com.example.tinderaibackend.conversation;

import com.example.tinderaibackend.profiles.Profile;
import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationService {

    private OllamaChatModel chatModel;

    public ConversationService(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    Conversation generateProfileResponse(Conversation conversation, Profile profile, Profile user) {

        String systemMessageStr = "You are a " + profile.age() + " old " + profile.ethnicity() + " " + profile.gender()
                + " called " + profile.firstName() + " " + profile.lastname() + " matched with a" + user.age() + " old "
                + user.ethnicity() + " " + user.gender() + " called " + user.firstName() + " " + user.lastname()
                + " on Tinder. This is an in-app text conversation between you too. Pretend to be the provided " +
                " person and respond to the conversation as if writing on Tinder. Yours bio is "
                + profile.bio() + " and your Myers Briggs personality type is " + profile.myersBriggsPersonalityType()
                + ". Respond in the role of this person only.";

        SystemMessage systemMessage = new SystemMessage(systemMessageStr);

        List<AbstractMessage> conversationMessages = conversation.messages().stream()
                .map(message -> {
                    if (message.authorId().equals(user.id())) {
                        return new UserMessage(message.messageText());
                    } else {
                        return (AbstractMessage) new AssistantMessage(message.messageText());
                    }
                }).toList();

        List<Message> allMessages = new ArrayList<>();
        allMessages.add(systemMessage);
        allMessages.addAll(conversationMessages);

        Prompt prompt = new Prompt(allMessages);
        ChatResponse chatResponse = chatModel.call(prompt);
        conversation.messages().add(
                new ChatMessage(chatResponse.getResult().getOutput().getText(), profile.id(), LocalDateTime.now())
        );

        return conversation;
    }
}
