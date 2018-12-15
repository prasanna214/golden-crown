package com.goldencrown.controller;

import com.goldencrown.controller.actions.Action;
import com.goldencrown.controller.actions.Quit;
import com.goldencrown.model.Universe;
import com.goldencrown.view.IO;

import java.util.Arrays;
import java.util.Map;

public class TameOfThrones {
    private static final String WELCOME_MESSAGE = "Welcome to Tame Of Thrones";
    private static final String KNOW_THE_RULER_OPTION = "1. Know the ruler of Southeros";
    private static final String QUIT_OPTION = "Type quit to exit";
    private static final String[] AVAILABLE_OPTIONS = {KNOW_THE_RULER_OPTION, QUIT_OPTION};
    private static final String AVAILABLE_OPTIONS_HEADER = "-------Available Options-------";
    private static final String OPTION_BELOW = "Enter your option below";

    private Universe universe;
    private IO consoleIO;
    private Map<String, Action> actionMap;

    public TameOfThrones(Universe universe, IO consoleIO, Map<String, Action> actionMap) {
        this.consoleIO = consoleIO;
        this.universe = universe;
        this.actionMap = actionMap;
    }

    public void start() {
        Action action;
        consoleIO.display(WELCOME_MESSAGE);
        do {
            displayAvailableOptions();
            action = actionMap.get(consoleIO.getInput().toLowerCase());
            action.execute(universe, consoleIO);
        } while (!(action instanceof Quit));
    }

    private void displayAvailableOptions() {
        consoleIO.display(AVAILABLE_OPTIONS_HEADER);
        Arrays.stream(AVAILABLE_OPTIONS).forEach(option -> {
            consoleIO.display(option);
        });
        consoleIO.display(OPTION_BELOW);
    }
}
