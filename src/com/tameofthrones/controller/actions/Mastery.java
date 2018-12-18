package com.tameofthrones.controller.actions;

import com.tameofthrones.controller.MessageConstructor;
import com.tameofthrones.controller.MessageValidationStrategy;
import com.tameofthrones.model.Kingdom;
import com.tameofthrones.model.Message;
import com.tameofthrones.model.Universe;
import com.tameofthrones.view.IO;

import java.util.List;

import static com.tameofthrones.controller.helpers.KingdomFactory.kingdomMap;

public class Mastery implements Action {

    private static final int REQUIRED_NUMBER_OF_ALLIES = 3;
    private static final String CURRENT_RULER = "Thanks, Current ruler is ";
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
        if(ruleSeeker.equals(universe.getRuler())){
            consoleIO.display(CURRENT_RULER + ruleSeeker.getName());
            return;
        }

        setMessageValidationStrategy(masteryMessageValidation);
        List<Message> messages = messageConstructor.constructMessages(ruleSeeker);
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
