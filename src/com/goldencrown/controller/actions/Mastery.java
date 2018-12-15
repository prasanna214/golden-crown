package com.goldencrown.controller.actions;

import com.goldencrown.controller.MessageConstructor;
import com.goldencrown.controller.MessageValidationStrategy;
import com.goldencrown.model.Kingdom;
import com.goldencrown.model.Message;
import com.goldencrown.model.Universe;
import com.goldencrown.view.IO;

import java.util.List;

import static com.goldencrown.controller.helpers.KingdomFactory.kingdomMap;

public class Mastery implements Action {

    private static final int REQUIRED_NUMBER_OF_ALLIES = 3;
    private Kingdom ruleSeeker;
    private MessageValidationStrategy masteryMessageValidation;
    private MessageConstructor messageConstructor;

    public Mastery(Kingdom ruleSeeker, MessageValidationStrategy masteryMessageValidation,
                   MessageConstructor messageConstructor) {
        this.ruleSeeker = ruleSeeker;
        this.masteryMessageValidation = masteryMessageValidation;
        this.messageConstructor = messageConstructor;
    }

    @Override
    public void execute(Universe universe, IO consoleIO) {
        setMessageValidationStrategy(masteryMessageValidation);
        List<Message> messages = messageConstructor.constructMessages(ruleSeeker, consoleIO);
        ruleSeeker.sendMessages(messages);
        setRuler(universe);
    }

    private void setRuler(Universe universe) {
        if (ruleSeeker.getAllies().size() >= REQUIRED_NUMBER_OF_ALLIES) {
            universe.setRuler(ruleSeeker);
        }
    }

    private void setMessageValidationStrategy(MessageValidationStrategy masteryMessageValidation) {
        kingdomMap.values().forEach(kingdom -> {
            kingdom.setMessageValidationStrategy(masteryMessageValidation);
        });
    }
}
