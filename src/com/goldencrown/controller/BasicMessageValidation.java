package com.goldencrown.controller;

import com.goldencrown.model.Message;

import static com.goldencrown.controller.FrequencyMatcher.charFrequencyMatch;
import static java.util.Objects.isNull;

public class BasicMessageValidation implements MessageValidationStrategy {

    @Override
    public boolean isValid(Message message) {
        if (isNull(message)) {
            return false;
        }
        if (isNull(message.getSender()) || isNull(message.getReceiver())
                || isNull(message.getContent())) {
            return false;
        }
        String emblem = message.getReceiver().getEmblem();
        return charFrequencyMatch(emblem, message.getContent());
    }
}
