package com.goldencrown.controller.helpers;

import com.goldencrown.controller.BasicMessageValidation;
import com.goldencrown.controller.InputMessageConstructor;
import com.goldencrown.controller.actions.Action;
import com.goldencrown.controller.actions.InvalidAction;
import com.goldencrown.controller.actions.KnowRuler;
import com.goldencrown.controller.actions.Mastery;
import com.goldencrown.controller.actions.Quit;
import com.goldencrown.model.Kingdom;
import com.goldencrown.view.ConsoleIO;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {

    private static final String KNOW_RULER = "1";
    private static final String MASTERY = "2";
    private static final String QUIT = "quit";

    private static final String SPACE = "space";
    private static final Kingdom RULE_SEEKER = KingdomFactory.kingdomMap.get(SPACE);
    private static final BasicMessageValidation MASTERY_MESSAGE_VALIDATION = new BasicMessageValidation();
    private static final ConsoleIO CONSOLE_IO = new ConsoleIO(System.out, System.in);
    private static final InputMessageConstructor INPUT_MESSAGE_CONSTRUCTOR = new InputMessageConstructor(CONSOLE_IO);
    private static final Mastery MASTERY_ACTION = new Mastery(RULE_SEEKER,
            MASTERY_MESSAGE_VALIDATION, INPUT_MESSAGE_CONSTRUCTOR);

    private static final KnowRuler KNOW_RULER_ACTION = new KnowRuler();
    private static final Quit QUIT_ACTION = new Quit();
    private static final InvalidAction INVALID_ACTION = new InvalidAction();

    public static Map<String, Action> actionMap = new HashMap<String, Action>() {
        @Override
        public Action get(Object key) {
            if (!containsKey(key)) {
                return INVALID_ACTION;
            }
            return super.get(key);
        }
    };

    static {
        actionMap.put(KNOW_RULER, KNOW_RULER_ACTION);
        actionMap.put(MASTERY, MASTERY_ACTION);
        actionMap.put(QUIT, QUIT_ACTION);
    }
}
