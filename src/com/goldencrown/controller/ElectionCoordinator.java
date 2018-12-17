package com.goldencrown.controller;

import com.goldencrown.model.Kingdom;
import com.goldencrown.model.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

public class ElectionCoordinator {
    private BalletBox balletBox;
    private List<Message> randomMessages;
    private int numberOfMessagesToBePicked;

    public void startElection(List<Kingdom> candidates) {
        if (isNull(candidates)) {
            return;
        }
        candidates.forEach(candidate -> candidate.getAllies().clear());
        balletBox.acceptMessages(candidates);
        this.randomMessages = pickRandomMessages(balletBox.getMessages(), numberOfMessagesToBePicked);
        distributeToReceivers(this.randomMessages);
    }

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

    public void setBalletBox(BalletBox balletBox) {
        this.balletBox = balletBox;
    }

    public List<Message> getRandomMessages() {
        return randomMessages;
    }

    public void setNumberOfMessagesToBePicked(Integer numberOfMessagesToBePicked) {
        this.numberOfMessagesToBePicked = numberOfMessagesToBePicked;
    }
}
