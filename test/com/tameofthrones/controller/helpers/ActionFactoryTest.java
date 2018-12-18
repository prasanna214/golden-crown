package com.tameofthrones.controller.helpers;

import com.tameofthrones.controller.actions.Election;
import com.tameofthrones.controller.actions.InvalidAction;
import com.tameofthrones.controller.actions.KnowRuler;
import com.tameofthrones.controller.actions.Mastery;
import com.tameofthrones.controller.actions.Quit;
import org.junit.jupiter.api.Test;

import static com.tameofthrones.controller.helpers.ActionFactory.actionMap;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActionFactoryTest {

    @Test
    void returnKnowTheRulerActionForInputOne() {
        assertTrue(actionMap.get("1") instanceof KnowRuler);
    }

    @Test
    void returnMasteryActionForInputTwo() {
        assertTrue(actionMap.get("2") instanceof Mastery);
    }

    @Test
    void returnElectionActionForInputTwo() {
        assertTrue(actionMap.get("3") instanceof Election);
    }

    @Test
    void returnKnowQuitActionForInputQuit() {
        assertTrue(actionMap.get("quit") instanceof Quit);
    }

    @Test
    void returnKnowInvalidActionIfInputNotFoundInMap() {
        assertTrue(actionMap.get("something invalid") instanceof InvalidAction);
    }
}
