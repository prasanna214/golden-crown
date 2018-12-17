package com.goldencrown.controller;

import com.goldencrown.view.IO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CandidateRegistryTest {
    private static final String INPUT_MESSAGE = "Enter the kingdoms competing to be the ruler (names separated space):";
    private static final String INVALID_CANDIDATE_MESSAGES = "Please enter valid candidate names";

    private IO consoleIO;
    private CandidateRegistry candidateRegistry;

    @BeforeEach
    void setUp() {
        consoleIO = mock(IO.class);
        candidateRegistry = new CandidateRegistry(consoleIO);
        when(consoleIO.getInput()).thenReturn("ice");
    }

    @Test
    void displayInputMessageOnIO() {
        candidateRegistry.registerCandidates();

        verify(consoleIO).display(INPUT_MESSAGE);
    }

    @Test
    void getCandidateNamesFromInput() {
        candidateRegistry.registerCandidates();

        verify(consoleIO).getInput();
    }

    @Test
    void trimNamesFromInputAndConvertToLowerCase() {
        when(consoleIO.getInput()).thenReturn("Air  ice FiRe ");

        candidateRegistry.registerCandidates();

        Set<String> candidateNames = candidateRegistry.getCandidateNames();
        assertNotNull(candidateNames);
        assertEquals(3, candidateNames.size());
        assertTrue(candidateNames.containsAll(Arrays.asList("air", "ice", "fire")));
    }

    @Test
    void candidateShouldNotBeRegisteredMultipleTimes() {
        when(consoleIO.getInput()).thenReturn("Air  air AIR ");

        candidateRegistry.registerCandidates();

        Set<String> candidateNames = candidateRegistry.getCandidateNames();
        assertNotNull(candidateNames);
        assertEquals(1, candidateNames.size());
        assertTrue(candidateNames.contains("air"));
    }

    @Test
    void displayInvalidCandidateMessageGivenInvalidInput() {
        when(consoleIO.getInput()).thenReturn("invalid candidate names").thenReturn("fire");

        candidateRegistry.registerCandidates();

        verify(consoleIO).display(INVALID_CANDIDATE_MESSAGES);
    }


    @Test
    void recurseUntilValidInputIsGiven() {
        when(consoleIO.getInput()).thenReturn("invalid input").thenReturn("invalid again")
                .thenReturn("ice air").thenReturn("invalid input");

        candidateRegistry.registerCandidates();

        verify(consoleIO, times(3)).display(INPUT_MESSAGE);
        verify(consoleIO, times(3)).getInput();
        verify(consoleIO, times(2)).display(INVALID_CANDIDATE_MESSAGES);
    }
}