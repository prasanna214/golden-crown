package com.goldencrown.controller.helpers;

import com.goldencrown.controller.actions.Action;
import com.goldencrown.controller.actions.InvalidAction;
import com.goldencrown.controller.actions.KnowRuler;
import com.goldencrown.controller.actions.Quit;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {

    private static final String KNOW_RULER = "1";
    private static final String QUIT = "quit";

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
        actionMap.put(QUIT, QUIT_ACTION);
    }
}
