package com.tameofthrones.controller;

import com.tameofthrones.controller.helpers.MessageContentGenerator;
import com.tameofthrones.model.Kingdom;
import com.tameofthrones.model.Message;

import java.util.ArrayList;
import java.util.List;

import static com.tameofthrones.controller.helpers.KingdomFactory.kingdomMap;
import static java.util.Objects.isNull;

public class BalletMessageConstructor implements MessageConstructor {
    private List<Message> messages = new ArrayList<>();
    private MessageContentGenerator messageContentGenerator;

    public BalletMessageConstructor(MessageContentGenerator messageContentGenerator) {
        this.messageContentGenerator = messageContentGenerator;
    }

    @Override
    public List<Message> constructMessages(Kingdom sender) {
        if (isNull(sender)) {
            return messages;
        }
        constructMessagesForAllKingdoms(sender);
        return messages;
    }

    private void constructMessagesForAllKingdoms(Kingdom sender) {
        kingdomMap.values().stream().filter(kingdom -> !kingdom.equals(sender))
                .forEach(kingdom -> {
                    String randomMessageContent = messageContentGenerator.getRandomMessageContent();
                    Message message = new Message(sender, kingdom, randomMessageContent);
                    messages.add(message);
                });
    }
}
