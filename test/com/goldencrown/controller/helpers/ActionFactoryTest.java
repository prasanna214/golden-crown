package com.goldencrown.controller.helpers;

import com.goldencrown.controller.actions.Election;
import com.goldencrown.controller.actions.InvalidAction;
import com.goldencrown.controller.actions.KnowRuler;
import com.goldencrown.controller.actions.Mastery;
import com.goldencrown.controller.actions.Quit;
import org.junit.jupiter.api.Test;

import static com.goldencrown.controller.helpers.ActionFactory.actionMap;
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
