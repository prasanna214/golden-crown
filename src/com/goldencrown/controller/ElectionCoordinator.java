package com.goldencrown.controller;

import com.goldencrown.model.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

public class ElectionCoordinator {

    public List<Message> pickRandomMessages(List<Message> balletBox, int requirement) {
        if (isNull(balletBox) || balletBox.isEmpty()) {
            return balletBox;
        }

        if (requirement > balletBox.size()) {
            return balletBox;
        }

        Collections.shuffle(balletBox);
        return new ArrayList<>(balletBox.subList(0, requirement));
    }

    public void distributeToReceivers(List<Message> messages) {
        if (isNull(messages)) {
            return;
        }

        messages.forEach(message -> {
            message.getReceiver().processAllyInvite(message);
        });
    }
}
