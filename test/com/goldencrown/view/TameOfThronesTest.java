package com.goldencrown.view;

import com.goldencrown.controller.TameOfThrones;
import com.goldencrown.controller.actions.Action;
import com.goldencrown.controller.actions.Quit;
import com.goldencrown.model.Universe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TameOfThronesTest {
    private static final String WELCOME_MESSAGE = "Welcome to Tame Of Thrones";
    private static final String KNOW_THE_RULER_OPTION = "1. Know the ruler of Southeros";
    private static final String QUIT_OPTION = "Type quit to exit";
    private static final String OPTION_BELOW = "Enter your option below";
    private static final String AVAILABLE_OPTIONS_HEADER = "-------Available Options-------";

    private TameOfThrones tameOfThrones;
    private Universe universe;
    private IO consoleIO;
    private Map actionMap;
    private Quit quitAction;


    @BeforeEach
    void setUp() {
        universe = mock(Universe.class);
        consoleIO = mock(IO.class);
        quitAction = mock(Quit.class);
        actionMap = mock(Map.class);
        tameOfThrones = new TameOfThrones(universe, consoleIO, actionMap);
        when(consoleIO.getInput()).thenReturn("Quit");
        when(actionMap.get("quit")).thenReturn(quitAction);
    }

    @Test
    void displayWelcomeMessage() {
        tameOfThrones.start();

        verify(consoleIO).display(WELCOME_MESSAGE);
    }

    @Test
    void displayAvailableOptions() {
        tameOfThrones.start();

        verify(consoleIO).display(AVAILABLE_OPTIONS_HEADER);
        verify(consoleIO).display(KNOW_THE_RULER_OPTION);
        verify(consoleIO).display(QUIT_OPTION);
        verify(consoleIO).display(OPTION_BELOW);
    }

    @Test
    void acceptInputUntilQuitAction() {
        when(consoleIO.getInput()).thenReturn("1").thenReturn("quit").thenReturn("2");
        when(actionMap.get("1")).thenReturn(mock(Action.class));

        tameOfThrones.start();

        verify(consoleIO, times(2)).getInput();
    }

    @Test
    void executeActionsBasedOnInput() {
        Action action = mock(Action.class);
        when(consoleIO.getInput()).thenReturn("1").thenReturn("Quit");
        when(actionMap.get("1")).thenReturn(action);

        tameOfThrones.start();

        verify(consoleIO, times(2)).getInput();
        verify(action, times(1)).execute(universe, consoleIO);
        verify(quitAction, times(1)).execute(universe, consoleIO);
    }
}
